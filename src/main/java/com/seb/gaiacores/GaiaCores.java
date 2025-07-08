package com.seb.gaiacores;

import com.seb.gaiacores.block.ModBlocks;
import com.seb.gaiacores.block.entity.ModBlockEntities;
import com.seb.gaiacores.compat.GaiaCoresJeiCompat;
import com.seb.gaiacores.component.ModDataComponentTypes;
import com.seb.gaiacores.item.ModItems;
import com.seb.gaiacores.recipe.ModRecipes;
import com.seb.gaiacores.screen.ModMenuTypes;
import com.seb.gaiacores.screen.custom.GaiaCoreAnalyzerScreen;
import com.seb.gaiacores.util.ModCreativeModsTabs;
import com.seb.gaiacores.util.ModItemProperties;
import com.seb.gaiacores.worldgen.ModFeatures;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GaiaCores.MOD_ID)
public class GaiaCores
{
    public static final String MOD_ID = "gaiacores";

    public GaiaCores(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        ModCreativeModsTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModDataComponentTypes.register(modEventBus);
        ModFeatures.FEATURES.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);

        if (ModList.get().isLoaded("jei")) {
            GaiaCoresJeiCompat.init();
            System.out.println("Hello seb");
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) { }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            ModItemProperties.addCustomItemProperties();
            MenuScreens.register(ModMenuTypes.GAIA_CORE_ANALYZER_MENU.get(), GaiaCoreAnalyzerScreen::new);
        }
    }
}
