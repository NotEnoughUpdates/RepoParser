package io.github.moulberry.repo.constants;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class Leveling {
    @SerializedName("leveling_xp")
    List<Integer> skillExperienceRequiredPerLevel;

    @SerializedName("leveling_caps")
    Map<String, Integer> maximumLevels;

    @SerializedName("runecrafting_xp")
    List<Integer> runecraftingExperienceRequiredPerLevel;

    @SerializedName("slayer_xp")
    Map<String, List<Integer>> slayerExperienceRequiredPerLevel;

    @SerializedName("slayer_boss_xp")
    List<Integer> slayerExperiencePerBoss;

    @SerializedName("catacombs")
    List<Integer> catacombsExperienceRequiredPerLevel;

    @SerializedName("HOTM")
    List<Integer> hotmExperienceRequiredPerLevel;

    @SerializedName("social")
    List<Integer> socialExperienceRequiredPerLevel;

    // TODO: bestiary (because this is a garbage format)

}
