package com.seb.gaiacore.datagen;

import com.seb.gaiacore.GaiaCore;
import com.seb.gaiacore.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, GaiaCore.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // basicItem(ModItems.GAIA_SCANNER.get()); // Manual JSON
        // basicItem(ModItems.COFFEE.get());
    }
}