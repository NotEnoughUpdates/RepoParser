import io.github.moulberry.repo.NEURecipeCache;
import io.github.moulberry.repo.NEURepository;
import io.github.moulberry.repo.NEURepositoryException;
import io.github.moulberry.repo.NEURepositoryVersion;
import io.github.moulberry.repo.data.NEUForgeRecipe;
import io.github.moulberry.repo.data.NEUMobDropRecipe;

import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestMain {
    public static void main(String[] args) throws NEURepositoryException {
        System.out.printf("Parser v%s%n", NEURepositoryVersion.REPOSITORY_PARSER_VERSION);
        System.out.printf("Schema %d.%d%n", NEURepositoryVersion.REPOSITORY_SCHEMA_VERSION_MAJOR, NEURepositoryVersion.REPOSITORY_SCHEMA_VERSION_MINOR);

        NEURepository repository = NEURepository.of(Paths.get("NotEnoughUpdates-REPO"));
        NEURecipeCache recipes = NEURecipeCache.forRepo(repository);
        repository.reload();
        System.out.println("pet mf (115): " + repository.getConstants().getBonuses().getPetRewards(115));
        System.out.println("skill reward (combat 60): " + repository.getConstants().getBonuses().getAccumulativeLevelingRewards("skill_combat", 60));
        System.out.println("parent of FLAWED_AMETHYST_GEM: " + repository.getConstants().getParents().getParent("FLAWED_AMETHYST_GEM"));
        System.out.println("enchants for a sword: " + repository.getConstants().getEnchants().getAvailableEnchants("SWORD"));
        System.out.println("conflicting enchants with sharpness: " + repository.getConstants().getEnchants().getConflictingEnchants("sharpness"));
        System.out.println("upgrade cost for HOT_CRIMSON_HELMET: " + repository.getConstants().getEssenceCost().getCosts().get("HOT_CRIMSON_HELMET"));
        System.out.println("first fairy soul in the hub: " + repository.getConstants().getFairySouls().getSoulLocations().get("hub").get(0));
        System.out.println("soul total: " + repository.getConstants().getFairySouls().getMaxSouls());
        System.out.println("minecraft item of ASPECT_OF_THE_END: " + repository.getItems().getItemBySkyblockId("ASPECT_OF_THE_END").getMinecraftItemId());
        System.out.println("is vanilla ASPECT_OF_THE_END: " + repository.getItems().getItemBySkyblockId("ASPECT_OF_THE_END").isVanilla());
        System.out.println("is vanilla DIAMOND: " + repository.getItems().getItemBySkyblockId("DIAMOND").isVanilla());
        System.out.println("crafting recipe of DIVAN DRILL: " + ((NEUForgeRecipe) recipes.getRecipes().get("DIVAN_DRILL").stream().findAny().get()).getInputs());

        int expTotal = 235268;
        int expLeft = expTotal;
        int level = 0;
        for (int expRequiredForThisLevel : repository.getConstants().getLeveling().getSkillExperienceRequiredPerLevel()) {
            if (expLeft > expRequiredForThisLevel) {
                level++;
                expLeft -= expRequiredForThisLevel;
            }
        }
        System.out.println(expTotal + " skill exp get you to level " + level + " with " + expLeft + " leftover exp");


        System.out.println("Non standard drop chances: " + repository.getItems().getItems().values().stream()
                .flatMap(it -> it.getRecipes().stream())
                .flatMap(it -> {
                    if (it instanceof NEUMobDropRecipe) {
                        return ((NEUMobDropRecipe) it).getDrops().stream();
                    }
                    return Stream.empty();
                })
                .map(it -> it.getChance())
                .filter(it -> it != null && !it.matches("\\d+(.\\d+)?+%"))
                .collect(Collectors.toList()));

    }
}
