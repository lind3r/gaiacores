package com.seb.gaiacore.blocks.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class VolcanicGaiaCore extends GaiaCoreBase {
    Variant variant;

    public VolcanicGaiaCore(Properties properties) {
        super(properties);
        this.variant = Variant.VOLCANIC;
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable(""));
            pTooltipComponents.add(Component.translatable("tooltip.gaiacore.volcanic_gaia_core"));
        }
    }

//    @Override
//    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
//        if (pEntity instanceof ItemEntity itemEntity) {
//            if (isValidItem(itemEntity.getItem())) {
//                itemEntity.setItem(new ItemStack(Items.DIAMOND, itemEntity.getItem().getCount()));
//                pLevel.playSound(null, pPos, SoundEvents.AMETHYST_CLUSTER_HIT, SoundSource.BLOCKS, 1f, 1f);
//            }
//        }
//
//        super.stepOn(pLevel, pPos, pState, pEntity);
//    }
//
//    private boolean isValidItem(ItemStack itemStack) {
//        return itemStack.is(ModTags.Items.TRANSFORMABLE_ITEMS);
//    }
}
