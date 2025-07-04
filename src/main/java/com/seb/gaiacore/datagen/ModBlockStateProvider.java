package com.seb.gaiacore.datagen;

import com.seb.gaiacore.GaiaCore;
import com.seb.gaiacore.block.ModBlocks;
import com.seb.gaiacore.block.custom.GaiaCoreBase;
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
        gaiaCore(ModBlocks.LUCENT_GAIA_CORE, GaiaCoreBase.GaiaCoreVariant.LUCENT);
        gaiaCore(ModBlocks.VOLCANIC_GAIA_CORE, GaiaCoreBase.GaiaCoreVariant.VOLCANIC);
        gaiaCore(ModBlocks.VERDANT_GAIA_CORE, GaiaCoreBase.GaiaCoreVariant.VERDANT);
        gaiaCore(ModBlocks.CHARRED_GAIA_CORE, GaiaCoreBase.GaiaCoreVariant.CHARRED);
        gaiaCore(ModBlocks.ADAMANT_GAIA_CORE, GaiaCoreBase.GaiaCoreVariant.ADAMANT);

        analyzerBlock(ModBlocks.GAIA_CORE_ANALYZER);
    }

    private void gaiaCore(RegistryObject<Block> block, GaiaCoreBase.GaiaCoreVariant gaiaCoreVariant) {
        String name_powered = gaiaCoreVariant.toString().toLowerCase() + "_gaia_core_" + "powered";
        String name_unpowered = gaiaCoreVariant.toString().toLowerCase() + "_gaia_core_" + "unpowered";

        getVariantBuilder(block.get()).forAllStates(state -> {
            if(state.getValue(GaiaCoreBase.POWERED)) {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(name_powered,
                        ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID, "block/" + name_powered)))};
            } else {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(name_unpowered,
                        ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID, "block/" + name_unpowered)))};
            }
        });

        simpleBlockItem(block.get(), models().cubeAll(name_unpowered,
                ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID, "block/" + name_unpowered)));
    }

    private void analyzerBlock(RegistryObject<Block> block) {
        String blockName = block.getId().getPath();

        simpleBlockWithItem(block.get(),
                models().orientable(
                        blockName,
                        ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID, "block/analyzer_side"),
                        ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID, "block/analyzer_front"),
                        ResourceLocation.fromNamespaceAndPath(GaiaCore.MOD_ID, "block/analyzer_side")
                )
        );
    }
}
