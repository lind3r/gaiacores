package com.seb.gaiacore.block.custom;

import com.mojang.serialization.MapCodec;
import com.seb.gaiacore.block.entity.ModBlockEntities;
import com.seb.gaiacore.block.entity.custom.EnergeticGaiaCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class EnergeticGaiaCore extends GaiaCoreBase {
    public static final MapCodec<EnergeticGaiaCore> CODEC = simpleCodec(EnergeticGaiaCore::new);

    public EnergeticGaiaCore(Properties properties) {
        super(properties, GaiaCoreVariant.ENERGETIC);
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
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pBlockEntityType, ModBlockEntities.ENERGETIC_GAIA_CORE_BE.get(),
                (level, blockPos, blockState, energeticGaiaCoreBlockEntity)
                        -> energeticGaiaCoreBlockEntity.tick(level, blockPos, blockState));
    }
}
