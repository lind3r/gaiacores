package com.seb.gaiacore.datagen;

import com.seb.gaiacore.GaiaCore;
import com.seb.gaiacore.block.ModBlocks;
import com.seb.gaiacore.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GAIA_SCANNER.get(), 1)
                .pattern("  A")
                .pattern("BBB")
                .pattern("   ")
                .define('A', Items.LAPIS_LAZULI)
                .define('B', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.LAPIS_LAZULI), has(Items.LAPIS_LAZULI))
                .save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.GAIA_CORE_ANALYZER.get(), 1)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Items.LAPIS_LAZULI)
                .define('B', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.LAPIS_LAZULI), has(Items.LAPIS_LAZULI))
                .save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GAIA_SCANNER.get(), 1)
                .requires(ModItems.GAIA_SCANNER.get())
                .unlockedBy(getHasName(ModItems.GAIA_SCANNER.get()), has(ModItems.GAIA_SCANNER.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID, "gaia_scanner_reset"));
    }
}
