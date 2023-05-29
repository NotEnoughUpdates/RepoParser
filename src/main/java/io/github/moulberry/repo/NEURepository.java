package io.github.moulberry.repo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.moulberry.repo.data.*;
import io.github.moulberry.repo.vendored.RuntimeTypeAdapterFactory;
import lombok.Getter;
import lombok.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * The base object for a structured in memory representation of the NEU repository files.
 */
public final class NEURepository {

    final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(new TypeToken<NEUUnknownRecipe>() {
            }.getType(), new NEUUnknownRecipe.Serializer())
            .registerTypeAdapter(new TypeToken<NEUIngredient>() {
            }.getType(), new NEUIngredient.Serializer())
            .registerTypeAdapter(new TypeToken<Coordinate>() {
            }.getType(), new Coordinate.CoordinateSerializer())
            .registerTypeAdapter(new TypeToken<NEUMobDropRecipe.Drop>() {
            }.getType(), new NEUMobDropRecipe.Drop.Serializer())
            .registerTypeAdapter(new TypeToken<NEUCraftingRecipe>() {
            }.getType(), new NEUCraftingRecipe.Serializer())
            .registerTypeAdapterFactory(
                    RuntimeTypeAdapterFactory.of(NEURecipe.class, "type", true)
                            .registerSubtype(NEUForgeRecipe.class, "forge")
                            .registerSubtype(NEUTradeRecipe.class, "trade")
                            .registerSubtype(NEUCraftingRecipe.class, "crafting")
                            .registerSubtype(NEUMobDropRecipe.class, "drops")
                            .registerSubtype(NEUNpcShopRecipe.class, "npc_shop")
                            .registerSubtype(NEUKatUpgradeRecipe.class, "katgrade")
                            .setFallbackType(NEUUnknownRecipe.class)
                            .setDefaultTypeTag("crafting")
            )
            .create();
    /**
     * The base path of this repository. This is the folder containing the {@code .git} folder..
     */
    @Getter
    final Path baseFolder;
    /**
     * All constants loaded in the {@code constant} folder of the repository.
     */
    @Getter
    final NEUConstants constants;
    /**
     * All {@link IReloadable}s linked to this repository. Those can be either content that needs to be reloaded, caches
     * that need to be marked as dirty, or other.
     */
    @Getter
    final List<IReloadable> reloadables = new ArrayList<>();
    /**
     * All items loaded in the {@code items} folder of the repository.
     */
    @Getter
    final NEUItems items;
    /**
     * If the current state of the repository is unstable. Unstable means that either the repository is
     * {@link #isIncomplete} or the repository is currently being {@link #reload()}ed and therefore may have mismatched
     * versions of different objects.
     */
    @Getter
    boolean isUnstable = true;

    /**
     * If the current state of the repository is incomplete. Incomplete means that either there hasn't been a
     * {@link #reload()} yet, or the last reload completed exceptionally.
     */
    @Getter
    boolean isIncomplete = true;

    /**
     * Create an in memory loaded repository of the given file path. This function doesn't do any loading, instead
     * {@link #reload()} has to be manually called by the caller of this function. Consecutive calls with the same
     * arguments will result in distinct objects, a common instance should be manually maintained.
     *
     * @param baseFolder the base folder of the repository. (should contain {@code README.md}, among other files)
     *
     * @return a {@link NEURepository} instance corresponding to this path.
     */
    public static NEURepository of(@NonNull Path baseFolder) {
        return new NEURepository(baseFolder);
    }


    private NEURepository(@NonNull Path baseFolder) {
        this.baseFolder = baseFolder;
        this.constants = new NEUConstants();
        this.items = new NEUItems();
        registerReloadListener(this.constants);
        registerReloadListener(this.items);
    }

    /**
     * Registers a {@link IReloadable} to be called whenever {@link #reload()} is invoked. Reloaders are called in the
     * order that they are registered (with internal reloadables like {@link NEUItems} and {@link NEUConstants} always
     * being reloaded first).
     */
    public void registerReloadListener(IReloadable reloadable) {
        reloadables.add(reloadable);
    }

    /**
     * Reloads the repository from disk. During reloading the state of the repository may be unstable. However, every
     * individual state (like the collection of all items, the collection fo all fairy souls) are updated atomically,
     * therefore it is advised to store those objects if consecutive queries should be executed in the same repository
     * context.
     *
     * @throws NEURepositoryException if there is an exception during loading, every other reloadable will still be
     *                                reloaded (which may result in an inconsistent loaded state), and all exceptions
     *                                will collectively be thrown after all reloads have finished using
     *                                {@link Throwable#addSuppressed}
     */
    public synchronized void reload() throws NEURepositoryException {
        List<NEURepositoryException> storedExceptions = new ArrayList<>();
        isUnstable = true;
        for (IReloadable reloadable : reloadables) {
            try {
                reloadable.reload(this);
            } catch (NEURepositoryException e) {
                storedExceptions.add(e);
            } catch (Exception e) {
                storedExceptions.add(new NEURepositoryException("<unknown>", "Invalid non NEU exception thrown during repository reload", e));
            }
        }
        for (int i = 1; i < storedExceptions.size(); i++) {
            storedExceptions.get(0).addSuppressed(storedExceptions.get(i));
        }
        if (storedExceptions.size() > 0) {
            isIncomplete = true;
            throw storedExceptions.get(0);
        }
        isUnstable = false;
        isIncomplete = false;
    }

    /**
     * Obtain a {@link NEURepoFile} that is guaranteed to exist.
     *
     * @param path the relative path of the file you want
     *
     * @throws NEURepositoryException if the file does not exist. this exception should be propagated. if you instead
     *                                want to react to a non existance of the file, use {@link #file} instead.
     */
    public NEURepoFile requireFile(@NonNull String path) throws NEURepositoryException {
        NEURepoFile file = file(path);
        if (file == null) throw new NEURepositoryException(path, "this file is required", null);
        return file;
    }

    /**
     * Obtain a {@link NEURepoFile}.
     *
     * @param path the relative path of the file you want
     *
     * @return the file as a repo file, or {@code null} if it doesn't exist.
     */
    @Nullable
    public NEURepoFile file(@NonNull String path) {
        Path fsPath = baseFolder.resolve(path);
        if (!Files.isReadable(fsPath))
            return null;
        return new NEURepoFile(this, fsPath);
    }

    /**
     * @return a stream of {@link NEURepoFile}s contained within this relative path. This stream needs to be manually
     * closed.
     */
    public Stream<NEURepoFile> tree(@NonNull String path) throws NEURepositoryException {
        Path fsPath = baseFolder.resolve(path);
        if (!Files.isDirectory(fsPath))
            return Stream.empty();
        try {
            return Files.walk(fsPath).map(it -> new NEURepoFile(this, it));
        } catch (IOException e) {
            throw new NEURepositoryException(path, "could not walk directory", e);
        }
    }
}
