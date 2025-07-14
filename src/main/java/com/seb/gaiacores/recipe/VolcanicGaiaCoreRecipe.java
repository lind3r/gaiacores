package com.seb.gaiacores.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record VolcanicGaiaCoreRecipe(Ingredient inputItem, ItemStack output)
        implements Recipe<VolcanicGaiaCoreRecipeInput> {

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.create();
    }

    @Override
    public boolean matches(VolcanicGaiaCoreRecipeInput pInput, Level pLevel) {
        return !pLevel.isClientSide();
    }

    @Override
    public ItemStack assemble(VolcanicGaiaCoreRecipeInput pInput, HolderLookup.Provider pRegistries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.VOLCANIC_GAIA_CORE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.VOLCANIC_GAIA_CORE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<VolcanicGaiaCoreRecipe> {
        public static final MapCodec<VolcanicGaiaCoreRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC.fieldOf("ingredient").forGetter(VolcanicGaiaCoreRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(VolcanicGaiaCoreRecipe::output)
        ).apply(inst, VolcanicGaiaCoreRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, VolcanicGaiaCoreRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, VolcanicGaiaCoreRecipe::inputItem,
                        ItemStack.STREAM_CODEC, VolcanicGaiaCoreRecipe::output,
                        VolcanicGaiaCoreRecipe::new);

        @Override
        public MapCodec<VolcanicGaiaCoreRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, VolcanicGaiaCoreRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
