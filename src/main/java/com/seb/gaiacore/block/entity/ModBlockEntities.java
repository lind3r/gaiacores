package com.seb.gaiacore.block.entity;

import com.seb.gaiacore.GaiaCore;
import com.seb.gaiacore.block.ModBlocks;
import com.seb.gaiacore.block.entity.custom.EnergeticGaiaCoreBlockEntity;
import com.seb.gaiacore.block.entity.custom.GaiaCoreAnalyzerBlockEntity;
import com.seb.gaiacore.block.entity.custom.VolcanicGaiaCoreBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, GaiaCore.MOD_ID);

    public static final RegistryObject<BlockEntityType<GaiaCoreAnalyzerBlockEntity>> GAIA_CORE_ANALYZER_BE =
            BLOCK_ENTITIES.register("gaia_core_analyzer_be", () -> BlockEntityType.Builder.of(
                    GaiaCoreAnalyzerBlockEntity::new, ModBlocks.GAIA_CORE_ANALYZER.get()).build(null));

    public static final RegistryObject<BlockEntityType<EnergeticGaiaCoreBlockEntity>> ENERGETIC_GAIA_CORE_BE =
            BLOCK_ENTITIES.register("energetic_gaia_core_be", () -> BlockEntityType.Builder.of(
                    EnergeticGaiaCoreBlockEntity::new, ModBlocks.ENERGETIC_GAIA_CORE.get()).build(null));

    public static final RegistryObject<BlockEntityType<VolcanicGaiaCoreBlockEntity>> VOLCANIC_GAIA_CORE_BE =
            BLOCK_ENTITIES.register("volcanic_gaia_core_be", () -> BlockEntityType.Builder.of(
                    VolcanicGaiaCoreBlockEntity::new, ModBlocks.VOLCANIC_GAIA_CORE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}