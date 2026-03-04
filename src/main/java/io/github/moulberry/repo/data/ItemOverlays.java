package io.github.moulberry.repo.data;

import io.github.moulberry.repo.IReloadable;
import io.github.moulberry.repo.NEURepoFile;
import io.github.moulberry.repo.NEURepository;
import io.github.moulberry.repo.NEURepositoryException;
import io.github.moulberry.repo.util.NEUId;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class ItemOverlays implements IReloadable {

    List<ItemOverlayFile> overlays;

    public static ItemOverlays forRepo(NEURepository repository) {
        ItemOverlays overlays = new ItemOverlays();
        repository.registerReloadListener(overlays);
        return overlays;
    }

    @Override
    public void reload(NEURepository repository) throws NEURepositoryException {
        @Cleanup Stream<NEURepoFile> files = repository.tree("itemsOverlay");
        overlays = files
                .filter(NEURepoFile::isFile)
                .map(file -> {
                    Matcher matcher = PATH_PATTERN.matcher(file.getPath());
                    if (matcher.matches()) {
                        int version = Integer.parseInt(matcher.group(1));
                        String name = matcher.group(2);
                        return new ItemOverlayFile(file, version, name);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * @param version the highest supported version
     * @return a map of item ids to their most up-to-date overlay file that is still readable.
     */
    public Map<@NEUId String, ItemOverlayFile> getMostUpToDateCompatibleWith(int version) {
        return overlays.stream()
                .filter(it -> it.version <= version)
                .collect(Collectors.toMap(ItemOverlayFile::getItemId, Function.identity(), (a, b) -> a.version > b.version ? a : b));
    }

    @Value
    public static class ItemOverlayFile {
        NEURepoFile file;
        int version;
        @NEUId
        String itemId;
    }

    private static final Pattern PATH_PATTERN = Pattern.compile("itemsOverlay/([0-9]+)/(.+)\\.snbt");
}
