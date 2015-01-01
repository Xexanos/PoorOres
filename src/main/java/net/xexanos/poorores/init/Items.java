package net.xexanos.poorores.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.xexanos.poorores.Nugget;
import net.xexanos.poorores.reference.Reference;

public class Items {
    public static void preInit() {
        for (Nugget nugget : Reference.NUGGETS_LIST) {
            GameRegistry.registerItem(nugget, nugget.getName());
        }
    }

    public static void init() {
        for (Nugget nugget : Reference.NUGGETS_LIST) {
            nugget.registerOreDict();
            nugget.registerRecipes();
        }

    }
}
