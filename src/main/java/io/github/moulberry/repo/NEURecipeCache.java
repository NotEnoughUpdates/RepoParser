package io.github.moulberry.repo;

import io.github.moulberry.repo.data.NEUIngredient;
import io.github.moulberry.repo.data.NEUItem;
import io.github.moulberry.repo.data.NEURecipe;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NEURecipeCache implements IReloadable {

    private NEURecipeCache() {
    }

    /**
     * Create a recipe cache.
     *
     * @param repository the repository for which to create a recipe cache
     *
     * @return an unitialized repository cache, that can be initialized by calling {@link NEURepository#reload()}, or by
     * manually {{@link #reload}ing it.}
     */
    public static NEURecipeCache forRepo(NEURepository repository) {
        NEURecipeCache neuRecipeCache = new NEURecipeCache();
        repository.registerReloadListener(neuRecipeCache);
        return neuRecipeCache;
    }


    @Getter
    Map<String, Set<NEURecipe>> recipes = new HashMap<>();
    @Getter
    Map<String, Set<NEURecipe>> usages = new HashMap<>();


    @Override
    public void reload(NEURepository repository) throws NEURepositoryException {
        Map<String, Set<NEURecipe>> recipes = new HashMap<>();
        Map<String, Set<NEURecipe>> usages = new HashMap<>();
        for (NEUItem item : repository.getItems().getItems().values()) {
            for (NEURecipe recipe : item.getRecipes()) {
                for (NEUIngredient input : recipe.getAllInputs()) {
                    usages.computeIfAbsent(input.getItemId(), ignored -> new HashSet<>()).add(recipe);
                }
                for (NEUIngredient output : recipe.getAllOutputs()) {
                    recipes.computeIfAbsent(output.getItemId(), ignored -> new HashSet<>()).add(recipe);
                }
            }
        }
        this.recipes = recipes;
        this.usages = usages;
    }
}
