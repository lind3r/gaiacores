package com.seb.gaiacore.block.custom;

import com.mojang.serialization.MapCodec;
import com.seb.gaiacore.block.entity.ModBlockEntities;
import com.seb.gaiacore.block.entity.custom.VerdantGaiaCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import org.jetbrains.annotations.Nullable;

public class VerdantGaiaCore extends GaiaCoreBase {
    public VerdantGaiaCore(Properties properties) {
        super(properties, GaiaCoreVariant.VERDANT);
    }

    public static final MapCodec<VerdantGaiaCore> CODEC = simpleCodec(VerdantGaiaCore::new);
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new VerdantGaiaCoreBlockEntity(pPos, pState);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return true;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pBlockEntityType, ModBlockEntities.VERDANT_GAIA_CORE_BE.get(),
                (level, blockPos, blockState, be)
                        -> be.tick(level, blockPos, blockState));
    }
}
