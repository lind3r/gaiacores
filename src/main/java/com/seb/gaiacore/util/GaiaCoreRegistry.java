package com.seb.gaiacore.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.*;

public class GaiaCoreRegistry {
    private static final Map<Level, Set<BlockPos>> gaiaCorePositions = new HashMap<>();

    public static void addGaiaCore(Level level, BlockPos pos) {
        gaiaCorePositions.computeIfAbsent(level, l -> new HashSet<>()).add(pos);
    }

    public static void removeGaiaCore(Level level, BlockPos pos) {
        Set<BlockPos> set = gaiaCorePositions.get(level);
        if (set != null) {
            set.remove(pos);
            if (set.isEmpty()) gaiaCorePositions.remove(level);
        }
    }

    public static Set<BlockPos> getGaiaCores(Level level) {
        return gaiaCorePositions.getOrDefault(level, Collections.emptySet());
    }
}
