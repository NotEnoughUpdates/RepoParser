package io.github.moulberry.repo.constants;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Enchants {

    @Getter
    @SerializedName("enchants")
    Map<String, List<String>> availableEnchants;

    @Getter
    @SerializedName("enchant_pools")
    List<List<String>> enchantPools;

    @Getter
    @SerializedName("enchants_xp_cost")
    Map<String, List<Integer>> enchantExperienceCost;

    public List<String> getAvailableEnchants(String tooltype) {
        if (availableEnchants == null) return null;
        return availableEnchants.get(tooltype);
    }

    public List<String> getConflictingEnchants(String enchant) {
        if (enchantPools == null) return new ArrayList<>();
        List<String> conflicts = new ArrayList<>();
        for (List<String> enchantPool : enchantPools) {
            if (enchantPool.contains(enchant)) {
                conflicts.addAll(enchantPool);
                conflicts.remove(enchant);
            }
        }
        return conflicts;
    }
}
