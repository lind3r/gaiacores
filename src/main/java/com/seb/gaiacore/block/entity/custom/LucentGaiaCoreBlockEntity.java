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

        Direction skyFacing = ModBlockPosHelper.findSkyFacing(level, worldPosition);
        if (skyFacing == null) return;

        boolean hasGlowExceptSky = allSidesGlowstoneExcept(skyFacing);
        boolean isDaytime = level.isDay();
        BlockPos skyPos = worldPosition.relative(skyFacing);
        boolean canSeeSky = level.canSeeSky(skyPos);
        int skyLightLevel = level.getBrightness(LightLayer.SKY, skyPos);

        boolean canRunEffect = hasGlowExceptSky && canSeeSky && isDaytime && skyLightLevel >= 15;

        reduceCooldownIf(canRunEffect);

        if (!isOnCooldown() && canRunEffect) {
            spawnLowTierOres(level);
            makeSound(level, blockPos);
            setCooldown(10);
        }

        if (blockState.getValue(GaiaCoreBase.POWERED) != canRunEffect) {
            level.setBlockAndUpdate(blockPos, blockState.setValue(GaiaCoreBase.POWERED, canRunEffect));
        }

        setChanged(level, blockPos, blockState);
    }

    private boolean allSidesGlowstoneExcept(Direction skip) {
        for (Direction dir : Direction.values()) {
            if (dir == skip) continue;
            BlockPos adjacent = worldPosition.relative(dir);
            if (!level.getBlockState(adjacent).is(Blocks.GLOWSTONE)) {
                return false;
            }
        }
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
        if (level.isEmptyBlock(targetPos)) {
            BlockState ore = LOW_TIER_ORES.get(RANDOM.nextInt(LOW_TIER_ORES.size()));
            level.setBlock(targetPos, ore, 3);
        }
    }

    @Override
    protected String getCoreTranslationKey() {
        return "block.gaiacore.lucent_gaia_core";
    }
}
