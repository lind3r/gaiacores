package com.seb.gaiacore.block.entity.custom;

import com.seb.gaiacore.Config;
import com.seb.gaiacore.block.custom.GaiaCoreBase;
import com.seb.gaiacore.block.entity.ModBlockEntities;
import com.seb.gaiacore.util.ModBlockPosHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LucentGaiaCoreBlockEntity extends GaiaCoreBlockEntityBase {
    private static final Random RANDOM = new Random();

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
            spawnOres(level);
            makeSound(level, blockPos);
            setCooldown(Config.getLucentCoreCooldown());
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
            if (!level.getBlockState(adjacent).is(net.minecraft.world.level.block.Blocks.GLOWSTONE)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void makeSound(Level level, BlockPos blockPos) {
        level.playSound(null, blockPos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    private void spawnOres(Level level) {
        if (level == null || level.isClientSide) return;
        Direction skyFacing = ModBlockPosHelper.findSkyFacing(level, worldPosition);
        if (skyFacing == null) return;

        BlockPos targetPos = worldPosition.relative(skyFacing);
        if (!level.isEmptyBlock(targetPos)) return;

        Map<String, Integer> oreFrequencies = Config.getOreFrequencies();

        List<Block> weightedOres = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : oreFrequencies.entrySet()) {
            Block oreBlock = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(entry.getKey()));
            if (oreBlock != null) {
                int weight = entry.getValue();
                for (int i = 0; i < weight; i++) {
                    weightedOres.add(oreBlock);
                }
            }
        }

        if (weightedOres.isEmpty()) return;

        Block selectedOre = weightedOres.get(RANDOM.nextInt(weightedOres.size()));
        level.setBlock(targetPos, selectedOre.defaultBlockState(), 3);
    }

    @Override
    protected String getCoreTranslationKey() {
        return "block.gaiacore.lucent_gaia_core";
    }
}
