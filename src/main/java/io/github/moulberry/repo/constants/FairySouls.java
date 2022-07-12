package io.github.moulberry.repo.constants;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import io.github.moulberry.repo.data.Coordinate;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FairySouls {
    @Getter
    int maxSouls;
    @Getter
    Map<String, List<Coordinate>> soulLocations = new HashMap<>();

    public FairySouls(Gson gson, Map<String, JsonElement> json) {
        for (Map.Entry<String, JsonElement> things : json.entrySet()) {
            if (things.getValue().isJsonArray()) {
                soulLocations.put(
                        things.getKey(),
                        gson.fromJson(things.getValue(),
                                new TypeToken<List<Coordinate>>() {
                                }.getType()));
            }
        }
        maxSouls = json.get("Max Souls").getAsInt();
    }
}
