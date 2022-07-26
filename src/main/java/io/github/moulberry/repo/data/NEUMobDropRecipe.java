package io.github.moulberry.repo.data;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class NEUMobDropRecipe implements NEURecipe {
    @SerializedName("combat_xp")
    int combatExperience;
    int coins;
    @SerializedName("xp")
    int enchantingExperience;
    String name;
    String render;
    String panorama;
    int level;
    List<String> extra;
    List<Drop> drops;
    transient NEUItem dropsFrom;

    @Data
    @AllArgsConstructor
    public static class Drop {
        NEUIngredient dropItem;
        @Nullable
        String chance;
        List<String> extra;

        public static class Serializer implements JsonDeserializer<Drop> {

            @Override
            public Drop deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                if (json.isJsonPrimitive()) {
                    return new Drop(NEUIngredient.fromString(json.getAsString()), null, Collections.emptyList());
                }
                JsonObject d = json.getAsJsonObject();
                return new Drop(
                        NEUIngredient.fromString(d.get("id").getAsString()),
                        d.has("chance") ? d.get("chance").getAsString() : null,
                        d.has("extra") ? context.deserialize(d.get("extra"), new TypeToken<List<String>>() {
                        }.getType()) : Collections.emptyList()
                );
            }
        }
    }

    @Override
    public void fillItemInfo(NEUItem item) {
        dropsFrom = item;
    }

    @Override
    public Collection<NEUIngredient> getAllInputs() {
        return Collections.singletonList(NEUIngredient.fromItem(dropsFrom, 1));
    }

    @Override
    public Collection<NEUIngredient> getAllOutputs() {
        List<NEUIngredient> l = new ArrayList<>();
        for (Drop drop : drops) {
            l.add(drop.getDropItem());
        }
        return l;
    }
}
