package net.xexanos.poorores.reference;

import net.xexanos.poorores.Nugget;
import net.xexanos.poorores.Ore;

import java.util.LinkedList;

public class Reference {
    public static final String MOD_ID = "PoorOres";
    public static final String MOD_NAME = "PoorOres";
    public static final String VERSION = "1.7.10-0.0.1";
    public static final String CLIENT_PROXY_CLASS = "net.xexanos.poorores.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "net.xexanos.poorores.proxy.ServerProxy";
    public static final String RESOURCE_PREFIX = MOD_ID.toLowerCase() + ":";
    public static final String CONFIG_PREFIX = "ores.block_";
    public static final LinkedList<Ore> ORES_LIST = new LinkedList<Ore>();
    public static final LinkedList<Nugget> NUGGETS_LIST =new LinkedList<Nugget>();

    public static boolean CONFIG_VANILLA_WORLDGEN = true;
}
