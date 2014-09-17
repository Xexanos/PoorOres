package net.xexanos.poorores.handler;

import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;
import net.xexanos.poorores.Ore;
import net.xexanos.poorores.reference.Reference;
import net.xexanos.poorores.utility.LogHelper;

import java.io.File;

public class configHandler {

    public static Configuration config;

    public static void init(File configFile) {
        config = new Configuration(configFile);

        //read worldgen config
        Reference.CONFIG_VANILLA_WORLDGEN = config.getBoolean("vanilla_worldgen", "worldgen", true, "Enable/Disable worldgen for vanilla Ores");

        //load all vanilla ores
        Reference.ORES_LIST.add(new Ore("coal", Block.getBlockFromName("minecraft:coal_ore"), Block.getBlockFromName("minecraft:stone"), 3));
        Reference.ORES_LIST.add(new Ore("diamond", Block.getBlockFromName("minecraft:diamond_ore"), Block.getBlockFromName("minecraft:stone"), 3));
        Reference.ORES_LIST.add(new Ore("emerald", Block.getBlockFromName("minecraft:emerald_ore"), Block.getBlockFromName("minecraft:stone"), 3));
        Reference.ORES_LIST.add(new Ore("gold", Block.getBlockFromName("minecraft:gold_ore"), Block.getBlockFromName("minecraft:stone"), 3));
        Reference.ORES_LIST.add(new Ore("iron", Block.getBlockFromName("minecraft:iron_ore"), Block.getBlockFromName("minecraft:stone"), 3));
        Reference.ORES_LIST.add(new Ore("lapis", Block.getBlockFromName("minecraft:lapis_ore"), Block.getBlockFromName("minecraft:stone"), 3));
        Reference.ORES_LIST.add(new Ore("redstone", Block.getBlockFromName("minecraft:redstone_ore"), Block.getBlockFromName("minecraft:stone"), 3));
        Reference.ORES_LIST.add(new Ore("quartz", Block.getBlockFromName("minecraft:quartz_ore"), Block.getBlockFromName("minecraft:netherrack"), 3));


        //read all matching categories and add corresponding blocks and items
        for (String category : config.getCategoryNames()) {
            if (category.startsWith(Reference.CONFIG_PREFIX)) {
                String name = category.substring(Reference.CONFIG_PREFIX.length());
                String modID = config.get(category, "modID", "minecraft").getString();
                if (modID.equals("minecraft") || Loader.isModLoaded(modID)) {
                    String baseBlockName = config.get(category, "baseBlock", "").getString();
                    int baseBlockMeta = config.get(category, "baseBlockMeta", 0).getInt();
                    Block baseBlock = Block.getBlockFromName(modID + ":" + baseBlockName);// + ":" + baseBlockMeta);
                    if (baseBlock != null) {
                        String underlyingBlockName = config.get(category, "underlyingBlock", "minecraft:stone").getString();
                        Block underlyingBlock = Block.getBlockFromName(underlyingBlockName);
                        if (underlyingBlock != null) {
                            int hardness = config.get(category, "hardness", 3).getInt();

                            Reference.ORES_LIST.add(new Ore(name, baseBlock, underlyingBlock, hardness));
                        } else {
                            LogHelper.warn("Underlying Block \"" + underlyingBlockName + "\" not found.");
                            LogHelper.warn("Ore will not be added.");
                        }

                    } else {
                        LogHelper.warn("Baseblock \"" + modID + ":" + baseBlockName + "\" not found.");
                        LogHelper.warn("Ore will not be added.");
                    }


                } else {
                    LogHelper.warn("Corresponding mod \"" + modID + "\" for " + Reference.CONFIG_PREFIX + name + " is missing.");
                    LogHelper.warn("Ore will not be added.");
                }
            }
        }

        if (config.hasChanged()) {
            config.save();
        }
    }

    public void loadConfig() {

    }
}
