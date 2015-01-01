package net.xexanos.poorores.reference;

import net.xexanos.poorores.Nugget;
import net.xexanos.poorores.PoorOre;

import java.util.LinkedList;

public class Reference {
    public static final String MOD_ID = "PoorOres";
    public static final String MOD_NAME = "PoorOres";
    public static final String VERSION = "1.7.10-1.3.2";
    public static final String RESOURCE_PREFIX = MOD_ID.toLowerCase() + ":";
    public static final String CONFIG_PREFIX = "ores.block_";

    public static LinkedList<PoorOre> ORES_LIST = new LinkedList<PoorOre>();
    public static LinkedList<Nugget> NUGGETS_LIST = new LinkedList<Nugget>();
    public static boolean CONFIG_WORLDGEN_COAL = false;
    public static boolean CONFIG_WORLDGEN_DIAMOND = false;
    public static boolean CONFIG_WORLDGEN_EMERALD = false;
    public static boolean CONFIG_WORLDGEN_GOLD = false;
    public static boolean CONFIG_WORLDGEN_IRON = false;
    public static boolean CONFIG_WORLDGEN_LAPIS = false;
    public static boolean CONFIG_WORLDGEN_REDSTONE = false;
    public static boolean CONFIG_WORLDGEN_QUARTZ = false;
    public static boolean CONFIG_ADD_CRAFTING = true;
    public static boolean CONFIG_ADD_SMELTING = true;
}
