package net.xexanos.poorores.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.xexanos.poorores.PoorOre;
import net.xexanos.poorores.reference.Reference;

public class Blocks {
    public static void preInit() {
        for (PoorOre poorOre : Reference.ORES_LIST) {
            GameRegistry.registerBlock(poorOre, "poor_" + poorOre.getName() + "_ore");
        }
    }

    public static void init() {
        for (PoorOre poorOre : Reference.ORES_LIST) {
            poorOre.registerOreDict();
        }
    }
}
