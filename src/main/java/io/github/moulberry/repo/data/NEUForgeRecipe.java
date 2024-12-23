package io.github.moulberry.repo.data;


import lombok.Getter;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NEUForgeRecipe implements NEURecipe {
    @Getter
    List<NEUIngredient> inputs;
    int count;
    String overrideOutputId;
    @Getter
    int duration;
    @Getter
    transient NEUIngredient outputStack;
    @Getter
    @Nullable
    String extraText;


    @Override
    public void fillItemInfo(NEUItem item) {
        if (extraText == null) {
            extraText = item.crafttext;
        }
        if (overrideOutputId == null) {
            outputStack = NEUIngredient.fromString(item.skyblockItemId);
        } else {
            outputStack = NEUIngredient.fromString(overrideOutputId);
        }
        if (count > 0) {
            outputStack.amount = count;
        }
    }

    @Override
    public Collection<NEUIngredient> getAllOutputs() {
        return Collections.singletonList(outputStack);
    }

    @Override
    public Collection<NEUIngredient> getAllInputs() {
        return inputs;
    }
}
