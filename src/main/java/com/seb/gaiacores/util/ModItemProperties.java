package com.seb.gaiacores.util;

import com.seb.gaiacores.component.ModDataComponentTypes;
import com.seb.gaiacores.GaiaCores;
import com.seb.gaiacores.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModItemProperties {

    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.GAIA_CORE_SCANNER.get(), ResourceLocation.fromNamespaceAndPath(GaiaCores.MOD_ID, "used"),
                (itemStack, clientLevel, livingEntity, i)
                        -> itemStack.get(ModDataComponentTypes.BLOCK_TRANSLATION_KEY.get()) != null ? 1f : 0f);
    }

}
