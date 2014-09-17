package net.xexanos.poorores.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.xexanos.poorores.Nugget;

public class Items {
    public static final Nugget IRON_NUGGET = new Nugget("iron_nugget");

    public static void init() {
        GameRegistry.registerItem(IRON_NUGGET, "iron_nugget");
    }
}
