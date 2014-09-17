package net.xexanos.poorores;

import net.minecraft.item.Item;
import net.xexanos.poorores.reference.Reference;

public class Nugget extends Item {
    private Item ingot;

    public Nugget(String unlocalizedName) {
        super();
        this.setUnlocalizedName(unlocalizedName);
    }

    @Override
    public Item setUnlocalizedName(String unlocalizedName) {
        super.setUnlocalizedName(Reference.RESOURCE_PREFIX + unlocalizedName);
        return this;
    }


}
