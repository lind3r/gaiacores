package com.seb.gaiacores.worldgen;

import com.seb.gaiacores.GaiaCores;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> LUCENT_GAIA_CORE_PLACED_KEY = registerKey("lucent_gaia_core_placed");
    public static final ResourceKey<PlacedFeature> VOLCANIC_GAIA_CORE_PLACED_KEY = registerKey("volcanic_gaia_core_placed");
    public static final ResourceKey<PlacedFeature> VERDANT_GAIA_CORE_PLACED_KEY = registerKey("verdant_gaia_core_placed");
    public static final ResourceKey<PlacedFeature> CHARRED_GAIA_CORE_PLACED_KEY = registerKey("charred_gaia_core_placed");
    public static final ResourceKey<PlacedFeature> ADAMANT_GAIA_CORE_PLACED_KEY = registerKey("adamant_gaia_core_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, LUCENT_GAIA_CORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.LUCENT_GAIA_CORE_KEY),
                ModOrePlacement.rareOrePlacement(1, HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, VOLCANIC_GAIA_CORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.VOLCANIC_GAIA_CORE_KEY),
                ModOrePlacement.rareOrePlacement(1, HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(-64), VerticalAnchor.absolute(110))));

        register(context, VERDANT_GAIA_CORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.VERDANT_GAIA_CORE_KEY),
                ModOrePlacement.rareOrePlacement(1, HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, CHARRED_GAIA_CORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.CHARRED_GAIA_CORE_KEY),
                ModOrePlacement.rareOrePlacement(1, HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, ADAMANT_GAIA_CORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ADAMANT_GAIA_CORE_KEY),
                ModOrePlacement.rareOrePlacement(1, HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(GaiaCores.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}