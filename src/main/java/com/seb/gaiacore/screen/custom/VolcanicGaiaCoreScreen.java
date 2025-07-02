package com.seb.gaiacore.screen.custom;

import com.seb.gaiacore.GaiaCore;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class VolcanicGaiaCoreScreen extends GaiaCoreBaseScreen<VolcanicGaiaCoreMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID, "textures/gui/volcanic_gaia_core/volcanic_gaia_core_gui.png");


    public VolcanicGaiaCoreScreen(VolcanicGaiaCoreMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, GUI_TEXTURE, ARROW_TEXTURE);
    }
}
