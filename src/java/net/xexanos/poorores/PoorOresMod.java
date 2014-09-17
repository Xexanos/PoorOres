package net.xexanos.poorores;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.xexanos.poorores.handler.configHandler;
import net.xexanos.poorores.init.Items;
import net.xexanos.poorores.proxy.IProyx;
import net.xexanos.poorores.reference.Reference;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class PoorOresMod {

    @Mod.Instance(Reference.MOD_ID)
    public static PoorOresMod instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProyx proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        configHandler.init(e.getSuggestedConfigurationFile());

        Items.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {

    }
}
