package com.seb.gaiacores.util;

import com.seb.gaiacores.GaiaCores;
import com.seb.gaiacores.block.ModBlocks;
import com.seb.gaiacores.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModsTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GaiaCores.MOD_ID);

    public static final RegistryObject<CreativeModeTab> GAIACORE_ITEMS_TAB = CREATIVE_MODE_TABS
            .register("gaiacores_items_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.GAIA_CORE_SCANNER.get()))
                    .title(Component.translatable("creativetab.gaiacores.items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.GAIA_CORE_SCANNER.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> GAIACORE_BLOCKS_TAB = CREATIVE_MODE_TABS
        .register("gaiacores_blocks_tab", () -> CreativeModeTab.builder()
                .withTabsBefore(GAIACORE_ITEMS_TAB.getId())
                .icon(() -> new ItemStack(ModBlocks.LUCENT_GAIA_CORE.get()))
                .title(Component.translatable("creativetab.gaiacores.blocks"))
                .displayItems((itemDisplayParameters, output) -> {
                    output.accept(ModBlocks.GAIA_CORE_ANALYZER.get());
                    output.accept(ModBlocks.LUCENT_GAIA_CORE.get());
                    output.accept(ModBlocks.VOLCANIC_GAIA_CORE.get());
                    output.accept(ModBlocks.VERDANT_GAIA_CORE.get());
                    output.accept(ModBlocks.CHARRED_GAIA_CORE.get());
                    output.accept(ModBlocks.ADAMANT_GAIA_CORE.get());
                }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
