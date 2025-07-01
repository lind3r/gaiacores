package com.seb.gaiacore.blocks.entity;

import com.seb.gaiacore.GaiaCore;
import com.seb.gaiacore.blocks.ModBlocks;
import com.seb.gaiacore.blocks.entity.custom.GaiaCoreAnalyzerBlockEntity;
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

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}