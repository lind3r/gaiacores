package com.seb.gaiacores.worldgen;

import com.seb.gaiacores.GaiaCores;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, GaiaCores.MOD_ID);

    public static final RegistryObject<Feature<OreConfiguration>> GAIA_ORE_FEATURE =
            FEATURES.register("gaia_core_ore", () -> new GaiaCoreOreFeature(OreConfiguration.CODEC));
}
