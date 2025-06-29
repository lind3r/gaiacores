package com.seb.gaiacore.items.custom;

import com.seb.gaiacore.blocks.custom.GaiaCoreBase;
import com.seb.gaiacore.component.ModDataComponentTypes;
import com.seb.gaiacore.sounds.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class GaiaScannerItem extends Item {
    public GaiaScannerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getDefaultMaxStackSize() {
        return 1;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;

        if (player.isCrouching()) {
            BlockState clickedState = context.getLevel().getBlockState(context.getClickedPos());
            Block clickedBlock = clickedState.getBlock();

            // Save block name into item's custom component
            context.getItemInHand().set(ModDataComponentTypes.BLOCK_NAME.get(), clickedBlock.getName());

            player.displayClientMessage(Component.literal("Saved block: " + clickedBlock.getName().getString()), true);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS; // fall through to allow normal use() to handle scan
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.isCrouching()) {
            // Crouch-right-click is handled by useOn when targeting block
            // Do nothing here to avoid double-triggering
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }

        if (!level.isClientSide) {
            // Perform scan
            BlockPos nearest = findNearestGaiaCoreBaseBlock(level, player.blockPosition(), 16);
            if (nearest != null) {
                double dist = Math.sqrt(player.blockPosition().distSqr(nearest));

                float maxRange = 16f;
                double clampedDist = Math.min(dist, maxRange);
                float normalizedDist = (float) (clampedDist / maxRange);
                float proximity = 1f - normalizedDist;

                float pitch = 0.5f + proximity * 1.5f;

                level.playSound(null, player.blockPosition(), ModSounds.SCANNER_BEEP.get(),
                        SoundSource.PLAYERS, 0.5f, pitch);

                player.displayClientMessage(Component.literal("Located Gaia Core: " + (int) dist + " blocks away."), true);
            } else {
                player.displayClientMessage(Component.literal("No Gaia Cores within scanning range."), true);
            }
        }

        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
    }

    private BlockPos findNearestGaiaCoreBaseBlock(Level level, BlockPos origin, int maxRange) {
        BlockPos nearest = null;
        double nearestDistanceSq = Double.MAX_VALUE;

        for (int dx = -maxRange; dx <= maxRange; dx++) {
            for (int dy = -maxRange; dy <= maxRange; dy++) {
                for (int dz = -maxRange; dz <= maxRange; dz++) {
                    BlockPos pos = origin.offset(dx, dy, dz);
                    Block block = level.getBlockState(pos).getBlock();

                    if (block instanceof GaiaCoreBase) {
                        double distSq = origin.distSqr(pos);
                        if (distSq < nearestDistanceSq) {
                            nearestDistanceSq = distSq;
                            nearest = pos;
                        }
                    }
                }
            }
        }

        return nearest;
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
            pTooltipComponents.add(Component.literal("Linked Block: ").append(styled));

        }

        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
