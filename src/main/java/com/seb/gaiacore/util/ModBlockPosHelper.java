package com.seb.gaiacore.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

public class ModBlockPosHelper {

    public static Direction findSkyFacing(Level level, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            BlockPos adjacent = pos.relative(dir);
            if (level.canSeeSky(adjacent)) {
                return dir;
            }
        }
        return null;
    }
}

