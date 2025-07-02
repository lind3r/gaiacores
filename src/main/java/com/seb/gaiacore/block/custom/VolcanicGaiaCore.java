package com.seb.gaiacore.block.custom;

import com.mojang.serialization.MapCodec;
import com.seb.gaiacore.block.entity.ModBlockEntities;
import com.seb.gaiacore.block.entity.custom.VolcanicGaiaCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class VolcanicGaiaCore extends GaiaCoreBase {
    public VolcanicGaiaCore(Properties properties) {
        super(properties, GaiaCoreVariant.VOLCANIC);
    }

    public static final MapCodec<VolcanicGaiaCore> CODEC = simpleCodec(VolcanicGaiaCore::new);
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new VolcanicGaiaCoreBlockEntity(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pBlockEntityType, ModBlockEntities.VOLCANIC_GAIA_CORE_BE.get(),
                (level, blockPos, blockState, be)
                        -> be.tick(level, blockPos, blockState));
    }
}
