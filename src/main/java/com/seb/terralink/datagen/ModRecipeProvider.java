package com.seb.terralink.datagen;

import com.seb.terralink.TerraLinkMain;
import com.seb.terralink.blocks.ModBlocks;
import com.seb.terralink.items.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        List<ItemLike> COFFEE_SMELTABLES = List.of(ModItems.RAW_COFFEE.get(),
                ModBlocks.COFFEE_ORE.get(), ModBlocks.COFFEE_DEEPSLATE_ORE.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.COFFEE_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.COFFEE.get())
                .unlockedBy(getHasName(ModItems.COFFEE.get()), has(ModItems.COFFEE.get())).save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COFFEE.get(), 9)
                .requires(ModBlocks.COFFEE_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.COFFEE_BLOCK.get()), has(ModBlocks.COFFEE_BLOCK.get())).save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COFFEE.get(), 32)
                .requires(ModBlocks.ENERGETIC_ESOTERIC_CORE.get())
                .unlockedBy(getHasName(ModBlocks.COFFEE_BLOCK.get()), has(ModBlocks.COFFEE_BLOCK.get()))
                .save(pRecipeOutput, TerraLinkMain.MOD_ID + ":coffee_from_energetic_esoteric_core");

        oreSmelting(pRecipeOutput, COFFEE_SMELTABLES, RecipeCategory.MISC, ModItems.COFFEE.get(), 0.25f, 200, "coffee");
        oreBlasting(pRecipeOutput, COFFEE_SMELTABLES, RecipeCategory.MISC, ModItems.COFFEE.get(), 0.25f, 100, "coffee");

    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, TerraLinkMain.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
