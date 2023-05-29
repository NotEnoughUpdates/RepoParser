package io.github.moulberry.repo.data;

import com.google.gson.*;
import io.github.moulberry.repo.util.NEUId;
import lombok.Getter;

import java.lang.reflect.Type;

@Getter
public class NEUIngredient {
    @NEUId String itemId;
    double amount;
    public static final String NEU_SENTINEL_EMPTY = "NEU_SENTINEL_EMPTY";
    public static final String NEU_SENTINEL_COINS = "SKYBLOCK_COIN";
    public static final NEUIngredient SENTINEL_EMPTY = new NEUIngredient();

    static {
        SENTINEL_EMPTY.itemId = NEU_SENTINEL_EMPTY;
        SENTINEL_EMPTY.amount = 0;
    }

    private NEUIngredient() {
    }

    public static NEUIngredient ofCoins(double coins) {
        NEUIngredient neuIngredient = new NEUIngredient();
        neuIngredient.amount = coins;
        neuIngredient.itemId = NEU_SENTINEL_COINS;
        return neuIngredient;
    }

    public static NEUIngredient fromItem(NEUItem item, int count) {
        NEUIngredient ingredient = new NEUIngredient();
        ingredient.amount = count;
        ingredient.itemId = item.getSkyblockItemId();
        return ingredient;
    }

    public static NEUIngredient fromString(String string) {
        String[] parts = string.split(":");
        NEUIngredient ingredient = new NEUIngredient();
        if (parts.length == 2) {
            ingredient.amount = Double.parseDouble(parts[1]);
        } else if (parts.length == 1) {
            ingredient.amount = 1;
        } else {
            throw new IllegalArgumentException("Could not parse ingredient " + string);
        }
        ingredient.itemId = parts[0];
        if (NEU_SENTINEL_EMPTY.equals(ingredient.itemId))
            return SENTINEL_EMPTY;
        return ingredient;
    }

    public static class Serializer implements JsonDeserializer<NEUIngredient> {

        @Override
        public NEUIngredient deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            if (!json.isJsonPrimitive()) throw new JsonParseException("Expected string for ingredient, found " + json);
            JsonPrimitive p = json.getAsJsonPrimitive();
            if (!p.isString()) throw new JsonParseException("Expected string for ingredient, found " + json);
            String asString = json.getAsString();
            return fromString(asString);
        }
    }

    @Override
    public String toString() {
        return String.format("NEUIngredient{%s:%f}", itemId, amount);
    }
}
