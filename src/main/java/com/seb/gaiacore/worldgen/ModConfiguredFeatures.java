package com.seb.gaiacore.worldgen;

import com.seb.gaiacore.GaiaCore;
import com.seb.gaiacore.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUCENT_GAIA_CORE_KEY = registerKey("lucent_gaia_core");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VOLCANIC_GAIA_CORE_KEY = registerKey("volcanic_gaia_core");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VERDANT_GAIA_CORE_KEY = registerKey("verdant_gaia_core");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHARRED_GAIA_CORE_KEY = registerKey("charred_gaia_core");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ADAMANT_GAIA_CORE_KEY = registerKey("adamant_gaia_core");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        register(context, LUCENT_GAIA_CORE_KEY, ModFeatures.GAIA_ORE_FEATURE.get(), new OreConfiguration(stoneReplaceables,
                ModBlocks.LUCENT_GAIA_CORE.get().defaultBlockState(), 1));
        register(context, VOLCANIC_GAIA_CORE_KEY, ModFeatures.GAIA_ORE_FEATURE.get(), new OreConfiguration(deepslateReplaceables,
                ModBlocks.VOLCANIC_GAIA_CORE.get().defaultBlockState(), 1));
        register(context, VERDANT_GAIA_CORE_KEY, ModFeatures.GAIA_ORE_FEATURE.get(), new OreConfiguration(deepslateReplaceables,
                ModBlocks.VERDANT_GAIA_CORE.get().defaultBlockState(), 1));
        register(context, CHARRED_GAIA_CORE_KEY, ModFeatures.GAIA_ORE_FEATURE.get(), new OreConfiguration(deepslateReplaceables,
                ModBlocks.CHARRED_GAIA_CORE.get().defaultBlockState(), 1));
        register(context, ADAMANT_GAIA_CORE_KEY, ModFeatures.GAIA_ORE_FEATURE.get(), new OreConfiguration(deepslateReplaceables,
                ModBlocks.ADAMANT_GAIA_CORE.get().defaultBlockState(), 1));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
