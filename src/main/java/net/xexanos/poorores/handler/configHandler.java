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

    public static void preInit(File configFile) {
        config = new Configuration(configFile);

        //read worldgen config
        Reference.CONFIG_WORLDGEN_COAL = config.getBoolean("baseWorldgenCoal", Configuration.CATEGORY_GENERAL, false, "Enable/Disable worldgen for coal");
        Reference.CONFIG_WORLDGEN_DIAMOND = config.getBoolean("baseWorldgenDiamond", Configuration.CATEGORY_GENERAL, false, "Enable/Disable worldgen for diamond");
        Reference.CONFIG_WORLDGEN_EMERALD = config.getBoolean("baseWorldgenEmerald", Configuration.CATEGORY_GENERAL, false, "Enable/Disable worldgen for emerald");
        Reference.CONFIG_WORLDGEN_GOLD = config.getBoolean("baseWorldgenGold", Configuration.CATEGORY_GENERAL, false, "Enable/Disable worldgen for gold");
        Reference.CONFIG_WORLDGEN_IRON = config.getBoolean("baseWorldgenIron", Configuration.CATEGORY_GENERAL, false, "Enable/Disable worldgen for iron");
        Reference.CONFIG_WORLDGEN_LAPIS = config.getBoolean("baseWorldgenLapis", Configuration.CATEGORY_GENERAL, false, "Enable/Disable worldgen for lapis");
        Reference.CONFIG_WORLDGEN_REDSTONE = config.getBoolean("baseWorldgenRedstone", Configuration.CATEGORY_GENERAL, false, "Enable/Disable worldgen for redstone");
        Reference.CONFIG_WORLDGEN_QUARTZ = config.getBoolean("baseWorldgenQuartz", Configuration.CATEGORY_GENERAL, false, "Enable/Disable worldgen for quartz");
        Reference.CONFIG_ADD_CRAFTING = config.getBoolean("add_crafting", Configuration.CATEGORY_GENERAL, true, "Enable/Disable recipes for crafting");
        Reference.CONFIG_ADD_SMELTING = config.getBoolean("add_smelting", Configuration.CATEGORY_GENERAL, true, "Enable/Disable recipes for smelting");

        //adding vanilla ores
        config.get(Reference.CONFIG_PREFIX + "coal", "modID", "minecraft");
        config.get(Reference.CONFIG_PREFIX + "coal", "baseBlock", "coal_ore");
        config.get(Reference.CONFIG_PREFIX + "coal", "burnTime", 200);
        config.get(Reference.CONFIG_PREFIX + "coal", "veinHeight", 120);
        config.get(Reference.CONFIG_PREFIX + "coal", "veinRate", 16);
        config.get(Reference.CONFIG_PREFIX + "coal", "veinSize", 48);
        config.get(Reference.CONFIG_PREFIX + "diamond", "modID", "minecraft");
        config.get(Reference.CONFIG_PREFIX + "diamond", "baseBlock", "diamond_ore");
        config.get(Reference.CONFIG_PREFIX + "diamond", "veinHeight", 16);
        config.get(Reference.CONFIG_PREFIX + "diamond", "veinRate", 4);
        config.get(Reference.CONFIG_PREFIX + "diamond", "veinSize", 8);
        config.get(Reference.CONFIG_PREFIX + "emerald", "modID", "minecraft");
        config.get(Reference.CONFIG_PREFIX + "emerald", "baseBlock", "emerald_ore");
        config.get(Reference.CONFIG_PREFIX + "emerald", "oreRenderType", 1);
        config.get(Reference.CONFIG_PREFIX + "gold", "modID", "minecraft");
        config.get(Reference.CONFIG_PREFIX + "gold", "baseBlock", "gold_ore");
        config.get(Reference.CONFIG_PREFIX + "gold", "veinHeight", 32);
        config.get(Reference.CONFIG_PREFIX + "gold", "veinRate", 3);
        config.get(Reference.CONFIG_PREFIX + "gold", "veinSize", 32);
        config.get(Reference.CONFIG_PREFIX + "iron", "modID", "minecraft");
        config.get(Reference.CONFIG_PREFIX + "iron", "baseBlock", "iron_ore");
        config.get(Reference.CONFIG_PREFIX + "iron", "veinHeight", 64);
        config.get(Reference.CONFIG_PREFIX + "iron", "veinRate", 16);
        config.get(Reference.CONFIG_PREFIX + "iron", "veinSize", 32);
        config.get(Reference.CONFIG_PREFIX + "lapis", "modID", "minecraft");
        config.get(Reference.CONFIG_PREFIX + "lapis", "baseBlock", "lapis_ore");
        config.get(Reference.CONFIG_PREFIX + "lapis", "veinHeight", 32);
        config.get(Reference.CONFIG_PREFIX + "lapis", "veinRate", 8);
        config.get(Reference.CONFIG_PREFIX + "lapis", "veinSize", 32);
        config.get(Reference.CONFIG_PREFIX + "redstone", "modID", "minecraft");
        config.get(Reference.CONFIG_PREFIX + "redstone", "baseBlock", "redstone_ore");
        config.get(Reference.CONFIG_PREFIX + "redstone", "isDust", true);
        config.get(Reference.CONFIG_PREFIX + "redstone", "veinHeight", 16);
        config.get(Reference.CONFIG_PREFIX + "redstone", "veinRate", 4);
        config.get(Reference.CONFIG_PREFIX + "redstone", "veinSize", 32);
        config.get(Reference.CONFIG_PREFIX + "quartz", "modID", "minecraft");
        config.get(Reference.CONFIG_PREFIX + "quartz", "baseBlock", "quartz_ore");
        config.get(Reference.CONFIG_PREFIX + "quartz", "underlyingBlock", "minecraft:netherrack");
        config.get(Reference.CONFIG_PREFIX + "quartz", "oreRenderType", 2);
        config.get(Reference.CONFIG_PREFIX + "quartz", "veinHeight", 128);
        config.get(Reference.CONFIG_PREFIX + "quartz", "veinRate", 10);
        config.get(Reference.CONFIG_PREFIX + "quartz", "veinSize", 64);

        //read all matching categories and add corresponding blocks and items
        for (String category : config.getCategoryNames()) {
            if (category.startsWith(Reference.CONFIG_PREFIX)) {
                String name = category.substring(Reference.CONFIG_PREFIX.length());
                String modID = config.get(category, "modID", "minecraft").getString();
                if (modID.equals("minecraft") || Loader.isModLoaded(modID)) {
                    String baseBlockName = config.get(category, "baseBlock", "").getString();
                    int baseBlockMeta = config.get(category, "baseBlockMeta", 0).getInt();
                    boolean dust = config.get(category, "isDust", false).getBoolean();
                    String baseBlockTexture = config.get(category, "baseBlockTexture", modID + ":" + baseBlockName).getString();
                    String underlyingBlockName = config.get(category, "underlyingBlock", "minecraft:stone").getString();
                    Block underlyingBlock = Block.getBlockFromName(underlyingBlockName);
                    if (underlyingBlock != null) {
                        int oreRenderType = config.get(category, "oreRenderType", 0).getInt();
                        int nuggetRenderType = config.get(category, "nuggetRenderType", 0).getInt();
                        int burnTime = config.get(category, "burnTime", 0).getInt();
                        int veinRate = config.get(category, "veinRate", 0).getInt();
                        int veinSize = config.get(category, "veinSize", 0).getInt();
                        int veinHeight = config.get(category, "veinHeight", 0).getInt();
                        String dimWhiteListStr = config.get(category, "dimWhiteList", "").getString();
                        LinkedList<Integer> dimWhiteList = new LinkedList<Integer>();
                        if (dimWhiteListStr.length() != 0) {
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
                        if (dimBlackListStr.length() != 0) {
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
                        PoorOre poorOre = new PoorOre(name, modID, baseBlockName, baseBlockMeta, baseBlockTexture, underlyingBlock, underlyingBlockName, oreRenderType, veinRate, veinSize, veinHeight, dimWhiteList, dimBlackList);
                        Nugget nugget = new Nugget(name, poorOre, baseBlockMeta, dust, burnTime, nuggetRenderType);
                        poorOre.setNugget(nugget);
                        Reference.ORES_LIST.add(poorOre);
                        Reference.NUGGETS_LIST.add(nugget);
                    } else {
                        LogHelper.warn(name + ": Underlying Block \"" + underlyingBlockName + "\" not found.");
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
