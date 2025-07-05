package com.seb.gaiacore;

import com.seb.gaiacore.block.ModBlocks;
import com.seb.gaiacore.block.entity.ModBlockEntities;
import com.seb.gaiacore.component.ModDataComponentTypes;
import com.seb.gaiacore.recipe.ModRecipes;
import com.seb.gaiacore.screen.ModMenuTypes;
import com.seb.gaiacore.screen.custom.GaiaCoreAnalyzerScreen;
import com.seb.gaiacore.util.ModCreativeModsTabs;
import com.seb.gaiacore.item.ModItems;
import com.seb.gaiacore.util.ModItemProperties;
import com.seb.gaiacore.worldgen.ModFeatures;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GaiaCore.MOD_ID)
public class GaiaCore
{
    public static final String MOD_ID = "gaiacore";

    public GaiaCore(FMLJavaModLoadingContext context)
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
