package io.github.moulberry.repo.data;

import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

@Getter
public class NEUTradeRecipe implements NEURecipe {
    NEUIngredient cost;
    int min;
    int max;
    NEUIngredient result;

    @Override
    public Collection<NEUIngredient> getAllInputs() {
        return Collections.singletonList(cost);
    }

    @Override
    public Collection<NEUIngredient> getAllOutputs() {
        return Collections.singletonList(result);
    }
}
