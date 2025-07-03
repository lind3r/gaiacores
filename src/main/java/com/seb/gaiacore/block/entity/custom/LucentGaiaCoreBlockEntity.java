package com.seb.gaiacore.block.entity.custom;

import com.seb.gaiacore.block.custom.GaiaCoreBase;
import com.seb.gaiacore.block.entity.ModBlockEntities;
import com.seb.gaiacore.util.ModBlockPosHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Random;

public class LucentGaiaCoreBlockEntity extends GaiaCoreBlockEntityBase {
    public LucentGaiaCoreBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.LUCENT_GAIA_CORE_BE.get(), pPos, pBlockState);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level == null || level.isClientSide) return;

        if (isOnCooldown()) {
            cooldown--;
            level.setBlockAndUpdate(blockPos, blockState.setValue(GaiaCoreBase.POWERED, false));
            return;
        }

        if (!conditionsMet(blockState)) {
            level.setBlockAndUpdate(blockPos, blockState.setValue(GaiaCoreBase.POWERED, false));
            return;
        }

        // Spawn ores and set cooldown
        spawnLowTierOres(level);
        makeSound(level, blockPos);
        setCooldown(defaultCooldown);
        level.setBlockAndUpdate(blockPos, blockState.setValue(GaiaCoreBase.POWERED, true));
        setChanged(level, blockPos, blockState);
    }

    private boolean conditionsMet(BlockState blockState) {
        if (level == null) return false;

        Direction skyFacing = ModBlockPosHelper.findSkyFacing(level, worldPosition);
        if (skyFacing == null) return false;

        // All other sides must be glowstone
        for (Direction dir : Direction.values()) {
            if (dir == skyFacing) continue;
            BlockPos adjacent = worldPosition.relative(dir);
            if (!level.getBlockState(adjacent).is(Blocks.GLOWSTONE)) {
                return false;
            }
        }

        // Sky-facing side must be exposed to sunlight
        BlockPos skyPos = worldPosition.relative(skyFacing);
        if (!level.canSeeSky(skyPos)) return false;
        if (level.getBrightness(LightLayer.SKY, skyPos) < 15) return false;

        return true;
    }

    @Override
    protected void makeSound(Level level, BlockPos blockPos) {
        level.playSound(null, blockPos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    private static final List<BlockState> LOW_TIER_ORES = List.of(
            Blocks.REDSTONE_ORE.defaultBlockState(),
            Blocks.IRON_ORE.defaultBlockState(),
            Blocks.COPPER_ORE.defaultBlockState()
    );
    private static final Random RANDOM = new Random();

    private void spawnLowTierOres(Level level) {
        if (level == null || level.isClientSide) return;
        Direction skyFacing = ModBlockPosHelper.findSkyFacing(level, worldPosition);
        if (skyFacing == null) return;
        BlockPos targetPos = worldPosition.relative(skyFacing);
        BlockState targetState = level.getBlockState(targetPos);
        if (targetState.isAir()) {
            BlockState ore = LOW_TIER_ORES.get(RANDOM.nextInt(LOW_TIER_ORES.size()));
            level.setBlock(targetPos, ore, 3);
        }
    }

    @Override
    protected String getCoreTranslationKey() {
        return "block.gaiacore.lucent_gaia_core";
    }
}
