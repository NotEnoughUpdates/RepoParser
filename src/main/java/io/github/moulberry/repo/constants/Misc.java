package io.github.moulberry.repo.constants;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class Misc {
    // TODO: (necessary?)
    // - item types
    // - tier colors
    // - base stats
    // - cosmetics info
    // - features list
    // - minion cost
    /**
     * A list of the cost to start a slayer of that tier.
     */
    @SerializedName("slayer_cost")
    List<String> slayerCost;

    /**
     * A map from API rank names to their respective renderers.
     */
    Map<String, RankData> ranks;
    /**
     * A map from minion name without the {@code _TIER} postfix (e.g. {@code ACACIA_GENERATOR}) to the maximum level
     * for that minion.
     */
    @SerializedName("minions")
    Map<String, Integer> maxMinionLevel;
    /**
     * A list of talismans along with their upgrades. This list is not transitive (or rather talisman upgrades are
     * transitive, but this list has already resolved these transitive upgrades).
     */
    @SerializedName("talisman_upgrades")
    Map<String, List<String>> talismanUpgrades;
    /**
     * A list of talismans that should be ignored because they are from the rift.
     */
    @SerializedName("ignored_talisman")
    List<String> ignoredTalismans;
}
