package com.seb.gaiacores.item.custom;

import com.seb.gaiacores.component.ModDataComponentTypes;
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

public class GaiaCoreScannerItem extends Item {
    public GaiaCoreScannerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;

        BlockState clickedState = context.getLevel().getBlockState(context.getClickedPos());
        Block clickedBlock = clickedState.getBlock();

        context.getItemInHand().set(ModDataComponentTypes.BLOCK_TRANSLATION_KEY.get(), clickedBlock.getDescriptionId()); // This is the translation key

        player.displayClientMessage(Component.literal("Saved block information: " + clickedBlock.getName().getString()), true);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.gaiacores.hold_shift"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.gaiacores.gaia_core_scanner"));
        }

        String translationKey = pStack.get(ModDataComponentTypes.BLOCK_TRANSLATION_KEY.get());
        if (translationKey != null && !translationKey.isEmpty()) {
            Component styled = ComponentUtils.wrapInSquareBrackets(Component.translatable(translationKey))
                    .withStyle(ChatFormatting.AQUA);
            pTooltipComponents.add(Component.literal("Saved block information: ").append(styled));
        }

        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
