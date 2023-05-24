package io.github.moulberry.repo.constants;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.moulberry.repo.NEURepositoryException;
import io.github.moulberry.repo.util.NEUId;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EssenceCosts {

    @Data
    public static class EssenceCost {
        private EssenceCost() {
        }

        @Getter
        String type;

        @Getter
        Map<Integer, Integer> essenceCosts;

        /**
         * WARNING: The inner list of strings is NOT item ids or ingredients, instead it *currently* is item
         * descriptions (as intended to be user facing strings).
         */
        @Getter
        Map<Integer, List<String>> itemCosts;
    }

    @Getter
    Map<@NEUId String, EssenceCost> costs;

    public EssenceCosts(JsonObject json) throws NEURepositoryException {
        costs = new HashMap<>();

        try {
            for (Map.Entry<String, JsonElement> costs : json.entrySet()) {
                EssenceCost essenceCost = new EssenceCost();
                JsonElement value = costs.getValue();
                JsonObject object = value.getAsJsonObject();
                essenceCost.type = object.get("type").getAsString();
                essenceCost.essenceCosts = new HashMap<>();
                int i = 1;
                while (true) {
                    JsonElement costThing = object.get(i + "");
                    if (costThing == null) break;
                    int cost = costThing.getAsInt();
                    essenceCost.essenceCosts.put(i, cost);
                    i++;
                }
                essenceCost.itemCosts = new HashMap<>();
                if (object.has("items")) {
                    JsonObject items = object.getAsJsonObject("items");
                    for (Map.Entry<String, JsonElement> itemCost : items.entrySet()) {
                        JsonArray itemCostList = itemCost.getValue().getAsJsonArray();
                        List<String> itemCostListTrans = new ArrayList<>();
                        for (JsonElement jsonElement : itemCostList) {
                            itemCostListTrans.add(jsonElement.getAsString());
                        }
                        essenceCost.itemCosts.put(Integer.parseInt(itemCost.getKey()), itemCostListTrans);
                    }
                }
                this.costs.put(costs.getKey(), essenceCost);
            }
        } catch (ClassCastException | NumberFormatException e) {
            throw new NEURepositoryException("constants/essencecosts.json", "Invalid JSON Syntax", e);
        }
    }
}
