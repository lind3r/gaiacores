package com.seb.gaiacores.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record VerdantGaiaCoreRecipeInput(ItemStack first, ItemStack second) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        return index == 0 ? first : second;
    }

    @Override
    public int size() {
        return 2;
    }
}
