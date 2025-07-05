package com.seb.gaiacore.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

public class GaiaCoreOreFeature extends Feature<OreConfiguration> {

    public GaiaCoreOreFeature(Codec<OreConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<OreConfiguration> context) {
        OreConfiguration config = context.config();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos origin = context.origin();

        boolean success = false;
        for (BlockState state : config.targetStates.stream().map(rule -> rule.state).toList()) {
            for (int i = 0; i < config.size; i++) {
                BlockPos pos = origin.offset(random.nextInt(8), random.nextInt(8), random.nextInt(8));
                if (config.targetStates.get(0).target.test(level.getBlockState(pos), random)) {
                    level.setBlock(pos, state, 2);

                    onOrePlaced(level, pos, state);

                    success = true;
                }
            }
        }
        return success;
    }

    private void onOrePlaced(WorldGenLevel level, BlockPos pos, BlockState state) {
        int radius = 4;
        int radiusSq = radius * radius;

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (dx * dx + dy * dy + dz * dz <= radiusSq) {
                        mutablePos.set(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz);

                        // Make sure not to overwrite the ore block itself
                        if (!mutablePos.equals(pos)) {
                            level.setBlock(mutablePos, Blocks.AIR.defaultBlockState(), 2);
                        }
                    }
                }
            }
        }
    }
}
