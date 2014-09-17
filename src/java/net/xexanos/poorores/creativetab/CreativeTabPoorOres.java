package net.xexanos.poorores.creativetab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.xexanos.poorores.init.Items;
import net.xexanos.poorores.reference.Reference;

public class CreativeTabPoorOres {
    public static final CreativeTabs POOR_ORES_TAB = new CreativeTabs(Reference.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return Items.IRON_NUGGET;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public String getTranslatedTabLabel()
        {
            return Reference.MOD_ID;
        }
    };
}
