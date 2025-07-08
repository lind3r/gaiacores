package com.seb.gaiacores.block.entity;

import com.seb.gaiacores.GaiaCores;
import com.seb.gaiacores.block.ModBlocks;
import com.seb.gaiacores.block.entity.custom.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, GaiaCores.MOD_ID);

    public static final RegistryObject<BlockEntityType<GaiaCoreAnalyzerBlockEntity>> GAIA_CORE_ANALYZER_BE =
            BLOCK_ENTITIES.register("gaia_core_analyzer_be", () -> BlockEntityType.Builder.of(
                    GaiaCoreAnalyzerBlockEntity::new, ModBlocks.GAIA_CORE_ANALYZER.get()).build(null));

    public static final RegistryObject<BlockEntityType<LucentGaiaCoreBlockEntity>> LUCENT_GAIA_CORE_BE =
            BLOCK_ENTITIES.register("lucent_gaia_core_be", () -> BlockEntityType.Builder.of(
                    LucentGaiaCoreBlockEntity::new, ModBlocks.LUCENT_GAIA_CORE.get()).build(null));

    public static final RegistryObject<BlockEntityType<VolcanicGaiaCoreBlockEntity>> VOLCANIC_GAIA_CORE_BE =
            BLOCK_ENTITIES.register("volcanic_gaia_core_be", () -> BlockEntityType.Builder.of(
                    VolcanicGaiaCoreBlockEntity::new, ModBlocks.VOLCANIC_GAIA_CORE.get()).build(null));

    public static final RegistryObject<BlockEntityType<VerdantGaiaCoreBlockEntity>> VERDANT_GAIA_CORE_BE =
            BLOCK_ENTITIES.register("verdant_gaia_core_be", () -> BlockEntityType.Builder.of(
                    VerdantGaiaCoreBlockEntity::new, ModBlocks.VERDANT_GAIA_CORE.get()).build(null));

    public static final RegistryObject<BlockEntityType<CharredGaiaCoreBlockEntity>> CHARRED_GAIA_CORE_BE =
            BLOCK_ENTITIES.register("charred_gaia_core_be", () -> BlockEntityType.Builder.of(
                    CharredGaiaCoreBlockEntity::new, ModBlocks.CHARRED_GAIA_CORE.get()).build(null));

    public static final RegistryObject<BlockEntityType<AdamantGaiaCoreBlockEntity>> ADAMANT_GAIA_CORE_BE =
            BLOCK_ENTITIES.register("adamant_gaia_core_be", () -> BlockEntityType.Builder.of(
                    AdamantGaiaCoreBlockEntity::new, ModBlocks.ADAMANT_GAIA_CORE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}