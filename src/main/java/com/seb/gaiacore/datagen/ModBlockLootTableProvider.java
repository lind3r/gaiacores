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
        dropSelf(ModBlocks.ENERGETIC_GAIA_CORE.get());
        dropSelf(ModBlocks.VOLCANIC_GAIA_CORE.get());

//        dropSelf(ModBlocks.COFFEE_BLOCK.get());
//        dropSelf(ModBlocks.RAW_COFFEE_BLOCK.get());
//        dropSelf(ModBlocks.ALEXANDRITE.get());
//
//        this.add(ModBlocks.COFFEE_ORE.get(), block -> createOreDrop(
//                ModBlocks.COFFEE_ORE.get(), ModItems.RAW_COFFEE.get()));
//        this.add(ModBlocks.COFFEE_DEEPSLATE_ORE.get(), block -> createMultipleOreDrops(
//                ModBlocks.COFFEE_DEEPSLATE_ORE.get(), ModItems.RAW_COFFEE.get(), 22, 25));
//
//
//        dropSelf(ModBlocks.ALEXANDRITE_STAIRS.get());
//        this.add(ModBlocks.ALEXANDRITE_SLAB.get(),
//                block -> createSlabItemTable(ModBlocks.ALEXANDRITE_SLAB.get()));
//
//        dropSelf(ModBlocks.ALEXANDRITE_PRESSURE_PLATE.get());
//        dropSelf(ModBlocks.ALEXANDRITE_BUTTON.get());
//        dropSelf(ModBlocks.ALEXANDRITE_FENCE.get());
//        dropSelf(ModBlocks.ALEXANDRITE_FENCE_GATE.get());
//        dropSelf(ModBlocks.ALEXANDRITE_WALL.get());
//        dropSelf(ModBlocks.ALEXANDRITE_TRAPDOOR.get());
//
//        this.add(ModBlocks.ALEXANDRITE_DOOR.get(),
//                block -> createDoorTable(ModBlocks.ALEXANDRITE_DOOR.get()));
    }

//    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
//        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
//        return this.createSilkTouchDispatchTable(
//                pBlock, this.applyExplosionDecay(
//                        pBlock, LootItem.lootTableItem(item)
//                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
//                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
//                )
//        );
//    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}