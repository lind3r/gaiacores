package com.seb.gaiacore.worldgen;

import com.seb.gaiacore.GaiaCore;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_ENERGETIC_GAIA_CORE = registerKey("add_energetic_gaia_core");
    public static final ResourceKey<BiomeModifier> ADD_VOLCANIC_GAIA_CORE = registerKey("add_volcanic_gaia_core");
//    public static final ResourceKey<BiomeModifier> ADD_END_ALEXANDRITE_ORE = registerKey("add_end_alexandrite_ore");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeature = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

//         Individual Biomes Example
//         context.register(ADD_ALEXANDRITE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
//                 HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS), biomes.getOrThrow(Biomes.BAMBOO_JUNGLE)),
//                 HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.ALEXANDRITE_ORE_PLACED_KEY)),
//                 GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_ENERGETIC_GAIA_CORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.ENERGETIC_GAIA_CORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_VOLCANIC_GAIA_CORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_NETHER),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.VOLCANIC_GAIA_CORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

//        context.register(ADD_END_ALEXANDRITE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
//                biomes.getOrThrow(BiomeTags.IS_END),
//                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.END_ALEXANDRITE_ORE_PLACED_KEY)),
//                GenerationStep.Decoration.UNDERGROUND_ORES));

    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation
                .fromNamespaceAndPath(GaiaCore.MOD_ID, name));
    }
}