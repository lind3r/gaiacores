package com.seb.gaiacores.recipe;

import com.seb.gaiacores.GaiaCores;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, GaiaCores.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, GaiaCores.MOD_ID);

    public static final RegistryObject<RecipeSerializer<LucentGaiaCoreRecipe>> LUCENT_GAIA_CORE_SERIALIZER =
            SERIALIZERS.register("lucent_gaia_core", LucentGaiaCoreRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<LucentGaiaCoreRecipe>> LUCENT_GAIA_CORE_TYPE =
            TYPES.register("lucent_gaia_core", () -> new RecipeType<LucentGaiaCoreRecipe>() {
                @Override
                public String toString() {
                    return "lucent_gaia_core";
                }
            });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}