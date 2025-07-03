package com.seb.gaiacore.datagen;

import com.seb.gaiacore.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider pRegistries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), pRegistries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.GAIA_CORE_ANALYZER.get());

        this.add(ModBlocks.ENERGETIC_GAIA_CORE.get(),
                block -> createNameableBlockEntityTable(ModBlocks.ENERGETIC_GAIA_CORE.get()));
        this.add(ModBlocks.VOLCANIC_GAIA_CORE.get(),
                block -> createNameableBlockEntityTable(ModBlocks.VOLCANIC_GAIA_CORE.get()));
        this.add(ModBlocks.VERDANT_GAIA_CORE.get(),
                block -> createNameableBlockEntityTable(ModBlocks.VERDANT_GAIA_CORE.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}