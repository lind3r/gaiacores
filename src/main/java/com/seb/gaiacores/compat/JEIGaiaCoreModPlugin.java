package com.seb.gaiacores.compat;

import com.seb.gaiacores.GaiaCores;
import com.seb.gaiacores.block.ModBlocks;
import com.seb.gaiacores.recipe.*;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIGaiaCoreModPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(GaiaCores.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(new LucentGaiaCoreRecipeCategory(guiHelper));
        registration.addRecipeCategories(new VerdantGaiaCoreRecipeCategory(guiHelper));
        registration.addRecipeCategories(new VolcanicGaiaCoreRecipeCategory(guiHelper));
        registration.addRecipeCategories(new CharredGaiaCoreRecipeCategory(guiHelper));
        registration.addRecipeCategories(new AdamantGaiaCoreRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<LucentGaiaCoreRecipe> lucentGaiaCoreRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.LUCENT_GAIA_CORE_TYPE.get()).stream().map(RecipeHolder::value).toList();
        List<VerdantGaiaCoreRecipe> verdantGaiaCoreRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.VERDANT_GAIA_CORE_TYPE.get()).stream().map(RecipeHolder::value).toList();
        List<VolcanicGaiaCoreRecipe> volcanicGaiaCoreRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.VOLCANIC_GAIA_CORE_TYPE.get()).stream().map(RecipeHolder::value).toList();
        List<CharredGaiaCoreRecipe> charredGaiaCoreRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.CHARRED_GAIA_CORE_TYPE.get()).stream().map(RecipeHolder::value).toList();
        List<AdamantGaiaCoreRecipe> adamantGaiaCoreRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.ADAMANT_GAIA_CORE_TYPE.get()).stream().map(RecipeHolder::value).toList();

        registration.addRecipes(LucentGaiaCoreRecipeCategory.TYPE, lucentGaiaCoreRecipes);
        registration.addRecipes(VerdantGaiaCoreRecipeCategory.TYPE, verdantGaiaCoreRecipes);
        registration.addRecipes(VolcanicGaiaCoreRecipeCategory.TYPE, volcanicGaiaCoreRecipes);
        registration.addRecipes(CharredGaiaCoreRecipeCategory.TYPE, charredGaiaCoreRecipes);
        registration.addRecipes(AdamantGaiaCoreRecipeCategory.TYPE, adamantGaiaCoreRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.LUCENT_GAIA_CORE.get()), LucentGaiaCoreRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.VERDANT_GAIA_CORE.get()), VerdantGaiaCoreRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.VOLCANIC_GAIA_CORE.get()), VolcanicGaiaCoreRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.CHARRED_GAIA_CORE.get()), CharredGaiaCoreRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.ADAMANT_GAIA_CORE.get()), AdamantGaiaCoreRecipeCategory.TYPE);
    }
}
