package com.seb.terralink.datagen;

import com.seb.terralink.TerraLinkMain;
import com.seb.terralink.items.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TerraLinkMain.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.COFFEE.get());
        basicItem(ModItems.RAW_COFFEE.get());

        basicItem(ModItems.ESOTERIC_SCANNER.get());
    }
}