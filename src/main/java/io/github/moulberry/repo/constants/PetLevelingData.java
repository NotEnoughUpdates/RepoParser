package io.github.moulberry.repo.constants;

import com.google.gson.annotations.SerializedName;
import io.github.moulberry.repo.data.Rarity;
import io.github.moulberry.repo.util.PetId;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class PetLevelingData {
    /**
     * Offset the {@link #getPetExpCostForLevel() pet leveling cost} by a specified amount for each rarity.
     */
    @SerializedName("pet_rarity_offset")
    Map<Rarity, Integer> petLevelStartOffset;
    /**
     * EXP needed for each individual pet level. This cost is offset by {@link #getPetLevelStartOffset() the pet level start offset} and then
     * capped according to
     */
    @SerializedName("pet_levels")
    List<Integer> petExpCostForLevel;

    /**
     * A map of pet name to overrides in the leveling behaviour.
     */
    @SerializedName("custom_pet_leveling")
    Map<@PetId String, PetLevelingBehaviourOverride> petLevelingBehaviourOverrides;

    /**
     * A map of pet name to the kind of exp it uses.
     * Uses the standard skill names, but may include {@code ALL} or might not be present for pets that
     * do not level up via normal means.
     */
    @SerializedName("pet_types")
    Map<@PetId String, String> petExpTypes;

}
