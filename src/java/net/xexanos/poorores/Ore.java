package net.xexanos.poorores;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.xexanos.poorores.creativetab.CreativeTabPoorOres;
import net.xexanos.poorores.reference.Reference;
import net.xexanos.poorores.utility.LogHelper;

import static java.lang.Math.min;

public class Ore extends Block {
    private String name;
    private Block baseBlock;
    private Block underlyingBlock;

    @Override
    public Block setBlockName(String unlocalizedName) {
        super.setBlockName(Reference.RESOURCE_PREFIX + unlocalizedName);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Block getBaseBlock() {
        return baseBlock;
    }

    public void setBaseBlock(Block baseBlock) {
        this.baseBlock = baseBlock;
    }

    public Block getUnderlyingBlock() {
        return underlyingBlock;
    }

    public void setUnderlyingBlock(Block underlyingBlock) {
        this.underlyingBlock = underlyingBlock;
    }

    public Ore(String name) {
        super(Material.rock);
        this.setBlockName(name);
        this.setCreativeTab(CreativeTabs.tabAllSearch);
        this.setCreativeTab(CreativeTabPoorOres.POOR_ORES_TAB);
    }

    public Ore(String name, Block baseBlock, Block underlyingBlock, int hardness) {
        super(Material.rock);
        this.setBlockName("poor_" + name + "_ore");
        this.setName(name);
        this.setBaseBlock(baseBlock);
        this.setUnderlyingBlock(underlyingBlock);
        this.setHardness(hardness);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setCreativeTab(CreativeTabs.tabAllSearch);
        this.setCreativeTab(CreativeTabPoorOres.POOR_ORES_TAB);
    }

    public void setHarvestLevel() {
        this.setHarvestLevel("pickaxe", min(getBaseHarvestLevel(), 0));
    }

    private int getBaseHarvestLevel() {
        int i = 0;
        while (i < 16 && getBaseBlock().getHarvestLevel(i) == -1) {
            i++;
        }
        return (i < 16) ? i : 2;
    }
}
