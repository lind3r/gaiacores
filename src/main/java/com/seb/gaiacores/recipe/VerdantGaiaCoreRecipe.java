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

public record VerdantGaiaCoreRecipe(Ingredient firstInput, Ingredient secondInput, ItemStack output)
        implements Recipe<VerdantGaiaCoreRecipeInput> {

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(firstInput);
        list.add(secondInput);
        return list;
    }

    @Override
    public boolean matches(VerdantGaiaCoreRecipeInput input, Level level) {
        if (level.isClientSide()) return false;

        return firstInput.test(input.getItem(0)) && secondInput.test(input.getItem(1));
    }

    @Override
    public ItemStack assemble(VerdantGaiaCoreRecipeInput pInput, HolderLookup.Provider pRegistries) {
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
        return ModRecipes.VERDANT_GAIA_CORE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.VERDANT_GAIA_CORE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<VerdantGaiaCoreRecipe> {
        public static final MapCodec<VerdantGaiaCoreRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC.fieldOf("first").forGetter(VerdantGaiaCoreRecipe::firstInput),
                Ingredient.CODEC.fieldOf("second").forGetter(VerdantGaiaCoreRecipe::secondInput),
                ItemStack.CODEC.fieldOf("result").forGetter(VerdantGaiaCoreRecipe::output)
        ).apply(inst, VerdantGaiaCoreRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, VerdantGaiaCoreRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, VerdantGaiaCoreRecipe::firstInput,
                        Ingredient.CONTENTS_STREAM_CODEC, VerdantGaiaCoreRecipe::secondInput,
                        ItemStack.STREAM_CODEC, VerdantGaiaCoreRecipe::output,
                        VerdantGaiaCoreRecipe::new);

        @Override
        public MapCodec<VerdantGaiaCoreRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, VerdantGaiaCoreRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
