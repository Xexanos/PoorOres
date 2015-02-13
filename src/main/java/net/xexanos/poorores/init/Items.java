package net.xexanos.poorores.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.xexanos.poorores.Nugget;
import net.xexanos.poorores.reference.Reference;

public class Items {
    public static void preInit() {
        for (Nugget nugget : Reference.NUGGETS_LIST) {
            GameRegistry.registerItem(nugget, nugget.getName());
        }
        for (Nugget dust : Reference.DUSTS_LIST) {
            GameRegistry.registerItem(dust, dust.getName());
        }
    }

    public static void init() {
        for (Nugget nugget : Reference.NUGGETS_LIST) {
            nugget.registerOreDict();
            nugget.registerSmelting();
        }
        for (Nugget dust : Reference.DUSTS_LIST) {
            dust.registerOreDict();
        }
    }

    public static void postInit() {
        for (Nugget nugget : Reference.NUGGETS_LIST) {
            nugget.registerCrafting();
        }
    }
}
