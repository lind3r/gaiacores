package com.seb.gaiacore.blocks.custom;

public class VolcanicGaiaCore extends GaiaCoreBase {
    GaiaCoreVariant gaiaCoreVariant;

    public VolcanicGaiaCore(Properties properties) {
        super(properties);
        this.gaiaCoreVariant = GaiaCoreVariant.VOLCANIC;
    }

    public GaiaCoreVariant getGaiaCoreVariant() {
        return gaiaCoreVariant;
    }
}
