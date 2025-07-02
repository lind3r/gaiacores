package com.seb.gaiacore.event;

import com.seb.gaiacore.GaiaCore;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GaiaCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
//        if (event.getSource().getDirectEntity() instanceof Player player) {
//            player.sendSystemMessage(Component.literal(player.getName().getString() + " hehe"));
//        }
    }
}
