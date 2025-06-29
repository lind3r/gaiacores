package com.seb.gaiacore.datagen;

import com.seb.gaiacore.GaiaCore;
import com.seb.gaiacore.blocks.ModBlocks;
import com.seb.gaiacore.blocks.custom.GaiaCoreBase;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, GaiaCore.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

//        blockWithItem(ModBlocks.ENERGETIC_GAIA_CORE);

        gaiaCore(ModBlocks.ENERGETIC_GAIA_CORE, GaiaCoreBase.Variant.ENERGETIC);
        gaiaCore(ModBlocks.VOLCANIC_GAIA_CORE, GaiaCoreBase.Variant.VOLCANIC);

//        stairsBlock(ModBlocks.ALEXANDRITE_STAIRS.get(), blockTexture(ModBlocks.ALEXANDRITE.get()));
//        slabBlock(ModBlocks.ALEXANDRITE_SLAB.get(), blockTexture(ModBlocks.ALEXANDRITE.get()), blockTexture(ModBlocks.ALEXANDRITE.get()));
//
//        buttonBlock(ModBlocks.ALEXANDRITE_BUTTON.get(), blockTexture(ModBlocks.ALEXANDRITE.get()));
//        pressurePlateBlock(ModBlocks.ALEXANDRITE_PRESSURE_PLATE.get(), blockTexture(ModBlocks.ALEXANDRITE.get()));
//
//        fenceBlock(ModBlocks.ALEXANDRITE_FENCE.get(), blockTexture(ModBlocks.ALEXANDRITE.get()));
//        fenceGateBlock(ModBlocks.ALEXANDRITE_FENCE_GATE.get(), blockTexture(ModBlocks.ALEXANDRITE.get()));
//        wallBlock(ModBlocks.ALEXANDRITE_WALL.get(), blockTexture(ModBlocks.ALEXANDRITE.get()));
//
//        doorBlockWithRenderType(ModBlocks.ALEXANDRITE_DOOR.get(), modLoc("block/alexandrite_door_bottom"), modLoc("block/alexandrite_door_top"), "cutout");
//        trapdoorBlockWithRenderType(ModBlocks.ALEXANDRITE_TRAPDOOR.get(), modLoc("block/alexandrite_trapdoor"), true, "cutout");
//
//        blockItem(ModBlocks.ALEXANDRITE_STAIRS);
//        blockItem(ModBlocks.ALEXANDRITE_SLAB);
//        blockItem(ModBlocks.ALEXANDRITE_PRESSURE_PLATE);
//        blockItem(ModBlocks.ALEXANDRITE_FENCE_GATE);
//        blockItem(ModBlocks.ALEXANDRITE_TRAPDOOR, "_bottom");
    }

    private void gaiaCore(RegistryObject<Block> block, GaiaCoreBase.Variant variant) {
        String name_powered = variant.toString().toLowerCase() + "_gaia_core_" + "powered";
        String name_unpowered = variant.toString().toLowerCase() + "_gaia_core_" + "unpowered";

        getVariantBuilder(block.get()).forAllStates(state -> {
            if(state.getValue(GaiaCoreBase.POWERED)) {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(name_powered,
                        ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID, "block/" + name_powered)))};
            } else {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(name_unpowered,
                        ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID, "block/" + name_unpowered)))};
            }
        });
        simpleBlockItem(block.get(), models().cubeAll(name_powered,
                ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID, "block/" + name_powered)));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }


//    private void blockItem(RegistryObject<? extends Block> blockRegistryObject) {
//        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("tutorialmod:block/" +
//                ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
//    }
//
//    private void blockItem(RegistryObject<? extends Block> blockRegistryObject, String appendix) {
//        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("tutorialmod:block/" +
//                ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath() + appendix));
//    }
}
