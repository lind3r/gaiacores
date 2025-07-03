package com.seb.gaiacore.block.entity.custom;

import com.seb.gaiacore.block.custom.GaiaCoreBase;
import com.seb.gaiacore.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CharredGaiaCoreBlockEntity extends GaiaCoreBlockEntityBase {
    public CharredGaiaCoreBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CHARRED_GAIA_CORE_BE.get(), pPos, pBlockState);
        setCooldown(20);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level == null || level.isClientSide) return;

        reduceCooldownIf(false);

        if (isOnCooldown()) {
            level.setBlockAndUpdate(blockPos, blockState.setValue(GaiaCoreBase.POWERED, false));
            return;
        }

        setCooldown(defaultCooldown);
    }

    @Override
    protected void makeSound(Level level, BlockPos blockPos) {

    }

    @Override
    protected String getCoreTranslationKey() {
        return "block.gaiacore.charred_gaia_core";
    }
}
