package com.seb.gaiacore.compat;

import com.seb.gaiacore.GaiaCore;
import com.seb.gaiacore.block.ModBlocks;
import com.seb.gaiacore.recipe.LucentGaiaCoreRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

@JeiPlugin
public class JEIGaiaCoreModPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new LucentGaiaCoreRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        TagKey<Item> planksTag = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("minecraft", "planks"));
        List<LucentGaiaCoreRecipe> lucentGaiaCoreRecipes = List.of(
                new LucentGaiaCoreRecipe(new ItemStack(Items.COPPER_ORE)),
                new LucentGaiaCoreRecipe(new ItemStack(Items.IRON_ORE)),
                new LucentGaiaCoreRecipe(new ItemStack(Items.GOLD_ORE)),
                new LucentGaiaCoreRecipe(new ItemStack(Items.REDSTONE_ORE)),
                new LucentGaiaCoreRecipe(new ItemStack(Items.LAPIS_ORE)),
                new LucentGaiaCoreRecipe(new ItemStack(Items.DIAMOND_ORE)),
                new LucentGaiaCoreRecipe(new ItemStack(Items.NETHER_QUARTZ_ORE)),
                new LucentGaiaCoreRecipe(new ItemStack(Items.EMERALD_ORE))
        );
        registration.addRecipes(LucentGaiaCoreRecipeCategory.TYPE, lucentGaiaCoreRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.LUCENT_GAIA_CORE.get()), LucentGaiaCoreRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.VERDANT_GAIA_CORE.get()), LucentGaiaCoreRecipeCategory.TYPE);
    }
}
