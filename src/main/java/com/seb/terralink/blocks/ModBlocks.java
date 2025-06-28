package com.seb.terralink.blocks;

import com.seb.terralink.TerraLinkMain;
import com.seb.terralink.blocks.custom.EnergeticEsotericCore;
import com.seb.terralink.items.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TerraLinkMain.MOD_ID);

    public static final RegistryObject<Block> ENERGETIC_ESOTERIC_CORE =
            registerBlockAndBlockItem("energetic_esoteric_core",
                    () -> new EnergeticEsotericCore(BlockBehaviour.Properties.of()
                            .strength(3f)
                            .noLootTable()));

    public static final RegistryObject<Block> RAW_COFFEE_BLOCK =
            registerBlockAndBlockItem("raw_coffee_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(3f)
                    .requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> COFFEE_BLOCK =
            registerBlockAndBlockItem("coffee_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> COFFEE_ORE =
            registerBlockAndBlockItem("coffee_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of()
                    .strength(3f)
                    .requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> COFFEE_DEEPSLATE_ORE =
            registerBlockAndBlockItem("coffee_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(4, 8),
                    BlockBehaviour.Properties.of()
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)));

    private static <T extends Block> RegistryObject<T> registerBlockAndBlockItem(String name, Supplier<T> blockSupp) {
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
