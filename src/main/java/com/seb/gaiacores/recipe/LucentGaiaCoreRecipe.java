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

public record LucentGaiaCoreRecipe(Ingredient inputItem, ItemStack output)
        implements Recipe<LucentGaiaCoreRecipeInput> {

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.create();
    }

    @Override
    public boolean matches(LucentGaiaCoreRecipeInput pInput, Level pLevel) {
        if (pLevel.isClientSide()) return false;

        if (inputItem == Ingredient.EMPTY) {
            return true;
        }

        return inputItem.test(pInput.input());
    }

    @Override
    public ItemStack assemble(LucentGaiaCoreRecipeInput pInput, HolderLookup.Provider pRegistries) {
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
        return ModRecipes.LUCENT_GAIA_CORE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.LUCENT_GAIA_CORE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<LucentGaiaCoreRecipe> {
        public static final MapCodec<LucentGaiaCoreRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC.fieldOf("ingredient").forGetter(LucentGaiaCoreRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(LucentGaiaCoreRecipe::output)
        ).apply(inst, LucentGaiaCoreRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, LucentGaiaCoreRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, LucentGaiaCoreRecipe::inputItem,
                        ItemStack.STREAM_CODEC, LucentGaiaCoreRecipe::output,
                        LucentGaiaCoreRecipe::new);

        @Override
        public MapCodec<LucentGaiaCoreRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, LucentGaiaCoreRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }


}
