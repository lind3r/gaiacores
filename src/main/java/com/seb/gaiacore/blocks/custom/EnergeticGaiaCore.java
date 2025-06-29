package com.seb.gaiacore.blocks.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class EnergeticGaiaCore extends GaiaCoreBase {
    Variant variant;

    public EnergeticGaiaCore(Properties properties) {
        super(properties);
        this.variant = Variant.ENERGETIC;
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable(""));
            pTooltipComponents.add(Component.translatable("tooltip.gaiacore.energetic_gaia_core"));
        }
    }
}
