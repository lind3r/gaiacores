package com.seb.gaiacore.block.entity.custom;

import com.seb.gaiacore.block.entity.ModBlockEntities;
import com.seb.gaiacore.screen.custom.EnergeticGaiaCoreMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class EnergeticGaiaCoreBlockEntity extends GaiaCoreBlockEntityBase {
    public EnergeticGaiaCoreBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ENERGETIC_GAIA_CORE_BE.get(), pPos, pBlockState);
    }

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (conditionsMet(blockState)) {
            progress++;
            setChanged(level, blockPos, blockState);
            if (progress >= maxProgress) {
                spawnLowTierOres(level);
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    @Override
    protected boolean conditionsMet(BlockState blockState) {
        if (!blockState.getValue(com.seb.gaiacore.block.custom.GaiaCoreBase.POWERED)) {
            return false;
        }
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

    private static final List<BlockState> LOW_TIER_ORES = List.of(
            Blocks.COAL_ORE.defaultBlockState(),
            Blocks.IRON_ORE.defaultBlockState(),
            Blocks.COPPER_ORE.defaultBlockState()
    );
    private static final Random RANDOM = new Random();

    private void spawnLowTierOres(Level level) {
        if (level == null || level.isClientSide) return;
        for (Direction dir : Direction.values()) {
            BlockPos targetPos = worldPosition.relative(dir);
            BlockState targetState = level.getBlockState(targetPos);
            if (targetState.isAir()) {
                BlockState ore = LOW_TIER_ORES.get(RANDOM.nextInt(LOW_TIER_ORES.size()));
                level.setBlock(targetPos, ore, 3);
            }
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new EnergeticGaiaCoreMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected String getCoreTranslationKey() {
        return "block.gaiacore.energetic_gaia_core";
    }
}
