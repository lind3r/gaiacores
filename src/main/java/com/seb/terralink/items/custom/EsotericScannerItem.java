package com.seb.terralink.items.custom;

import com.seb.terralink.blocks.ModBlocks;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Map;

public class EsotericScannerItem extends Item {
    public EsotericScannerItem(Properties pProperties) {
        super(pProperties);
    }

    private static final Map<Block, Block> CHISEL_MAP =
            Map.of(
                    Blocks.STONE, Blocks.STONE_BRICKS,
                    Blocks.DIRT, ModBlocks.ENERGETIC_ESOTERIC_CORE.get()
            );

    @Override
    public int getDefaultMaxStackSize() {
        return 1;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        Block clickedBlock = level.getBlockState(pContext.getClickedPos()).getBlock();

        if(CHISEL_MAP.containsKey(clickedBlock) && !level.isClientSide()) {
            level.setBlockAndUpdate(
                    pContext.getClickedPos(), CHISEL_MAP.get(clickedBlock).defaultBlockState()
            );

            pContext.getItemInHand().hurtAndBreak(1, (ServerLevel) level, (ServerPlayer) pContext.getPlayer(),
                    item -> pContext.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND));
            level.playSound(null, pContext.getClickedPos(), SoundEvents.ENDER_PEARL_THROW, SoundSource.BLOCKS);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.terralink.esoteric_scanner"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.terralink.hold_shift"));
        }

        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
