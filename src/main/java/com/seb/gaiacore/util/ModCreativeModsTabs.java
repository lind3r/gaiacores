package com.seb.gaiacore.util;

import com.seb.gaiacore.GaiaCore;
import com.seb.gaiacore.blocks.ModBlocks;
import com.seb.gaiacore.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModsTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GaiaCore.MOD_ID);

    public static final RegistryObject<CreativeModeTab> COFFEE_ITEMS_TAB = CREATIVE_MODE_TABS
            .register("coffee_items_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.COFFEE.get()))
                    .title(Component.translatable("creativetab.gaiacore.items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.COFFEE.get());
                        output.accept(ModItems.GAIA_SCANNER.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> COFFEE_BLOCKS_TAB = CREATIVE_MODE_TABS
        .register("coffee_blocks_tab", () -> CreativeModeTab.builder()
                .withTabsBefore(COFFEE_ITEMS_TAB.getId())
                .icon(() -> new ItemStack(ModBlocks.ENERGETIC_GAIA_CORE.get()))
                .title(Component.translatable("creativetab.gaiacore.blocks"))
                .displayItems((itemDisplayParameters, output) -> {
                    output.accept(ModBlocks.ENERGETIC_GAIA_CORE.get());
                }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
