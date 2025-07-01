package com.seb.gaiacore.blocks.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public abstract class GaiaCoreBase extends Block {
    public GaiaCoreBase(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, false));
    }

    public enum GaiaCoreVariant { ENERGETIC, VOLCANIC }
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(POWERED);
    }

//    @Override
//    protected InteractionResult useWithoutItem(
//            BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
//
//        if(!pLevel.isClientSide()) {
//            boolean currentState = pState.getValue(POWERED);
//            pLevel.setBlockAndUpdate(pPos, pState.setValue(POWERED, !currentState));
//        }
//
//        pLevel.playSound(pPlayer, pPos, SoundEvents.AMETHYST_CLUSTER_PLACE, SoundSource.BLOCKS, 1f, 1f);
//
//        return InteractionResult.SUCCESS;
//    }
}
