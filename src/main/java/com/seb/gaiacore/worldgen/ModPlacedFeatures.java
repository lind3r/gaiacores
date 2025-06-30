package com.seb.gaiacore.worldgen;

import com.seb.gaiacore.GaiaCore;
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
    public static final ResourceKey<PlacedFeature> ENERGETIC_GAIA_CORE_PLACED_KEY = registerKey("energetic_gaia_core_placed");
    public static final ResourceKey<PlacedFeature> VOLCANIC_GAIA_CORE_PLACED_KEY = registerKey("volcanic_gaia_core_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, ENERGETIC_GAIA_CORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ENERGETIC_GAIA_CORE_KEY),
                ModOrePlacement.rareOrePlacement(2, HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, VOLCANIC_GAIA_CORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.VOLCANIC_GAIA_CORE_KEY),
                ModOrePlacement.rareOrePlacement(2, HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}