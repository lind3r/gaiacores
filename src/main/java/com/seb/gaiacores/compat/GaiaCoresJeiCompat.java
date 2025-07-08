package com.seb.gaiacores.compat;

import net.minecraftforge.fml.InterModComms;

public class GaiaCoresJeiCompat {
    public static void init() {
        InterModComms.sendTo("jei", "jeiPlugin", JEIGaiaCoreModPlugin::new);
    }
}
