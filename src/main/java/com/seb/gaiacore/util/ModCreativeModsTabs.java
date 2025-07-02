package com.seb.gaiacore.util;

import com.seb.gaiacore.GaiaCore;
import com.seb.gaiacore.block.ModBlocks;
import com.seb.gaiacore.item.ModItems;
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

    public static final RegistryObject<CreativeModeTab> GAIACORE_ITEMS_TAB = CREATIVE_MODE_TABS
            .register("gaiacore_items_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.GAIA_SCANNER.get()))
                    .title(Component.translatable("creativetab.gaiacore.items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.GAIA_SCANNER.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> GAIACORE_BLOCKS_TAB = CREATIVE_MODE_TABS
        .register("gaiacore_blocks_tab", () -> CreativeModeTab.builder()
                .withTabsBefore(GAIACORE_ITEMS_TAB.getId())
                .icon(() -> new ItemStack(ModBlocks.ENERGETIC_GAIA_CORE.get()))
                .title(Component.translatable("creativetab.gaiacore.blocks"))
                .displayItems((itemDisplayParameters, output) -> {
                    output.accept(ModBlocks.ENERGETIC_GAIA_CORE.get());
                    output.accept(ModBlocks.VOLCANIC_GAIA_CORE.get());
                    output.accept(ModBlocks.GAIA_CORE_ANALYZER.get());
                }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
