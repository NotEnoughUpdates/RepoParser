package io.github.moulberry.repo;

import io.github.moulberry.repo.data.NEUItem;
import io.github.moulberry.repo.util.NEUId;
import io.github.moulberry.repo.util.StreamIt;
import lombok.Getter;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

public class NEUItems implements IReloadable {

    @Getter
    Map<@NEUId String, NEUItem> items;

    @Override
    public void reload(NEURepository repository) throws NEURepositoryException {
        items = new HashMap<>();
        try (Stream<NEURepoFile> itemSources = repository.tree("items")
                .filter(NEURepoFile::isFile)
                .filter(it -> it.getFsPath().getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".json"))) {
            for (NEURepoFile rf : new StreamIt<>(itemSources)) {
                NEUItem item = rf.json(NEUItem.class);
                items.put(item.getSkyblockItemId(), item);
            }
        }
    }

    @Nullable
    public NEUItem getItemBySkyblockId(@NEUId String itemId) {
        if (items == null) return null;
        return items.get(itemId.toUpperCase(Locale.ROOT));
    }
}
