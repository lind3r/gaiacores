package com.seb.gaiacore.blocks.custom;

public class EnergeticGaiaCore extends GaiaCoreBase {
    GaiaCoreVariant gaiaCoreVariant;

    public EnergeticGaiaCore(Properties properties) {
        super(properties);
        this.gaiaCoreVariant = GaiaCoreVariant.ENERGETIC;
    }

    public GaiaCoreVariant getGaiaCoreVariant() {
        return gaiaCoreVariant;
    }
}
