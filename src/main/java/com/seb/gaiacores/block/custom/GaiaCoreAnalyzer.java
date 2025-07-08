package com.seb.gaiacores.block.custom;

import com.mojang.serialization.MapCodec;
import com.seb.gaiacores.block.entity.custom.GaiaCoreAnalyzerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class GaiaCoreAnalyzer extends BaseEntityBlock {

    public static final MapCodec<GaiaCoreAnalyzer> CODEC = simpleCodec(GaiaCoreAnalyzer::new);

    public GaiaCoreAnalyzer(Properties pProperties) {
        super(pProperties);
        registerDefaultState(defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new GaiaCoreAnalyzerBlockEntity(pPos, pState);
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos,
                            BlockState pNewState, boolean pMovedByPiston) {
        if(pState.getBlock() != pNewState.getBlock()) {
            if(pLevel.getBlockEntity(pPos) instanceof GaiaCoreAnalyzerBlockEntity gaiaCoreAnalyzerBlockEntity) {
                gaiaCoreAnalyzerBlockEntity.drops();
                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (pLevel.getBlockEntity(pPos) instanceof GaiaCoreAnalyzerBlockEntity gaiaCoreAnalyzerBlockEntity) {
            if (!pLevel.isClientSide()) {
                ((ServerPlayer) pPlayer).openMenu(new SimpleMenuProvider(gaiaCoreAnalyzerBlockEntity,
                        Component.literal("Gaia Core Analyzer")), pPos);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HorizontalDirectionalBlock.FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(
                HorizontalDirectionalBlock.FACING,
                pContext.getHorizontalDirection().getOpposite()
        );
    }
}
