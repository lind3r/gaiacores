package com.seb.gaiacores.compat;

import com.seb.gaiacores.GaiaCores;
import com.seb.gaiacores.block.ModBlocks;
import com.seb.gaiacores.recipe.AdamantGaiaCoreRecipe;
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
import net.minecraft.world.item.Items;

public class AdamantGaiaCoreRecipeCategory implements IRecipeCategory<AdamantGaiaCoreRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(GaiaCores.MOD_ID, "adamant_gaia_core");
    public static final RecipeType<AdamantGaiaCoreRecipe> TYPE = new RecipeType<>(UID, AdamantGaiaCoreRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableStatic arrow;
    private final IDrawable downArrow;
    public static final ResourceLocation DOWN_ARROW_TEXTURE = ResourceLocation.fromNamespaceAndPath(GaiaCores.MOD_ID, "textures/gui/down_arrow.png");

    public AdamantGaiaCoreRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(150, 60);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                new ItemStack(ModBlocks.ADAMANT_GAIA_CORE.get()));
        this.arrow = helper.getRecipeArrow();
        this.downArrow = helper.drawableBuilder(DOWN_ARROW_TEXTURE, 0, 0, 8, 13)
                .setTextureSize(8, 13)
                .build();
    }

    @Override
    public void draw(AdamantGaiaCoreRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        arrow.draw(guiGraphics, 65, 20);
        downArrow.draw(guiGraphics, 35, 20);
    }

    @Override
    public RecipeType<AdamantGaiaCoreRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.gaiacores.adamant_gaia_core");
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
    public void setRecipe(IRecipeLayoutBuilder builder, AdamantGaiaCoreRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 30, 0).addItemStack(new ItemStack(Items.COAL));
        builder.addSlot(RecipeIngredientRole.CATALYST, 30, 40).addItemStack(new ItemStack(ModBlocks.ADAMANT_GAIA_CORE.get()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 110, 10).addItemStack(recipe.output());
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 110, 30).addItemStack(new ItemStack(Items.CREEPER_HEAD))
                .addRichTooltipCallback((recipeSlotView, tooltip) -> {
                    tooltip.clear();
                    tooltip.add(Component.translatable("misc.gaiacores.spawn_creeper"));
                });
    }
}
