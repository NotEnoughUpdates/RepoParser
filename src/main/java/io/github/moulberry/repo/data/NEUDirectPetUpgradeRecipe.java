package io.github.moulberry.repo.data;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NEUDirectPetUpgradeRecipe implements NEURecipe {
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
     * Item you need to upgrade, not including the pet itself.
     */
    @Getter
    NEUIngredient item;

    @Override
    public Collection<NEUIngredient> getAllInputs() {
        List<NEUIngredient> inputs = new ArrayList<>();
        inputs.add(input);
        inputs.add(item);
        return inputs;
    }

    @Override
    public Collection<NEUIngredient> getAllOutputs() {
        return Collections.singleton(output);
    }
}
