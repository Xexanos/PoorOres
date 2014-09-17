package net.xexanos.poorores;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.xexanos.poorores.creativetab.CreativeTabPoorOres;
import net.xexanos.poorores.reference.Reference;

public class Nugget extends Item {
    private Item ingot;

    public Nugget(String unlocalizedName) {
        super();
        this.setUnlocalizedName(unlocalizedName + "_nugget");
        this.setCreativeTab(CreativeTabs.tabAllSearch);
        this.setCreativeTab(CreativeTabPoorOres.POOR_ORES_TAB);
    }

    @Override
    public Item setUnlocalizedName(String unlocalizedName) {
        super.setUnlocalizedName(Reference.RESOURCE_PREFIX + unlocalizedName);
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }
}
