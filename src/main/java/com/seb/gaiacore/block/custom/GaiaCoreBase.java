package com.seb.gaiacore.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public abstract class GaiaCoreBase extends BaseEntityBlock {
    protected final GaiaCoreVariant gaiaCoreVariant;

    public GaiaCoreBase(Properties properties, GaiaCoreVariant variant) {
        super(properties);
        this.gaiaCoreVariant = variant;
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, false));
    }

    public enum GaiaCoreVariant { ENERGETIC, VOLCANIC }
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(POWERED);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos,
                        BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be != null) {
                // If the block entity has a drops() method, call it
                try {
                    be.getClass().getMethod("drops").invoke(be);
                } catch (Exception ignored) {}
                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be != null) {
            if (!pLevel.isClientSide() && pPlayer.isCrouching()) {
                boolean currentState = pState.getValue(POWERED);
                pLevel.setBlockAndUpdate(pPos, pState.setValue(POWERED, !currentState));
                pLevel.playSound(pPlayer, pPos, SoundEvents.AMETHYST_CLUSTER_PLACE, SoundSource.BLOCKS, 1f, 1f);
                return InteractionResult.SUCCESS;
            } else if (!pLevel.isClientSide() && !pPlayer.isCrouching()) {
                if (be instanceof MenuProvider provider && pPlayer instanceof ServerPlayer serverPlayer) {
                    serverPlayer.openMenu(provider, pPos);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    public GaiaCoreVariant getGaiaCoreVariant() {
        return gaiaCoreVariant;
    }
}
