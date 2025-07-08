package com.seb.gaiacores.item;

import com.seb.gaiacores.GaiaCores;
import com.seb.gaiacores.item.custom.GaiaCoreScannerItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, GaiaCores.MOD_ID);

    public static final RegistryObject<Item> GAIA_CORE_SCANNER = ITEMS.register("gaia_core_scanner",
            () -> new GaiaCoreScannerItem(new Item.Properties().durability(32)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
