package com.seb.gaiacore.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class VolcanicGaiaCore extends GaiaCoreBase {
    GaiaCoreVariant gaiaCoreVariant;

    public VolcanicGaiaCore(Properties properties) {
        super(properties);
        this.gaiaCoreVariant = GaiaCoreVariant.VOLCANIC;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    public GaiaCoreVariant getGaiaCoreVariant() {
        return gaiaCoreVariant;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }
}
