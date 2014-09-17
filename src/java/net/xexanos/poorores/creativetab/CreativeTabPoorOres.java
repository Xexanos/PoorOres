package net.xexanos.poorores.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.xexanos.poorores.init.Items;
import net.xexanos.poorores.reference.Reference;

public class CreativeTabPoorOres {
    public static final CreativeTabs PO_TAB = new CreativeTabs(Reference.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return Items.IRON_NUGGET;
        }


    };
}
