package com.seb.gaiacore.block.entity.custom;

import com.seb.gaiacore.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Random;

public class VerdantGaiaCoreBlockEntity extends GaiaCoreBlockEntityBase {
    public VerdantGaiaCoreBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.VERDANT_GAIA_CORE_BE.get(), pPos, pBlockState);
    }

    @Override
    protected boolean coreSpecificConditionsMet(BlockState blockState) {
        // At least one adjacent block must be air
        for (Direction dir : Direction.values()) {
            BlockPos adjacentPos = worldPosition.relative(dir);
            BlockState adjacentState = level.getBlockState(adjacentPos);
            if (adjacentState.isAir()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onProgressComplete(Level level, BlockPos blockPos, BlockState blockState) {

    }

    @Override
    protected void makeSound(Level level, BlockPos blockPos) {
        level.playSound(null, blockPos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    protected String getCoreTranslationKey() {
        return "block.gaiacore.verdant_gaia_core";
    }

    public void onExplosionNearby() {
        if (level == null || level.isClientSide) return;
        setAnchored(false);
    }
}
