package com.seb.gaiacore.block;

import com.seb.gaiacore.GaiaCore;
import com.seb.gaiacore.block.custom.*;
import com.seb.gaiacore.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, GaiaCore.MOD_ID);

    public static final RegistryObject<Block> ENERGETIC_GAIA_CORE = registerBlock("energetic_gaia_core",
            () -> new EnergeticGaiaCore(BlockBehaviour.Properties.of()
                    .strength(4.0f, 3600000.0f)
                    .lightLevel(state -> state.getValue(GaiaCoreBase.POWERED) ? 15 : 0)));

    public static final RegistryObject<Block> VOLCANIC_GAIA_CORE = registerBlock("volcanic_gaia_core",
            () -> new VolcanicGaiaCore(BlockBehaviour.Properties.of()
                    .strength(4.0f, 3600000.0f)
                    .lightLevel(state -> state.getValue(GaiaCoreBase.POWERED) ? 15 : 0)));

    public static final RegistryObject<Block> VERDANT_GAIA_CORE = registerBlock("verdant_gaia_core",
            () -> new VerdantGaiaCore(BlockBehaviour.Properties.of()
                    .strength(4.0f, 3600000.0f)
                    .lightLevel(state -> state.getValue(GaiaCoreBase.POWERED) ? 15 : 0)));

    public static final RegistryObject<Block> GAIA_CORE_ANALYZER = registerBlock("gaia_core_analyzer",
            () -> new GaiaCoreAnalyzer(BlockBehaviour.Properties.of()
                    .strength(4.0f)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> blockSupp) {
        RegistryObject<T> block = BLOCKS.register(name, blockSupp);
        registerBlockItem(name, block);
        return block;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
