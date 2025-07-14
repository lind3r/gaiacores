package com.seb.gaiacores.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record CharredGaiaCoreRecipeInput(ItemStack input) implements RecipeInput {

    @Override
    public ItemStack getItem(int pIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 0;
    }
}
