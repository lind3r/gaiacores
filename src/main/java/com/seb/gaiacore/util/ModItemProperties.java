package com.seb.gaiacore.util;

import com.seb.gaiacore.component.ModDataComponentTypes;
import com.seb.gaiacore.GaiaCore;
import com.seb.gaiacore.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModItemProperties {

    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.GAIA_SCANNER.get(), ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID, "used"),
                (itemStack, clientLevel, livingEntity, i)
                        -> itemStack.get(ModDataComponentTypes.BLOCK_TRANSLATION_KEY.get()) != null ? 1f : 0f);
    }

}
