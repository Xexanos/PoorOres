package net.xexanos.poorores.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.xexanos.poorores.reference.Reference;

public class CreativeTabPoorOres {
    public static final CreativeTabs POOR_ORES_TAB = new CreativeTabs(Reference.MOD_ID.toLowerCase()) {
        @Override
        public Item getTabIconItem() {
            return Reference.NUGGETS_LIST.getFirst();
        }
    };
}
