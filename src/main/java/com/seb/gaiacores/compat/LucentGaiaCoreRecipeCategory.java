package com.seb.gaiacores.compat;

import com.seb.gaiacores.GaiaCores;
import com.seb.gaiacores.block.ModBlocks;
import com.seb.gaiacores.recipe.LucentGaiaCoreRecipe;
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
import net.minecraft.world.item.ItemStack;

public class LucentGaiaCoreRecipeCategory implements IRecipeCategory<LucentGaiaCoreRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(GaiaCores.MOD_ID, "lucent_gaia_core");
    public static final RecipeType<LucentGaiaCoreRecipe> TYPE = new RecipeType<>(UID, LucentGaiaCoreRecipe.class);
    public static final ResourceLocation SUN_TEXTURE = ResourceLocation.fromNamespaceAndPath(GaiaCores.MOD_ID, "textures/gui/sun.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableStatic arrow;
    private final IDrawable sunIcon;

    public LucentGaiaCoreRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(150, 60);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                new ItemStack(ModBlocks.LUCENT_GAIA_CORE.get()));
        this.sunIcon = helper.drawableBuilder(SUN_TEXTURE, 0, 0, 32, 32)
                .setTextureSize(32, 32)
                .build();
        this.arrow = helper.getRecipeArrow();
    }

    @Override
    public void draw(LucentGaiaCoreRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        sunIcon.draw(guiGraphics, 15, 8);
        arrow.draw(guiGraphics, 65, 15);
    }

    @Override
    public RecipeType<LucentGaiaCoreRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.gaiacores.lucent_gaia_core");
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
    public void setRecipe(IRecipeLayoutBuilder builder, LucentGaiaCoreRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.OUTPUT, 110, 15).addItemStack(recipe.getResultItem(null));
    }
}
