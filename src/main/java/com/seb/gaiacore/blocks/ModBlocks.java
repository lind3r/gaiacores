package com.seb.gaiacore.blocks;

import com.seb.gaiacore.GaiaCore;
import com.seb.gaiacore.blocks.custom.EnergeticGaiaCore;
import com.seb.gaiacore.items.ModItems;
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
            () -> new EnergeticGaiaCore(BlockBehaviour.Properties.of().strength(3f).noLootTable()));

//    public static final RegistryObject<Block> RAW_COFFEE_BLOCK = registerBlock("raw_coffee_block",
//            () -> new Block(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
//    public static final RegistryObject<Block> COFFEE_BLOCK = registerBlock("coffee_block",
//            () -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));
//
//    public static final RegistryObject<Block> COFFEE_ORE = registerBlock("coffee_ore",
//            () -> new DropExperienceBlock(UniformInt.of(2, 4), BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
//    public static final RegistryObject<Block> COFFEE_DEEPSLATE_ORE = registerBlock("coffee_deepslate_ore",
//            () -> new DropExperienceBlock(UniformInt.of(4, 8), BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
//
//    public static final RegistryObject<Block> ALEXANDRITE = registerBlock("alexandrite",
//            () -> new Block(BlockBehaviour.Properties.of().strength(3f)));
//
//
//    public static final RegistryObject<StairBlock> ALEXANDRITE_STAIRS = registerBlock("alexandrite_stairs",
//            () -> new StairBlock(ModBlocks.ALEXANDRITE.get().defaultBlockState(),
//                    BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
//    public static final RegistryObject<SlabBlock> ALEXANDRITE_SLAB = registerBlock("alexandrite_slab",
//            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
//
//    public static final RegistryObject<PressurePlateBlock> ALEXANDRITE_PRESSURE_PLATE = registerBlock("alexandrite_pressure_plate",
//            () -> new PressurePlateBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
//    public static final RegistryObject<ButtonBlock> ALEXANDRITE_BUTTON = registerBlock("alexandrite_button",
//            () -> new ButtonBlock(BlockSetType.IRON,1, BlockBehaviour.Properties.of().strength(3f)
//                    .requiresCorrectToolForDrops().noCollission()));
//
//    public static final RegistryObject<FenceBlock> ALEXANDRITE_FENCE = registerBlock("alexandrite_fence",
//            () -> new FenceBlock(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
//    public static final RegistryObject<FenceGateBlock> ALEXANDRITE_FENCE_GATE = registerBlock("alexandrite_fence_gate",
//            () -> new FenceGateBlock(WoodType.ACACIA, BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
//    public static final RegistryObject<WallBlock> ALEXANDRITE_WALL = registerBlock("alexandrite_wall",
//            () -> new WallBlock(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops()));
//
//    public static final RegistryObject<DoorBlock> ALEXANDRITE_DOOR = registerBlock("alexandrite_door",
//            () -> new DoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().noOcclusion()));
//    public static final RegistryObject<TrapDoorBlock> ALEXANDRITE_TRAPDOOR = registerBlock("alexandrite_trapdoor",
//            () -> new TrapDoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().noOcclusion()));


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
