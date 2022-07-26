package io.github.moulberry.repo.data;

import java.util.Collection;

public interface NEURecipe {
    /**
     * This is to be called once, after deserialization by the item that contains this recipe. This will be done
     * automatically by {@link NEUItem#getRecipes()}.
     */
    @Deprecated
    default void fillItemInfo(NEUItem item) {
    }

    Collection<NEUIngredient> getAllInputs();

    Collection<NEUIngredient> getAllOutputs();
}
