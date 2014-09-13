package net.xexanos.poorores.handler;

import net.minecraftforge.common.config.Configuration;
import net.xexanos.poorores.reference.Reference;

import java.io.File;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class configHandler {
	
	public static Configuration config;

    public static void init(File configFile){
        config = new Configuration(configFile);

        int id = 0;

        //read worldgen config
        Reference.CONFIG_VANILLA_WORLDGEN = config.getBoolean("vanilla_worldgen", "worldgen", true, "Enable/Disable worldgen for vanilla Ores");

        //load all vanilla ores

        //read all matching categories and add corresponding block

        if(config.hasChanged()){
            config.save();
        }
    }
    
    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent e){
    	if (e.modID.equalsIgnoreCase(Reference.MOD_ID)){
             loadConfig();
        }
    }

    public void loadConfig(){

    }
}
