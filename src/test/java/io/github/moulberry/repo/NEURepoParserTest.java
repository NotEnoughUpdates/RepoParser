package io.github.moulberry.repo;

import io.github.moulberry.repo.data.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Collectors;

public class NEURepoParserTest {
    private static final NEURepository repository = NEURepository.of(Paths.get("NotEnoughUpdates-REPO"));
    private static final NEURecipeCache recipes = NEURecipeCache.forRepo(repository);

    @BeforeAll
    static void beforeAll() throws NEURepositoryException {
        System.out.printf("Parser v%s%n", NEURepositoryVersion.REPOSITORY_PARSER_VERSION);
        System.out.printf("Schema %d.%d%n", NEURepositoryVersion.REPOSITORY_SCHEMA_VERSION_MAJOR, NEURepositoryVersion.REPOSITORY_SCHEMA_VERSION_MINOR);

        repository.reload();
    }

    @Test
    void testBazaarStocks() {
        Assertions.assertEquals("SHARD_SALMON", repository.getConstants().getBazaarStocks().getBazaarStockOrDefault("ATTRIBUTE_SHARD_LOST_AND_FOUND;1"));
        Assertions.assertEquals("REDSTONE", repository.getConstants().getBazaarStocks().getBazaarStockOrDefault("REDSTONE"));
    }

    @Test
    void testPetConstants() {
        Assertions.assertEquals("MINING", repository.getConstants().getPetLevelingData().getPetExpTypes().get("ROCK"));
        Assertions.assertEquals("PetNumbers.Stats(otherNumbers=[0.2, 0.1, 10.0, 0.2], statNumbers={ABILITY_DAMAGE=0.2, INTELLIGENCE=1.0})", repository.getConstants().getPetNumbers().get("SHEEP").get(Rarity.LEGENDARY).interpolatedStatsAtLevel(1).toString());
        Assertions.assertEquals("PetNumbers.Stats(otherNumbers=[74.74747474747474, 0.37373737373737376, 0.18686868686868688], statNumbers={STRENGTH=37.37373737373737, MAGIC_FIND=7.474747474747475, BONUS_ATTACK_SPEED=37.37373737373737})", repository.getConstants().getPetNumbers().get("GOLDEN_DRAGON").get(Rarity.LEGENDARY).interpolatedStatsAtLevel(150).toString());
        Assertions.assertEquals(200, repository.getConstants().getPetLevelingData().getPetLevelingBehaviourOverrides().get("GOLDEN_DRAGON").getMaxLevel());
        Assertions.assertEquals(200, repository.getConstants().getPetLevelingData().getPetLevelingBehaviourOverrides().get("JADE_DRAGON").getMaxLevel());
    }

    @Test
    void testLevelingConstants() {
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
    }

    @Test
    void testMiscConstants() {
        Assertions.assertEquals(300, repository.getConstants().getMisc().getRainbowNames().size());
        Assertions.assertEquals("Private Island", repository.getConstants().getMisc().getAreaNames().get("dynamic"));
        Assertions.assertEquals(12, repository.getConstants().getMisc().getMaxMinionLevel().get("BLAZE_GENERATOR"));
        Assertions.assertEquals("100000", repository.getConstants().getMisc().getSlayerCost().get(4));
        Assertions.assertEquals("MVP", repository.getConstants().getMisc().getRanks().get("SUPERSTAR").getTag());
        Assertions.assertEquals("[CANDY_RING, CANDY_ARTIFACT, CANDY_RELIC]", repository.getConstants().getMisc().getTalismanUpgrades().get("CANDY_TALISMAN").toString());
        Assertions.assertEquals(31, repository.getConstants().getMisc().getCredits().getLore().size());
    }

    @Test
    void testConstants() {
        Assertions.assertEquals("{magic_find=5.0}", repository.getConstants().getBonuses().getPetRewards(115).toString());
        Assertions.assertEquals("{damage_increase=2.0999992, crit_chance=30.0}", repository.getConstants().getBonuses().getAccumulativeLevelingRewards("skill_combat", 60).toString());
        Assertions.assertEquals("Optional[PERFECT_AMETHYST_GEM]", repository.getConstants().getParents().getParent("FLAWED_AMETHYST_GEM").toString());
        Assertions.assertEquals("[bane_of_arthropods, cleave, critical, cubism, dragon_hunter, ender_slayer, execute, experience, fire_aspect, first_strike, giant_killer, impaling, knockback, lethality, life_steal, looting, luck, mana_steal, PROSECUTE, scavenger, sharpness, smite, syphon, titan_killer, thunderlord, thunderbolt, triple_strike, vampirism, venomous, vicious, ultimate_one_for_all, ultimate_soul_eater, ultimate_chimera, ultimate_combo, ultimate_swarm, ultimate_wise, smoldering, ultimate_inferno, ultimate_fatal_tempo, tabasco, champion, divine_gift]", repository.getConstants().getEnchants().getAvailableEnchants("SWORD").toString());
        Assertions.assertEquals("[bane_of_arthropods, smite, ultimate_one_for_all]", repository.getConstants().getEnchants().getConflictingEnchants("sharpness").toString());
        Assertions.assertEquals("EssenceCosts.EssenceCost(type=Crimson, essenceCosts={1=170, 2=190, 3=215, 4=240, 5=270, 6=300, 7=340, 8=390, 9=440, 10=500}, itemCosts={4=[SKYBLOCK_COIN:10000], 5=[SKYBLOCK_COIN:25000], 6=[SKYBLOCK_COIN:50000], 7=[SKYBLOCK_COIN:100000], 8=[HEAVY_PEARL:3, SKYBLOCK_COIN:250000], 9=[HEAVY_PEARL:4, SKYBLOCK_COIN:500000], 10=[HEAVY_PEARL:5, SKYBLOCK_COIN:1000000]})", repository.getConstants().getEssenceCost().getCosts().get("HOT_CRIMSON_HELMET").toString());
        Assertions.assertEquals("Coordinate(x=138, y=66, z=129)", repository.getConstants().getFairySouls().getSoulLocations().get("hub").get(0).toString());
        Assertions.assertEquals(266, repository.getConstants().getFairySouls().getMaxSouls());
    }

    @Test
    void testItems() {
        Assertions.assertEquals("minecraft:diamond_sword", repository.getItems().getItemBySkyblockId("ASPECT_OF_THE_END").getMinecraftItemId());
        Assertions.assertFalse(repository.getItems().getItemBySkyblockId("ASPECT_OF_THE_END").isVanilla());
        Assertions.assertTrue(repository.getItems().getItemBySkyblockId("DIAMOND").isVanilla());
    }

    @Test
    void testRecipes() {
        Assertions.assertEquals("[NEUIngredient{TITANIUM_DRILL_4:1.000000}, NEUIngredient{SKYBLOCK_COIN:50000000.000000}, NEUIngredient{DIVAN_ALLOY:1.000000}]", ((NEUForgeRecipe) recipes.getRecipes().get("DIVAN_DRILL").stream().filter(NEUForgeRecipe.class::isInstance).findAny().get()).getInputs().toString());
        Assertions.assertEquals(864000, ((NEUKatUpgradeRecipe) recipes.getRecipes().get("BAL;4").stream().filter(NEUKatUpgradeRecipe.class::isInstance).findAny().get()).getSeconds());

        Assertions.assertEquals("[x8-15, x10-18, x1-2, x2-3, x6-17, x3-5, x10-20, x16-22, x2-3, x8-15, x8-15, x1-2, x3-4, x0-2, x10-20, x0-2, x10-20, x5-6, x0-6, x10-60, x0-6, x10-60, x30-32, x8-13, x2-3, x20-30, x32-44, x2-4, x2-5, x20-35, x0-2, x1-10, x3-6, x1-2, x2-3, .05% per Summoning Eye, .01% per Summoning Eye, 2% per Summoning Eye, 3% per Summoning Eye, x1-2, .05% per Summoning Eye, .01% per Summoning Eye, 2% per Summoning Eye, 3% per Summoning Eye, x1-2, x2-3, x2-3, x1-2, x1-2, x1-2, .05% per Summoning Eye, .01% per Summoning Eye, 2% per Summoning Eye, 3% per Summoning Eye, x20-30, x4-7, x8-15, x1-3, x1-3, x1-2, x1-2, x2-3, x20-35, x0-2, x5-9, x1-2, x1-2, x1-2, .05% per Summoning Eye, .01% per Summoning Eye, 2% per Summoning Eye, 3% per Summoning Eye, x1-2, x1-3, x8-15, x8-15, x16-18, x20-35, x0-2, x1, x1-10, x0-2, §a20%, §a10%, .05% per Summoning Eye, .01% per Summoning Eye, 2% per Summoning Eye, 3% per Summoning Eye, x20-30, x1-3, x6-9, x35-55, x3-5, x4-8, x2-4, x2-3, x20-35, x0-2, x2-3, x2-4, x1-4, x1-2, x8-15, x8-15, 5% per Hit, 5% per Hit, 4.5% per Hit, 0.0126% per Hit, 0.0054% per Hit, x1-2, x2-3, x2-3, x8-15, x8-15, x1-2, x2-4, x2-4, x20-35, x0-2, x8-11, x1-2, x1-2, x1-2, x2, x1-2, x12-15, x2-3, .05% per Summoning Eye, .01% per Summoning Eye, 2% per Summoning Eye, 3% per Summoning Eye, x1-2, x2-4, x1-2, x2-4, x32-44, x3-5, x1-2, x5-7, x4-5, x20-30, .05% per Summoning Eye, .01% per Summoning Eye, 2% per Summoning Eye, 3% per Summoning Eye, x1-2, x1-2, x1-2, x1-2, x2-3, x10-17, 100% + 65% per hit, 100% + 25% per hit, 10% + 10% per hit, x5, x1-2, x1-2, x1-2, x10-22, 20% per hit, x2-3, x15-27, x18-41]", repository.getItems().getItems().values().stream()
                .flatMap(it -> it.getRecipes().stream())
                .filter(NEUMobDropRecipe.class::isInstance)
                .flatMap(it -> ((NEUMobDropRecipe) it).getDrops().stream())
                .map(NEUMobDropRecipe.Drop::getChance)
                .filter(it -> it != null && !it.matches("\\d+(.\\d+)?+%"))
                .collect(Collectors.toList()).toString()
        );
    }

    @Test
    void testUnknownRecipes() {
        Assertions.assertEquals(Collections.emptyList(), repository.getItems().getItems().values().stream()
                .flatMap(it -> it.getRecipes().stream())
                .filter(NEUUnknownRecipe.class::isInstance)
                .map(NEUUnknownRecipe.class::cast)
                .collect(Collectors.toList())
        );
    }
}
