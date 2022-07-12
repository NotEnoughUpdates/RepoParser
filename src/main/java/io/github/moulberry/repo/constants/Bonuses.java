package io.github.moulberry.repo.constants;

import com.google.gson.annotations.SerializedName;
import io.github.moulberry.repo.data.Rarity;
import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Bonuses {

    @Getter
    @SerializedName("pet_rewards")
    Map<Integer, Map<String, Float>> petRewards;
    @Getter
    @SerializedName("pet_value")
    Map<Rarity, Integer> petValue;
    @Getter
    @SerializedName("bonus_stats")
    Map<String, Map<Integer, Map<String, Float>>> bonusStats;

    public int getPetValue(Rarity rarity) {
        return petValue.getOrDefault(rarity, 0);
    }

    public Map<String, Float> getPetRewards(int score) {
        return petRewards.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .filter(it -> it.getKey() <= score)
                .map(Map.Entry::getValue)
                .reduce(new HashMap<>(), (a, b) -> {
                    a.putAll(b);
                    return a;
                });
    }

    public Map<String, Float> getAccumulativeLevelingRewards(String name, int level) {
        Map<Integer, Map<String, Float>> rewardTiers = bonusStats.getOrDefault(name, new HashMap<>());
        Map<String, Float> rewards = new HashMap<>();
        Map<String, Float> currentBonuses = new HashMap<>();
        for (int i = 0; i <= level; i++) {
            currentBonuses = rewardTiers.getOrDefault(i, currentBonuses);
            for (Map.Entry<String, Float> reward : currentBonuses.entrySet()) {
                rewards.put(reward.getKey(), rewards.getOrDefault(reward.getKey(), 0F) + reward.getValue());
            }
        }
        return rewards;
    }

}
