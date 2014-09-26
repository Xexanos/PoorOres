package net.xexanos.poorores;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.xexanos.poorores.handler.configHandler;
import net.xexanos.poorores.handler.eventHandler;
import net.xexanos.poorores.init.Blocks;
import net.xexanos.poorores.init.Items;
import net.xexanos.poorores.proxy.IProyx;
import net.xexanos.poorores.reference.Reference;
import net.xexanos.poorores.worldgen.Generator;

import java.io.File;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class PoorOresMod {
    private File configName;

    @Mod.Instance(Reference.MOD_ID)
    public static PoorOresMod instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProyx proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        configName = e.getSuggestedConfigurationFile();

        configHandler.init(configName);

        Blocks.preInit();
        Items.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        MinecraftForge.ORE_GEN_BUS.register(new eventHandler());
        Blocks.init();
        Items.init();
        GameRegistry.registerWorldGenerator(new Generator(), 16);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    }
}
