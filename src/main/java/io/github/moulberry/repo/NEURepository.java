package io.github.moulberry.repo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.moulberry.repo.data.Coordinate;
import lombok.Getter;
import lombok.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public final class NEURepository {

    final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(new TypeToken<Coordinate>() {
            }.getType(), new Coordinate.CoordinateSerializer())
            .create();
    @Getter
    final Path baseFolder;
    @Getter
    final NEUConstants constants;
    @Getter
    final List<IReloadable> reloadables = new ArrayList<>();
    @Getter
    final NEUItems items;

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

    public void registerReloadListener(IReloadable reloadable) {
        reloadables.add(reloadable);
    }

    public void reload() throws NEURepositoryException {
        List<NEURepositoryException> storedExceptions = new ArrayList<>();
        for (IReloadable reloadable : reloadables) {
            try {
                reloadable.reload(this);
            } catch (NEURepositoryException e) {
                storedExceptions.add(e);
            }
        }
        for (int i = 1; i < storedExceptions.size(); i++) {
            storedExceptions.get(0).addSuppressed(storedExceptions.get(i));
        }
        if (storedExceptions.size() > 0) {
            throw storedExceptions.get(0);
        }
    }


    public NEURepoFile requireFile(@NonNull String path) throws NEURepositoryException {
        NEURepoFile file = file(path);
        if (file == null) throw new NEURepositoryException(path, "this file is required", null);
        return file;
    }

    @Nullable
    public NEURepoFile file(@NonNull String path) {
        Path fsPath = baseFolder.resolve(path);
        if (!Files.isReadable(fsPath))
            return null;
        return new NEURepoFile(this, fsPath);
    }

    /**
     * @return a stream of {@link NEURepoFile}s below this path. This stream needs to be closed
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
