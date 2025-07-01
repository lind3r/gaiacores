package com.seb.gaiacore.block.custom;

import com.mojang.serialization.MapCodec;
import com.seb.gaiacore.block.entity.ModBlockEntities;
import com.seb.gaiacore.block.entity.custom.EnergeticGaiaCoreBlockEntity;
import com.seb.gaiacore.block.entity.custom.GaiaCoreAnalyzerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class EnergeticGaiaCore extends GaiaCoreBase {
    public static final MapCodec<GaiaCoreAnalyzer> CODEC = simpleCodec(GaiaCoreAnalyzer::new);
    GaiaCoreVariant gaiaCoreVariant;

    public EnergeticGaiaCore(Properties properties) {
        super(properties);
        this.gaiaCoreVariant = GaiaCoreVariant.ENERGETIC;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new EnergeticGaiaCoreBlockEntity(pPos, pState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos,
                            BlockState pNewState, boolean pMovedByPiston) {
        if(pState.getBlock() != pNewState.getBlock()) {
            if(pLevel.getBlockEntity(pPos) instanceof EnergeticGaiaCoreBlockEntity energeticGaiaCoreBlockEntity) {
                energeticGaiaCoreBlockEntity.drops();
                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (pLevel.getBlockEntity(pPos) instanceof EnergeticGaiaCoreBlockEntity blockEntity) {
            if (!pLevel.isClientSide() && pPlayer.isCrouching()) {
                boolean currentState = pState.getValue(POWERED);
                pLevel.setBlockAndUpdate(pPos, pState.setValue(POWERED, !currentState));
                pLevel.playSound(pPlayer, pPos, SoundEvents.AMETHYST_CLUSTER_PLACE, SoundSource.BLOCKS, 1f, 1f);

            } else if (!pLevel.isClientSide() && !pPlayer.isCrouching()) {
                ((ServerPlayer) pPlayer).openMenu(new SimpleMenuProvider(blockEntity,
                        Component.translatable( "block.gaiacore.energetic_gaia_core")), pPos);
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.ENERGETIC_GAIA_CORE_BE.get(),
                (level, blockPos, blockState, energeticGaiaCoreBlockEntity)
                        -> energeticGaiaCoreBlockEntity.tick(level, blockPos, blockState));
    }
}
