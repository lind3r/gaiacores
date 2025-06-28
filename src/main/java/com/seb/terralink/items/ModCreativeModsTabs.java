package com.seb.terralink.items;

import com.seb.terralink.TerraLinkMain;
import com.seb.terralink.blocks.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModsTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TerraLinkMain.MOD_ID);

    public static final RegistryObject<CreativeModeTab> COFFEE_ITEMS_TAB = CREATIVE_MODE_TABS
            .register("coffee_items_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.COFFEE.get()))
                    .title(Component.translatable("creativetab.terralink.coffe_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.COFFEE.get());
                        output.accept(ModItems.RAW_COFFEE.get());
                        output.accept(ModItems.ESOTERIC_SCANNER.get());
                        output.accept(ModItems.COOL_FUEL.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> COFFEE_BLOCKS_TAB = CREATIVE_MODE_TABS
        .register("coffee_blocks_tab", () -> CreativeModeTab.builder()
                .withTabsBefore(COFFEE_ITEMS_TAB.getId())
                .icon(() -> new ItemStack(ModBlocks.COFFEE_BLOCK.get()))
                .title(Component.translatable("creativetab.terralink.coffe_blocks"))
                .displayItems((itemDisplayParameters, output) -> {
                    output.accept(ModBlocks.COFFEE_BLOCK.get());
                    output.accept(ModBlocks.RAW_COFFEE_BLOCK.get());
                    output.accept(ModBlocks.COFFEE_ORE.get());
                    output.accept(ModBlocks.COFFEE_DEEPSLATE_ORE.get());
                    output.accept(ModBlocks.ENERGETIC_ESOTERIC_CORE.get());
                }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
