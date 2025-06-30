package com.seb.gaiacore.worldgen;

import com.seb.gaiacore.GaiaCore;
import com.seb.gaiacore.blocks.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

// Configured Features (CF) is kind of the composition of your stuff, e.g 3-5 iron ore in this random pattern
// Placed Features is how many, at what Y level and so on
// Biome Modifiers is in what biome I guess

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ENERGETIC_GAIA_CORE_KEY = registerKey("energetic_gaia_core");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VOLCANIC_GAIA_CORE_KEY = registerKey("volcanic_gaia_core");
//    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_ENERGETIC_GAIA_CORE_KEY = registerKey("nether_energetic_gaia_core");
//    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_VOLCANIC_GAIA_CORE_KEY = registerKey("nether_volcanic_gaia_core");
//    public static final ResourceKey<ConfiguredFeature<?, ?>> END_ENERGETIC_GAIA_CORE_KEY = registerKey("end_energetic_gaia_core");
//    public static final ResourceKey<ConfiguredFeature<?, ?>> END_VOLCANIC_GAIA_CORE_KEY = registerKey("end_volcanic_gaia_core");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
//        RuleTest netherrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);
//        RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);

        register(context, ENERGETIC_GAIA_CORE_KEY, ModFeatures.GAIA_ORE_FEATURE.get(), new OreConfiguration(stoneReplaceables,
                ModBlocks.ENERGETIC_GAIA_CORE.get().defaultBlockState(), 1));
        register(context, VOLCANIC_GAIA_CORE_KEY, Feature.ORE, new OreConfiguration(deepslateReplaceables,
                ModBlocks.VOLCANIC_GAIA_CORE.get().defaultBlockState(), 1));
//        register(context, NETHER_ALEXANDRITE_ORE_KEY, Feature.ORE, new OreConfiguration(netherrackReplaceables,
//                ModBlocks.ALEXANDRITE_NETHER_ORE.get().defaultBlockState(), 9));
//        register(context, END_ALEXANDRITE_ORE_KEY, Feature.ORE, new OreConfiguration(endReplaceables,
//                ModBlocks.ALEXANDRITE_END_ORE.get().defaultBlockState(), 9));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
