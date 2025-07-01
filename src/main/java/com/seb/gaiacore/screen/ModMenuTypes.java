package com.seb.gaiacore.screen;

import com.seb.gaiacore.GaiaCore;
import com.seb.gaiacore.screen.custom.EnergeticGaiaCoreMenu;
import com.seb.gaiacore.screen.custom.GaiaCoreAnalyzerMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, GaiaCore.MOD_ID);

    public static final RegistryObject<MenuType<GaiaCoreAnalyzerMenu>> GAIA_CORE_ANALYZER_MENU =
            MENUS.register("gaia_core_analyzer_menu", () -> IForgeMenuType.create(GaiaCoreAnalyzerMenu::new));
    public static final RegistryObject<MenuType<EnergeticGaiaCoreMenu>> ENERGETIC_GAIA_CORE_MENU =
            MENUS.register("energetic_gaia_core_menu", () -> IForgeMenuType.create(EnergeticGaiaCoreMenu::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
