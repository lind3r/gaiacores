package com.seb.gaiacore.datagen;

import com.seb.gaiacore.GaiaCore;
import com.seb.gaiacore.items.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, GaiaCore.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.GAIA_SCANNER.get());
        basicItem(ModItems.COFFEE.get());

//        buttonItem(ModBlocks.ALEXANDRITE_BUTTON, ModBlocks.ALEXANDRITE);
//        fenceItem(ModBlocks.ALEXANDRITE_FENCE, ModBlocks.ALEXANDRITE);
//        wallItem(ModBlocks.ALEXANDRITE_WALL, ModBlocks.ALEXANDRITE);
//        simpleBlockItem(ModBlocks.ALEXANDRITE_DOOR);
    }


//    public void buttonItem(RegistryObject<? extends Block> block, RegistryObject<Block> baseBlock) {
//        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
//                .texture("texture",  ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID,
//                        "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
//    }
//
//    public void fenceItem(RegistryObject<? extends Block> block, RegistryObject<Block> baseBlock) {
//        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
//                .texture("texture",  ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID,
//                        "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
//    }
//
//    public void wallItem(RegistryObject<? extends Block> block, RegistryObject<Block> baseBlock) {
//        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
//                .texture("wall",  ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID,
//                        "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
//    }
//
//    private ItemModelBuilder simpleBlockItem(RegistryObject<? extends Block> item) {
//        return withExistingParent(item.getId().getPath(),
//                ResourceLocation.parse("item/generated")).texture("layer0",
//                ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID,"item/" + item.getId().getPath()));
//    }
}