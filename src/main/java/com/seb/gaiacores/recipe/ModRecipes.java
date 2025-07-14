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

    public static final RegistryObject<RecipeSerializer<VerdantGaiaCoreRecipe>> VERDANT_GAIA_CORE_SERIALIZER =
            SERIALIZERS.register("verdant_gaia_core", VerdantGaiaCoreRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<VerdantGaiaCoreRecipe>> VERDANT_GAIA_CORE_TYPE =
            TYPES.register("verdant_gaia_core", () -> new RecipeType<VerdantGaiaCoreRecipe>() {
                @Override
                public String toString() { return "verdant_gaia_core"; }
            });

    public static final RegistryObject<RecipeSerializer<VolcanicGaiaCoreRecipe>> VOLCANIC_GAIA_CORE_SERIALIZER =
            SERIALIZERS.register("volcanic_gaia_core", VolcanicGaiaCoreRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<VolcanicGaiaCoreRecipe>> VOLCANIC_GAIA_CORE_TYPE =
            TYPES.register("volcanic_gaia_core", () -> new RecipeType<VolcanicGaiaCoreRecipe>() {
                @Override
                public String toString() { return "volcanic_gaia_core";
                }
            });

    public static final RegistryObject<RecipeSerializer<CharredGaiaCoreRecipe>> CHARRED_GAIA_CORE_SERIALIZER =
            SERIALIZERS.register("charred_gaia_core", CharredGaiaCoreRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<CharredGaiaCoreRecipe>> CHARRED_GAIA_CORE_TYPE =
            TYPES.register("charred_gaia_core", () -> new RecipeType<CharredGaiaCoreRecipe>() {
                @Override
                public String toString() { return "charred_gaia_core";
                }
            });

    public static final RegistryObject<RecipeSerializer<AdamantGaiaCoreRecipe>> ADAMANT_GAIA_CORE_SERIALIZER =
            SERIALIZERS.register("adamant_gaia_core", AdamantGaiaCoreRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<AdamantGaiaCoreRecipe>> ADAMANT_GAIA_CORE_TYPE =
            TYPES.register("adamant_gaia_core", () -> new RecipeType<AdamantGaiaCoreRecipe>() {
                @Override
                public String toString() { return "adamant_gaia_core";
                }
            });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}