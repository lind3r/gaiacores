package com.seb.gaiacore.item;

import com.seb.gaiacore.GaiaCore;
import com.seb.gaiacore.item.custom.GaiaScannerItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, GaiaCore.MOD_ID);

    public static final RegistryObject<Item> GAIA_SCANNER = ITEMS.register("gaia_scanner",
            () -> new GaiaScannerItem(new Item.Properties().durability(32)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
