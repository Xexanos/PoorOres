package net.xexanos.poorores.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.xexanos.poorores.Ore;
import net.xexanos.poorores.reference.Reference;

public class Blocks {
    public static void preInit() {
        for (Ore ore : Reference.ORES_LIST) {
            GameRegistry.registerBlock(ore, "poor_" + ore.getName() + "_ore");
        }
    }

    public static void init() {
        for (Ore ore : Reference.ORES_LIST) {
            ore.registerOreDict();
            ore.setHarvestLevel();
        }
    }
}
