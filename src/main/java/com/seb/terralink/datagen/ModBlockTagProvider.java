package com.seb.terralink.datagen;

import com.seb.terralink.TerraLinkMain;
import com.seb.terralink.blocks.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TerraLinkMain.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.COFFEE_BLOCK.get())
                .add(ModBlocks.RAW_COFFEE_BLOCK.get())
                .add(ModBlocks.COFFEE_ORE.get())
                .add(ModBlocks.COFFEE_DEEPSLATE_ORE.get())
                .add(ModBlocks.ENERGETIC_ESOTERIC_CORE.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.COFFEE_DEEPSLATE_ORE.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.ENERGETIC_ESOTERIC_CORE.get());
    }
}