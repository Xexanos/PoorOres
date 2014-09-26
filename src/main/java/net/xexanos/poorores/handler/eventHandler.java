package net.xexanos.poorores.handler;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.xexanos.poorores.reference.Reference;

public class eventHandler {

    @SubscribeEvent
    public void onGenerateMinable(OreGenEvent.GenerateMinable event) {
        if (event.type == OreGenEvent.GenerateMinable.EventType.COAL) {
            event.setResult((Reference.CONFIG_WORLDGEN_COAL) ? Event.Result.ALLOW : Event.Result.DENY);
        } else if (event.type == OreGenEvent.GenerateMinable.EventType.DIAMOND) {
            event.setResult((Reference.CONFIG_WORLDGEN_DIAMOND) ? Event.Result.ALLOW : Event.Result.DENY);
/*
        } else if (event.type == OreGenEvent.GenerateMinable.EventType.EMERALD) {
            event.setResult((Reference.CONFIG_WORLDGEN_COAL) ? Event.Result.ALLOW : Event.Result.DENY);
*/
        } else if (event.type == OreGenEvent.GenerateMinable.EventType.GOLD) {
            event.setResult((Reference.CONFIG_WORLDGEN_GOLD) ? Event.Result.ALLOW : Event.Result.DENY);
        } else if (event.type == OreGenEvent.GenerateMinable.EventType.IRON) {
            event.setResult((Reference.CONFIG_WORLDGEN_IRON) ? Event.Result.ALLOW : Event.Result.DENY);
        } else if (event.type == OreGenEvent.GenerateMinable.EventType.LAPIS) {
            event.setResult((Reference.CONFIG_WORLDGEN_LAPIS) ? Event.Result.ALLOW : Event.Result.DENY);
        } else if (event.type == OreGenEvent.GenerateMinable.EventType.REDSTONE) {
            event.setResult((Reference.CONFIG_WORLDGEN_REDSTONE) ? Event.Result.ALLOW : Event.Result.DENY);
        } else if (event.type == OreGenEvent.GenerateMinable.EventType.QUARTZ) {
            event.setResult((Reference.CONFIG_WORLDGEN_QUARTZ) ? Event.Result.ALLOW : Event.Result.DENY);
        }
    }
}
