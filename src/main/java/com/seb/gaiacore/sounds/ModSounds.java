package com.seb.gaiacore.sounds;

import com.seb.gaiacore.GaiaCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(Registries.SOUND_EVENT, GaiaCore.MOD_ID);

    public static final RegistryObject<SoundEvent> SCANNER_BEEP =
            SOUNDS.register("scanner_beep", () ->
                    SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(
                            GaiaCore.MOD_ID, "scanner_beep")));

    public static void register(IEventBus bus) {
        SOUNDS.register(bus);
    }
}
