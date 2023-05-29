package io.github.moulberry.repo.data;

import com.google.gson.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class NEUUnknownRecipe implements NEURecipe {
    /**
     * Type tag of the unknown recipe type.
     */
    @Getter
    @Nullable
    final String type;

    /**
     * The json tree of this unknown recipe.
     */
    @Getter
    final JsonObject tree;

    /**
     * Item associated with this recipe.
     */
    @Getter
    NEUItem item;

    @Override
    public Collection<NEUIngredient> getAllInputs() {
        return Collections.singleton(NEUIngredient.fromItem(item, 1));
    }

    @Override
    public Collection<NEUIngredient> getAllOutputs() {
        return Collections.emptyList();
    }

    @Override
    public void fillItemInfo(NEUItem item) {
        this.item = item;
    }

    public static class Serializer implements JsonDeserializer<NEUUnknownRecipe> {

        @Override
        public NEUUnknownRecipe deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonElement type = json.getAsJsonObject().get("type");
            return new NEUUnknownRecipe(type != null && type.isJsonPrimitive() ? type.getAsString() : null, json.getAsJsonObject());
        }
    }
}
