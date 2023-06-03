package io.github.moulberry.repo.constants;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Getter
public class PetNumbers {
    /**
     * Stats at the {@link #getLowLevel()} of this curve.
     */
    @SerializedName("1")
    Stats statsAtLowLevel;
    /**
     * Stats at the {@link #getHighLevel()} of this curve.
     */
    @SerializedName("100")
    Stats statsAtHighLevel;
    @SerializedName("stats_levelling_curve")
    @ApiStatus.Obsolete
    String levelingCurve;
    private transient int minLevel, maxLevel;

    /**
     * The first level at which this pet has stats.
     */
    public int getLowLevel() {
        if (minLevel > 0) return minLevel;
        if (levelingCurve == null)
            return 1;
        String[] split = levelingCurve.split(":");
        assert split.length == 3;
        assert Objects.equals(split[2], "1");
        return minLevel = Integer.parseInt(split[0]);
    }

    /**
     * The last level at which this pet has stats.
     */
    public int getHighLevel() {
        if (maxLevel > 0) return maxLevel;
        if (levelingCurve == null)
            return 100;
        String[] split = levelingCurve.split(":");
        assert split.length == 3;
        assert Objects.equals(split[2], "1");
        return maxLevel = Integer.parseInt(split[1]);
    }

    @Nullable
    public Stats interpolatedStatsAtLevel(int level) {
        if (level < getLowLevel()) return null;
        if (level > getHighLevel()) return null;
        double progress = (level - getLowLevel()) / (double) (getHighLevel() - getLowLevel());

        Stats stats = new Stats();
        for (String stat : getStatsAtLowLevel().statNumbers.keySet()) {
            Double lowStat = getStatsAtLowLevel().getStatNumbers().get(stat);
            Double highStat = getStatsAtHighLevel().getStatNumbers().get(stat);
            stats.getStatNumbers().put(stat, lowStat + (highStat - lowStat) * progress);
        }
        Iterator<Double> lowStatIt = getStatsAtLowLevel().getOtherNumbers().iterator();
        Iterator<Double> highStatIt = getStatsAtHighLevel().getOtherNumbers().iterator();
        while (lowStatIt.hasNext() && highStatIt.hasNext()) {
            Double lowStat = lowStatIt.next();
            Double highStat = highStatIt.next();
            stats.getOtherNumbers().add(lowStat + (highStat - lowStat) * progress);
        }
        return stats;
    }

    @Getter
    @AllArgsConstructor
    @ToString
    @NoArgsConstructor
    public static class Stats {
        @SerializedName("otherNums")
        List<Double> otherNumbers = new ArrayList<>();
        @SerializedName("statNums")
        Map<String, Double> statNumbers = new HashMap<>();
    }


}
