package io.github.moulberry.repo.constants;

import com.google.gson.annotations.SerializedName;
import io.github.moulberry.repo.data.NEUItem;
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
     * A map from {@code /locraw} mode name to a display name for that zone.
     */
    @SerializedName("area_names")
    Map<String, String> areaNames;

    /**
     * A map from API rank names to their respective renderers.
     */
    Map<String, RankData> ranks;
    /**
     * A list of dash-less UUIDs of users with rainbow names in the profile viewer.
     */
    @SerializedName("special_bois")
    List<String> rainbowNames;
    /**
     * A map from minion name without the {@code _TIER} postfix (e.g. {@code ACACIA_GENERATOR}) to the maximum level
     * for that minion.
     */
    @SerializedName("minions")
    Map<String, Integer> maxMinionLevel;
    /**
     * The credit item for the NEU mod.
     */
    NEUItem credits;
    /**
     * A list of talismans along with their upgrades. This list is not transitive (or rather talisman upgrades are
     * transitive, but this list has already resolved these transitive upgrades).
     */
    @SerializedName("talisman_upgrades")
    Map<String, List<String>> talismanUpgrades;
}
