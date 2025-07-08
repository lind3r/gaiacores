package com.seb.gaiacores.recipe;

import com.seb.gaiacores.GaiaCores;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, GaiaCores.MOD_ID);

    public static void register(IEventBus eventBus) {
        TYPES.register(eventBus);
    }
}