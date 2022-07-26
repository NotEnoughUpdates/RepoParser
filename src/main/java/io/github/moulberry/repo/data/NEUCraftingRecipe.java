package io.github.moulberry.repo.data;

import com.google.gson.*;
import lombok.Getter;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class NEUCraftingRecipe implements NEURecipe {

    NEUIngredient[] inputs;
    @Nullable
    String extraText;
    NEUIngredient output;
    int outputCount;

    public static class Serializer implements JsonDeserializer<NEUCraftingRecipe> {

        @Override
        public NEUCraftingRecipe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            NEUCraftingRecipe cr = new NEUCraftingRecipe();
            JsonObject recipe = json.getAsJsonObject();
            String[] x = {"1", "2", "3"};
            String[] y = {"A", "B", "C"};
            cr.inputs = new NEUIngredient[9];
            for (int i = 0; i < 9; i++) {
                String name = y[i / 3] + x[i % 3];
                if (!recipe.has(name)) continue;
                String item = recipe.get(name).getAsString();
                if (item == null || item.isEmpty()) {
                    cr.inputs[i] = NEUIngredient.SENTINEL_EMPTY;
                } else {
                    cr.inputs[i] = NEUIngredient.fromString(item);
                }
            }
            cr.outputCount = -1;
            if (recipe.has("count"))
                cr.outputCount = recipe.get("count").getAsInt();
            if (recipe.has("crafttext"))
                cr.extraText = recipe.get("crafttext").getAsString();
            if (recipe.has("overrideOutputId"))
                cr.output = NEUIngredient.fromString(recipe.get("overrideOutputId").getAsString());
            return cr;
        }
    }

    @Override
    public void fillItemInfo(NEUItem item) {
        if (this.extraText == null)
            this.extraText = item.crafttext;
        if (this.output == null)
            this.output = NEUIngredient.fromString(item.skyblockItemId);
        if (outputCount > 0)
            this.output.amount = outputCount;
    }

    @Override
    public Collection<NEUIngredient> getAllOutputs() {
        return Collections.singletonList(output);
    }

    @Override
    public Collection<NEUIngredient> getAllInputs() {
        List<NEUIngredient> ingredientList = new ArrayList<>();
        for (NEUIngredient ingredient : inputs) {
            if (ingredient != null)
                ingredientList.add(ingredient);
        }
        return ingredientList;
    }
}
