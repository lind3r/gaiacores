package com.seb.gaiacore.event;

import com.seb.gaiacore.GaiaCore;
import com.seb.gaiacore.block.ModBlocks;
import com.seb.gaiacore.block.entity.custom.EnergeticGaiaCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GaiaCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    @SubscribeEvent
    public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
        var explosion = event.getExplosion();
        var center = explosion.center();
        float radius = explosion.radius();

        int checkRadius = (int) Math.ceil(radius) + 1;
        BlockPos centerPos = BlockPos.containing(center);

        for (BlockPos pos : BlockPos.betweenClosed(
                centerPos.offset(-checkRadius, -checkRadius, -checkRadius),
                centerPos.offset(checkRadius, checkRadius, checkRadius))) {
            BlockState state = event.getLevel().getBlockState(pos);
            if (state.is(ModBlocks.ENERGETIC_GAIA_CORE.get())) {
                if (event.getLevel().getBlockEntity(pos) instanceof EnergeticGaiaCoreBlockEntity core) {
                    core.onExplosionNearby();
                }
            }
        }
    }
}
