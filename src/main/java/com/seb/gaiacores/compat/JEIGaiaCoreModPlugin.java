package com.seb.gaiacores.compat;

import com.seb.gaiacores.Config;
import com.seb.gaiacores.GaiaCores;
import com.seb.gaiacores.block.ModBlocks;
import com.seb.gaiacores.recipe.LucentGaiaCoreRecipe;
import com.seb.gaiacores.recipe.VerdantGaiaCoreRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Objects;

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
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<LucentGaiaCoreRecipe> lucentGaiaCoreRecipes = Config.getOreFrequencies().keySet().stream()
                .map(ResourceLocation::tryParse)
                .filter(Objects::nonNull)
                .map(ForgeRegistries.BLOCKS::getValue)
                .filter(Objects::nonNull)
                .map(block -> new LucentGaiaCoreRecipe(new ItemStack(block.asItem())))
                .toList();

        List<VerdantGaiaCoreRecipe> verdantGaiaCoreRecipes = List.of(
                new VerdantGaiaCoreRecipe(new ItemStack(Blocks.OAK_LOG))
        );

        registration.addRecipes(LucentGaiaCoreRecipeCategory.TYPE, lucentGaiaCoreRecipes);
        registration.addRecipes(VerdantGaiaCoreRecipeCategory.TYPE, verdantGaiaCoreRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.LUCENT_GAIA_CORE.get()), LucentGaiaCoreRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.VERDANT_GAIA_CORE.get()), VerdantGaiaCoreRecipeCategory.TYPE);
    }
}
