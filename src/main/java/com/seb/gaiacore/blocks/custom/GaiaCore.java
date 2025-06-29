package com.seb.gaiacore.blocks.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.network.chat.Component;

import java.util.List;

public abstract class GaiaCore extends Block {
    public GaiaCore(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.gaiacore.gaia_core"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.gaiacore.hold_shift"));
        }

        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
