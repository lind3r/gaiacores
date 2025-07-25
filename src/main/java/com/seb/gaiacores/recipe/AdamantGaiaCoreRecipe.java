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

public record AdamantGaiaCoreRecipe(Ingredient inputItem, ItemStack output)
        implements Recipe<AdamantGaiaCoreRecipeInput> {

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(AdamantGaiaCoreRecipeInput pInput, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }

        return inputItem.test(pInput.getItem(0));
    }

    @Override
    public ItemStack assemble(AdamantGaiaCoreRecipeInput pInput, HolderLookup.Provider pRegistries) {
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
        return ModRecipes.ADAMANT_GAIA_CORE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ADAMANT_GAIA_CORE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<AdamantGaiaCoreRecipe> {
        public static final MapCodec<AdamantGaiaCoreRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(AdamantGaiaCoreRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(AdamantGaiaCoreRecipe::output)
        ).apply(inst, AdamantGaiaCoreRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, AdamantGaiaCoreRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, AdamantGaiaCoreRecipe::inputItem,
                        ItemStack.STREAM_CODEC, AdamantGaiaCoreRecipe::output,
                        AdamantGaiaCoreRecipe::new);

        @Override
        public MapCodec<AdamantGaiaCoreRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AdamantGaiaCoreRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}