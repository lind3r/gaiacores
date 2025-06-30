package com.seb.gaiacore.items.custom;

import com.seb.gaiacore.component.ModDataComponentTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class GaiaScannerItem extends Item {
    public GaiaScannerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;

        BlockState clickedState = context.getLevel().getBlockState(context.getClickedPos());
        Block clickedBlock = clickedState.getBlock();

        // Save block name into item's custom component
        context.getItemInHand().set(ModDataComponentTypes.BLOCK_NAME.get(), clickedBlock.getName());

        player.displayClientMessage(Component.literal("Saved block information: " + clickedBlock.getName().getString()), true);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.gaiacore.gaia_scanner"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.gaiacore.hold_shift"));
        }

        Component clickedBlockName = pStack.get(ModDataComponentTypes.BLOCK_NAME.get());
        if (clickedBlockName != null) {
            Component styled = ComponentUtils.wrapInSquareBrackets(clickedBlockName.copy())
                    .withStyle(ChatFormatting.AQUA);
            pTooltipComponents.add(Component.literal("Saved block information: ").append(styled));

        }

        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
