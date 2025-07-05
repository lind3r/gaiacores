package com.seb.gaiacore.recipe;

import com.seb.gaiacore.GaiaCore;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, GaiaCore.MOD_ID);

    public static final RegistryObject<RecipeType<LucentGaiaCoreRecipe>> GAIA_CORE_TYPE =
            TYPES.register("gaia_core", () -> new RecipeType<LucentGaiaCoreRecipe>() {
                @Override
                public String toString() { return "gaia_core"; }
            });

    public static void register(IEventBus eventBus) {
        TYPES.register(eventBus);
    }
}