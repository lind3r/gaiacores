package com.seb.gaiacores.compat;

import com.seb.gaiacores.GaiaCores;
import com.seb.gaiacores.block.ModBlocks;
import com.seb.gaiacores.recipe.VolcanicGaiaCoreRecipe;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;

public class VolcanicGaiaCoreRecipeCategory implements IRecipeCategory<VolcanicGaiaCoreRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(GaiaCores.MOD_ID, "volcanic_gaia_core");
    public static final RecipeType<VolcanicGaiaCoreRecipe> TYPE = new RecipeType<>(UID, VolcanicGaiaCoreRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableStatic arrow;
    private final IDrawable downArrow;
    public static final ResourceLocation DOWN_ARROW_TEXTURE = ResourceLocation.fromNamespaceAndPath(GaiaCores.MOD_ID, "textures/gui/down_arrow.png");

    public VolcanicGaiaCoreRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(150, 60);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                new ItemStack(ModBlocks.VOLCANIC_GAIA_CORE.get()));
        this.arrow = helper.getRecipeArrow();
        this.downArrow = helper.drawableBuilder(DOWN_ARROW_TEXTURE, 0, 0, 8, 13)
                .setTextureSize(8, 13)
                .build();
    }

    @Override
    public void draw(VolcanicGaiaCoreRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        guiGraphics.drawString(Minecraft.getInstance().font, "Any monster", 10, 5, 0x404040, false);
        arrow.draw(guiGraphics, 65, 20);
        downArrow.draw(guiGraphics, 35, 20);
    }

    @Override
    public RecipeType<VolcanicGaiaCoreRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.gaiacores.volcanic_gaia_core");
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
    public void setRecipe(IRecipeLayoutBuilder builder, VolcanicGaiaCoreRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 30, 40).addFluidStack(Fluids.LAVA);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 110, 20).addFluidStack(Fluids.LAVA, 1000);
    }
}
