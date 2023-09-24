package io.github.moulberry.repo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import io.github.moulberry.repo.constants.*;
import io.github.moulberry.repo.data.Rarity;
import io.github.moulberry.repo.util.PetId;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class NEUConstants implements IReloadable {
    Bonuses bonuses;
    Parents parents;
    Enchants enchants;
    EssenceCosts essenceCost;
    FairySouls fairySouls;
    Misc misc;
    Leveling leveling;
    PetLevelingData petLevelingData;
    Map<@PetId String, Map<Rarity, PetNumbers>> petNumbers;
    Islands islands;

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
        petNumbers = repository.requireFile("constants/petnums.json").json(new TypeToken<Map<@PetId String, Map<Rarity, PetNumbers>>>() {
        });
        NEURepoFile islandsFile = repository.file("constants/islands.json");
        islands = islandsFile != null ? islandsFile.json(Islands.class) : new Islands();
    }


}
