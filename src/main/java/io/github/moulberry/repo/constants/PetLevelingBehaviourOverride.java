package io.github.moulberry.repo.constants;

import com.google.gson.annotations.SerializedName;
import io.github.moulberry.repo.data.Rarity;
import lombok.Getter;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class PetLevelingBehaviourOverride {
    /**
     * Constant multiplier for all exp gains. If not present should be treated as {@code 1.0}
     */
    @Getter
    @SerializedName("xp_multiplier")
    @Nullable
    Double xpMultiplier;

    /**
     * Offset the {@link PetLevelingData#getPetExpCostForLevel() pet leveling cost} by a specified amount for each rarity.
     * This replaces the default behaviour in {@link PetLevelingData#getPetLevelStartOffset()}.
     */
    @Getter
    @SerializedName("pet_rarity_offset")
    Map<Rarity, Integer> petLevelStartOffset;

    @SerializedName("type")
    @Getter
    @ApiStatus.Obsolete
    Integer rawLevelOverrideType;

    /**
     * Override the max level of this pet.
     */
    @SerializedName("max_level")
    @Getter
    Integer maxLevel;

    /**
     * List of levels to replace or augment the default ones in {@link PetLevelingData#getPetExpCostForLevel()}
     */
    @SerializedName("pet_levels")
    @Getter
    List<Integer> petExpCostModifier;

    /**
     * Specify how {@link #getPetExpCostModifier() the pet exp cost modifier list} should be used.
     */
    @Nullable
    public PetExpModifierType getPetExpCostModifierType() {
        if (rawLevelOverrideType == null) return null;
        if (rawLevelOverrideType == 1) return PetExpModifierType.APPEND;
        if (rawLevelOverrideType == 2) return PetExpModifierType.REPLACE;
        return null;
    }

    public enum PetExpModifierType {
        /**
         * Append the data in {@link #getPetExpCostModifier()} to {@link PetLevelingData#getPetExpCostForLevel()}.
         */
        APPEND,
        /**
         * Replace the data in {@link PetLevelingData#getPetExpCostForLevel()} with {@link  #getPetExpCostModifier()}
         */
        REPLACE,
        ;
    }

}
