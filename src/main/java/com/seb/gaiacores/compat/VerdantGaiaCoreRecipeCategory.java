package com.seb.gaiacores.compat;

import com.seb.gaiacores.GaiaCores;
import com.seb.gaiacores.block.ModBlocks;
import com.seb.gaiacores.recipe.VerdantGaiaCoreRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class VerdantGaiaCoreRecipeCategory implements IRecipeCategory<VerdantGaiaCoreRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(GaiaCores.MOD_ID, "verdant_gaia_core");
    public static final RecipeType<VerdantGaiaCoreRecipe> TYPE = new RecipeType<>(UID, VerdantGaiaCoreRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableStatic arrow;

    public VerdantGaiaCoreRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(150, 60);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                new ItemStack(ModBlocks.VERDANT_GAIA_CORE.get()));
        this.arrow = helper.getRecipeArrow();
    }

    @Override
    public void draw(VerdantGaiaCoreRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        arrow.draw(guiGraphics, 65, 15);
    }

    @Override
    public RecipeType<VerdantGaiaCoreRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.gaiacores.verdant_gaia_core");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, VerdantGaiaCoreRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 20, 15).addIngredients(Ingredient.of(ItemTags.PLANKS));
        builder.addSlot(RecipeIngredientRole.INPUT, 40, 15).addIngredients(Ingredient.of(Items.STICK));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 110, 15).addItemStack(recipe.output());
    }
}
