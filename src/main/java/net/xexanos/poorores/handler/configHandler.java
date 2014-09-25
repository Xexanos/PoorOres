package net.xexanos.poorores.handler;

import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;
import net.xexanos.poorores.Nugget;
import net.xexanos.poorores.PoorOre;
import net.xexanos.poorores.reference.Reference;
import net.xexanos.poorores.utility.LogHelper;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class configHandler {

    public static Configuration config;

    public static void init(File configFile) {
        config = new Configuration(configFile);

        //read worldgen config
        Reference.CONFIG_VANILLA_WORLDGEN = config.getBoolean("vanilla_worldgen", config.CATEGORY_GENERAL, true, "Enable/Disable worldgen for vanilla Ores");
        Reference.CONFIG_ADD_CRAFTING = config.getBoolean("add_crafting", config.CATEGORY_GENERAL, true, "Enable/Disable recipes for crafting");
        Reference.CONFIG_ADD_SMELTING = config.getBoolean("add_smelting", config.CATEGORY_GENERAL, true, "Enable/Disable recipes for smelting");

        //read all matching categories and add corresponding blocks and items
        for (String category : config.getCategoryNames()) {
            if (category.startsWith(Reference.CONFIG_PREFIX)) {
                String name = category.substring(Reference.CONFIG_PREFIX.length());
                String modID = config.get(category, "modID", "minecraft").getString();
                if (modID.equals("minecraft") || Loader.isModLoaded(modID)) {
                    String baseBlockName = config.get(category, "baseBlock", "").getString();
                    int baseBlockMeta = config.get(category, "baseBlockMeta", 0).getInt();
                    Block baseBlock = Block.getBlockFromName(modID + ":" + baseBlockName);
                    if (baseBlock != null) {
                        String baseBlockTexture = config.get(category, "baseBlockTexture", modID + ":" + baseBlockName).getString();
                        String underlyingBlockName = config.get(category, "underlyingBlock", "minecraft:stone").getString();
                        Block underlyingBlock = Block.getBlockFromName(underlyingBlockName);
                        if (underlyingBlock != null) {
                            int hardness = config.get(category, "hardness", 3).getInt();
                            int oreRenderType = config.get(category, "oreRenderType", 0).getInt();
                            int nuggetRenderType = config.get(category, "nuggetRenderType", 0).getInt();
                            int veinRate = config.get(category, "veinRate", 0).getInt();
                            int veinSize = config.get(category, "veinSize", 0).getInt();
                            int veinHeight = config.get(category, "veinHeight", 0).getInt();
                            String dimWhiteListStr = config.get(category, "dimWhiteList", "").getString();
                            LinkedList<Integer> dimWhiteList = new LinkedList<Integer>();
                            if (dimWhiteListStr.length() == 0) {
                                String[] dimWhiteListStrArray = dimWhiteListStr.split(",");
                                for (String entry : dimWhiteListStrArray) {
                                    try {
                                        dimWhiteList.add(Integer.parseInt(entry));
                                    } catch (NumberFormatException e) {
                                        LogHelper.warn(name + ": Could not parse " + entry + " to an integer.");
                                        LogHelper.warn(name + ": Entry will be ignored.");
                                    }
                                }
                            }
                            String dimBlackListStr = config.get(category, "dimBlackList", "").getString();
                            List<Integer> dimBlackList = new LinkedList<Integer>();
                            if (dimBlackListStr.length() == 0) {
                                String[] dimBlackListStrArray = dimBlackListStr.split(",");
                                for (String entry : dimBlackListStrArray) {
                                    try {
                                        dimBlackList.add(Integer.parseInt(entry));
                                    } catch (NumberFormatException e) {
                                        LogHelper.warn(name + ": Could not parse " + entry + " to an integer.");
                                        LogHelper.warn(name + ": Entry will be ignored.");
                                    }
                                }
                            }
                            PoorOre poorOre = new PoorOre(name, baseBlock, baseBlockTexture, underlyingBlock, underlyingBlockName, hardness, oreRenderType, veinRate, veinSize, veinHeight, dimWhiteList, dimBlackList);
                            Reference.ORES_LIST.add(poorOre);
                            Reference.NUGGETS_LIST.add(new Nugget(name, poorOre, modID, baseBlockMeta, nuggetRenderType));
                        } else {
                            LogHelper.warn(name + ": Underlying Block \"" + underlyingBlockName + "\" not found.");
                            LogHelper.warn(name + ": Ore will not be added.");
                        }

                    } else {
                        LogHelper.warn(name + ": Baseblock \"" + modID + ":" + baseBlockName + "\" not found.");
                        LogHelper.warn(name + ": Ore will not be added.");
                    }


                } else {
                    LogHelper.warn(name + ": Corresponding mod \"" + modID + "\" is missing.");
                    LogHelper.warn(name + ": Ore will not be added.");
                }
            }
        }

        if (config.hasChanged()) {
            config.save();
        }
    }
}
