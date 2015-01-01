package net.xexanos.poorores.handler;

import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.item.ItemStack;
import net.xexanos.poorores.Nugget;

public class fuelHandler implements IFuelHandler {
    @Override
    public int getBurnTime(ItemStack fuel) {
        if (fuel.getItem() instanceof Nugget) {
            Nugget n = (Nugget) fuel.getItem();
            return n.getBurnTime();
        } else {
            return 0;
        }
    }
}
