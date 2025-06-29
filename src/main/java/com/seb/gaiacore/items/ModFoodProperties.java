package com.seb.gaiacore.items;

import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties COFFEE = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.25f)
            .build();
}
