package com.seb.gaiacore.block.entity.custom;

import com.seb.gaiacore.block.entity.ModBlockEntities;
import com.seb.gaiacore.screen.custom.VolcanicGaiaCoreMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class VolcanicGaiaCoreBlockEntity extends GaiaCoreBlockEntityBase {
    public VolcanicGaiaCoreBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VOLCANIC_GAIA_CORE_BE.get(), pos, state);
    }

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (conditionsMet(blockState)) {
            progress++;
            setChanged(level, blockPos, blockState);
            if (progress >= maxProgress) {
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    @Override
    protected boolean conditionsMet(BlockState blockState) {
        // Placeholder: implement volcanic-specific condition logic here
        return blockState.getValue(com.seb.gaiacore.block.custom.GaiaCoreBase.POWERED);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new VolcanicGaiaCoreMenu(id, playerInventory, this, this.data);
    }

    @Override
    protected String getCoreTranslationKey() {
        return "block.gaiacore.volcanic_gaia_core";
    }
}