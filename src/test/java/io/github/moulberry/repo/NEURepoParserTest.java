package io.github.moulberry.repo;

import io.github.moulberry.repo.data.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NEURepoParserTest {
    @Test
    public void test() throws NEURepositoryException {
        System.out.printf("Parser v%s%n", NEURepositoryVersion.REPOSITORY_PARSER_VERSION);
        System.out.printf("Schema %d.%d%n", NEURepositoryVersion.REPOSITORY_SCHEMA_VERSION_MAJOR, NEURepositoryVersion.REPOSITORY_SCHEMA_VERSION_MINOR);

        NEURepository repository = NEURepository.of(Paths.get("NotEnoughUpdates-REPO"));
        NEURecipeCache recipes = NEURecipeCache.forRepo(repository);
        repository.reload();
        System.out.println("unknown recipe types: " + repository.getItems().getItems().values().stream()
                .flatMap(it -> it.getRecipes().stream())
                .filter(it -> it instanceof NEUUnknownRecipe).map(it -> (NEUUnknownRecipe) it)
                .map(NEUUnknownRecipe::getType)
                .collect(Collectors.toSet()));
        Assertions.assertEquals("MINING", repository.getConstants().getPetLevelingData().getPetExpTypes().get("ROCK"));
        Assertions.assertEquals("PetNumbers.Stats(otherNumbers=[0.2, 0.1, 10.0, 0.2], statNumbers={ABILITY_DAMAGE=0.0, INTELLIGENCE=1.0})", repository.getConstants().getPetNumbers().get("SHEEP").get(Rarity.LEGENDARY).interpolatedStatsAtLevel(1).toString());
        Assertions.assertEquals("PetNumbers.Stats(otherNumbers=[74.74747474747474, 0.3787878787878788, 0.17424242424242425], statNumbers={STRENGTH=37.37373737373737, MAGIC_FIND=4.94949494949495, BONUS_ATTACK_SPEED=37.37373737373737})", repository.getConstants().getPetNumbers().get("GOLDEN_DRAGON").get(Rarity.LEGENDARY).interpolatedStatsAtLevel(150).toString());
        Assertions.assertEquals(200, repository.getConstants().getPetLevelingData().getPetLevelingBehaviourOverrides().get("GOLDEN_DRAGON").getMaxLevel());
        Assertions.assertEquals(175, repository.getConstants().getMisc().getRainbowNames().size());
        Assertions.assertEquals("Private Island", repository.getConstants().getMisc().getAreaNames().get("dynamic"));
        Assertions.assertEquals(12, repository.getConstants().getMisc().getMaxMinionLevel().get("BLAZE_GENERATOR"));
        Assertions.assertEquals("100000", repository.getConstants().getMisc().getSlayerCost().get(4));
        Assertions.assertEquals("MVP", repository.getConstants().getMisc().getRanks().get("SUPERSTAR").getTag());
        Assertions.assertEquals("[CANDY_RING, CANDY_ARTIFACT, CANDY_RELIC]", repository.getConstants().getMisc().getTalismanUpgrades().get("CANDY_TALISMAN").toString());
        Assertions.assertEquals(31, repository.getConstants().getMisc().getCredits().getLore().size());
        Assertions.assertEquals("{magic_find=5.0}", repository.getConstants().getBonuses().getPetRewards(115).toString());
        Assertions.assertEquals("{damage_increase=2.0999992, crit_chance=30.0}", repository.getConstants().getBonuses().getAccumulativeLevelingRewards("skill_combat", 60).toString());
        Assertions.assertEquals("Optional[PERFECT_AMETHYST_GEM]", repository.getConstants().getParents().getParent("FLAWED_AMETHYST_GEM").toString());
        Assertions.assertEquals("[bane_of_arthropods, cleave, critical, cubism, dragon_hunter, ender_slayer, execute, experience, fire_aspect, first_strike, giant_killer, impaling, knockback, lethality, life_steal, looting, luck, mana_steal, PROSECUTE, scavenger, sharpness, smite, syphon, titan_killer, thunderlord, thunderbolt, triple_strike, vampirism, venomous, vicious, ultimate_one_for_all, ultimate_soul_eater, ultimate_chimera, ultimate_combo, ultimate_swarm, ultimate_wise, smoldering, ultimate_inferno, ultimate_fatal_tempo, tabasco, champion, divine_gift]", repository.getConstants().getEnchants().getAvailableEnchants("SWORD").toString());
        Assertions.assertEquals("[bane_of_arthropods, smite, ultimate_one_for_all]", repository.getConstants().getEnchants().getConflictingEnchants("sharpness").toString());
        Assertions.assertEquals("EssenceCosts.EssenceCost(type=Crimson, essenceCosts={1=170, 2=190, 3=215, 4=240, 5=270, 6=300, 7=340, 8=390, 9=440, 10=500}, itemCosts={4=[§610,000 Coins], 5=[§625,000 Coins], 6=[§650,000 Coins], 7=[§6100,000 Coins], 8=[§6Heavy Pearl §8x3, §6250,000 Coins], 9=[§6Heavy Pearl §8x4, §6500,000 Coins], 10=[§6Heavy Pearl §8x5, §61,000,000 Coins]})", repository.getConstants().getEssenceCost().getCosts().get("HOT_CRIMSON_HELMET").toString());
        Assertions.assertEquals("Coordinate(x=138, y=66, z=129)", repository.getConstants().getFairySouls().getSoulLocations().get("hub").get(0).toString());
        Assertions.assertEquals(247, repository.getConstants().getFairySouls().getMaxSouls());
        Assertions.assertEquals("minecraft:diamond_sword", repository.getItems().getItemBySkyblockId("ASPECT_OF_THE_END").getMinecraftItemId());
        Assertions.assertFalse(repository.getItems().getItemBySkyblockId("ASPECT_OF_THE_END").isVanilla());
        Assertions.assertTrue(repository.getItems().getItemBySkyblockId("DIAMOND").isVanilla());
        Assertions.assertEquals("[NEUIngredient{TITANIUM_DRILL_4:1.000000}, NEUIngredient{SKYBLOCK_COIN:50000000.000000}, NEUIngredient{DIVAN_ALLOY:1.000000}]", ((NEUForgeRecipe) recipes.getRecipes().get("DIVAN_DRILL").stream().filter(NEUForgeRecipe.class::isInstance).findAny().get()).getInputs().toString());
        Assertions.assertEquals(864000, ((NEUKatUpgradeRecipe) recipes.getRecipes().get("BAL;4").stream().filter(NEUKatUpgradeRecipe.class::isInstance).findAny().get()).getSeconds());

        int expTotal = 235268;
        int expLeft = expTotal;
        int level = 0;
        for (int expRequiredForThisLevel : repository.getConstants().getLeveling().getSkillExperienceRequiredPerLevel()) {
            if (expLeft > expRequiredForThisLevel) {
                level++;
                expLeft -= expRequiredForThisLevel;
            }
        }
        Assertions.assertEquals(235268, expTotal);
        Assertions.assertEquals(18, level);
        Assertions.assertEquals(12843, expLeft);


        Assertions.assertEquals("[x8-15, x10-18, x2-3, x8-15, x8-15, x3, x1-2, x0-2, x0-3, x5-6, x0-6, x0-3, x30-32, x2, x3-6, x1-2, .05% per Summoning Eye, .01% per Summoning Eye, 2% per Summoning Eye, 3% per Summoning Eye, .05% per Summoning Eye, .01% per Summoning Eye, 2% per Summoning Eye, 3% per Summoning Eye, x1-2, x1-2, .05% per Summoning Eye, .01% per Summoning Eye, 2% per Summoning Eye, 3% per Summoning Eye, x4-7, x8-15, x1-3, x1-3, x1-2, x1-2, x1-2, x1-2, .05% per Summoning Eye, .01% per Summoning Eye, 2% per Summoning Eye, 3% per Summoning Eye, x8-15, x8-15, §aUnommon 20%, x1, x-10, x1-3, §a20%, §a10%, .05% per Summoning Eye, .01% per Summoning Eye, 2% per Summoning Eye, 3% per Summoning Eye, x2-4, x2-3, x1-3, x1-4, x1-2, x8-15, x8-15, 5% per Hit, 5% per Hit, 4.5% per Hit, 0.0126% per Hit, 0.0054% per Hit, x1-2, x1-2, x2-3, x8-15, x8-15, x1-3, x1-2, .05% per Summoning Eye, .01% per Summoning Eye, 2% per Summoning Eye, 3% per Summoning Eye, x1-2, x1-3, x1-2, §fUncommon 25%, .05% per Summoning Eye, .01% per Summoning Eye, 2% per Summoning Eye, 3% per Summoning Eye, x32, x1-2, x1-2, x1-2, x2-3, x5, x1-2, x1-2, x1-2, x10-22, x2, x2-3, x1-3, x15-27, x18-41]", repository.getItems().getItems().values().stream()
                .flatMap(it -> it.getRecipes().stream())
                .flatMap(it -> {
                    if (it instanceof NEUMobDropRecipe) {
                        return ((NEUMobDropRecipe) it).getDrops().stream();
                    }
                    return Stream.empty();
                })
                .map(NEUMobDropRecipe.Drop::getChance)
                .filter(it -> it != null && !it.matches("\\d+(.\\d+)?+%"))
                .collect(Collectors.toList()).toString()
        );
    }
}
