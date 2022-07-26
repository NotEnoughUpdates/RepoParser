package io.github.moulberry.repo.data;

import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class NEUNpcShopRecipe implements NEURecipe {

    List<NEUIngredient> cost;
    NEUIngredient result;
    NEUItem isSoldBy;

    @Override
    public void fillItemInfo(NEUItem item) {
        isSoldBy = item;
    }

    @Override
    public Collection<NEUIngredient> getAllInputs() {
        return cost;
    }

    @Override
    public Collection<NEUIngredient> getAllOutputs() {
        return Collections.singletonList(result);
    }
}
