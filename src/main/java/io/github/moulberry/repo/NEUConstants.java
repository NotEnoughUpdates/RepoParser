package io.github.moulberry.repo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import io.github.moulberry.repo.constants.*;
import lombok.Getter;

import java.util.List;
import java.util.Map;

public class NEUConstants implements IReloadable {
    @Getter
    Bonuses bonuses;
    @Getter
    Parents parents;
    @Getter
    Enchants enchants;
    @Getter
    EssenceCosts essenceCost;
    @Getter
    FairySouls fairySouls;
    @Getter
    Misc misc;
    @Getter
    Leveling leveling;
    @Getter
    PetLevelingData petLevelingData;

    public void reload(NEURepository repository) throws NEURepositoryException {
        bonuses = repository.requireFile("constants/bonuses.json").json(Bonuses.class);
        parents = new Parents(repository.requireFile("constants/parents.json")
                .json(new TypeToken<Map<String, List<String>>>() {
                }));
        enchants = repository.requireFile("constants/enchants.json").json(Enchants.class);
        essenceCost = new EssenceCosts(repository.requireFile("constants/essencecosts.json").json(JsonObject.class));
        fairySouls = new FairySouls(repository.gson, repository.requireFile("constants/fairy_souls.json").json(new TypeToken<Map<String, JsonElement>>() {
        }));
        misc = repository.requireFile("constants/misc.json").json(Misc.class);
        leveling = repository.requireFile("constants/leveling.json").json(Leveling.class);
        petLevelingData = repository.requireFile("constants/pets.json").json(PetLevelingData.class);
    }


}
