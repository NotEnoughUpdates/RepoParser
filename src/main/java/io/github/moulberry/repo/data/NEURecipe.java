package io.github.moulberry.repo.data;

import org.jetbrains.annotations.ApiStatus;

import java.util.Collection;

public interface NEURecipe {
    /**
     * This is to be called once, after deserialization by the item that contains this recipe. This will be done
     * automatically by {@link NEUItem#getRecipes()}.
     */
    @ApiStatus.OverrideOnly
    default void fillItemInfo(NEUItem item) {
    }

    Collection<NEUIngredient> getAllInputs();

    Collection<NEUIngredient> getAllOutputs();
}
