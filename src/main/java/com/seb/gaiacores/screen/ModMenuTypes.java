package com.seb.gaiacores.screen;

import com.seb.gaiacores.GaiaCores;
import com.seb.gaiacores.screen.custom.GaiaCoreAnalyzerMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, GaiaCores.MOD_ID);

    public static final RegistryObject<MenuType<GaiaCoreAnalyzerMenu>> GAIA_CORE_ANALYZER_MENU =
            MENUS.register("gaia_core_analyzer_menu", () -> IForgeMenuType.create(GaiaCoreAnalyzerMenu::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
