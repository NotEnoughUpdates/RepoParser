package io.github.moulberry.repo.constants;

import io.github.moulberry.repo.util.NEUId;
import lombok.Getter;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Value
public class BazaarStocks {
    Map<@NEUId String, String> stocks;

    public @NotNull String getBazaarStockOrDefault(@NotNull @NEUId String id) {
        return stocks.getOrDefault(id, id);
    }

    @Getter
    public static class InternalRepresentation {
        @NEUId
        String id;

        String stock;
    }
}
