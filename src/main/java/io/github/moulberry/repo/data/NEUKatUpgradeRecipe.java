package io.github.moulberry.repo.data;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NEUKatUpgradeRecipe implements NEURecipe {
    /**
     * Base pet.
     */
    @Getter
    NEUIngredient input;
    /**
     * Upgraded pet.
     */
    @Getter
    NEUIngredient output;
    /**
     * List of bonus items you need to upgrade, not including the pet itself.
     */
    @Getter
    List<NEUIngredient> items;
    /**
     * Coin cost for a level "0" pet. This cost is reduced for each level, so even a level 1 pet costs less to upgrade than this.
     */
    @Getter
    double coins;
    /**
     * Time for the pet upgrade in seconds.
     */
    @Getter
    long seconds;

    @Override
    public Collection<NEUIngredient> getAllInputs() {
        List<NEUIngredient> inputs = new ArrayList<>();
        inputs.add(input);
        inputs.addAll(items);
        inputs.add(NEUIngredient.ofCoins(coins));
        return inputs;
    }

    @Override
    public Collection<NEUIngredient> getAllOutputs() {
        return Collections.singleton(output);
    }
}
