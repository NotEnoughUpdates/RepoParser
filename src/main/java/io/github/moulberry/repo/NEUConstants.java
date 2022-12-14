package io.github.moulberry.repo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import io.github.moulberry.repo.constants.Parents;
import io.github.moulberry.repo.constants.Bonuses;
import io.github.moulberry.repo.constants.Enchants;
import io.github.moulberry.repo.constants.EssenceCosts;
import io.github.moulberry.repo.constants.FairySouls;
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

    public void reload(NEURepository repository) throws NEURepositoryException {
        bonuses = repository.requireFile("constants/bonuses.json").json(Bonuses.class);
        parents = new Parents(repository.requireFile("constants/parents.json")
                .json(new TypeToken<Map<String, List<String>>>() {
                }));
        enchants = repository.requireFile("constants/enchants.json").json(Enchants.class);
        essenceCost = new EssenceCosts(repository.requireFile("constants/essencecosts.json").json(JsonObject.class));
        fairySouls = new FairySouls(repository.gson, repository.requireFile("constants/fairy_souls.json").json(new TypeToken<Map<String, JsonElement>>() {
        }));
    }


}
