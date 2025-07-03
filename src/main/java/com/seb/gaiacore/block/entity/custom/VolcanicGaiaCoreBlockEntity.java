package com.seb.gaiacore.block.entity.custom;

import com.seb.gaiacore.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;

public class VolcanicGaiaCoreBlockEntity extends GaiaCoreBlockEntityBase {
    public VolcanicGaiaCoreBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VOLCANIC_GAIA_CORE_BE.get(), pos, state);
    }

    @Override
    protected boolean coreSpecificConditionsMet(BlockState blockState) {
        return false;
    }

    @Override
    protected void onProgressComplete(Level level, BlockPos blockPos, BlockState blockState) {

    }

    @Override
    protected void makeSound(Level level, BlockPos blockPos) {

    }

    @Override
    protected String getCoreTranslationKey() {
        return "block.gaiacore.volcanic_gaia_core";
    }
}