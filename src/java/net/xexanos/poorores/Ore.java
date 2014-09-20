package net.xexanos.poorores;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.oredict.OreDictionary;
import net.xexanos.poorores.creativetab.CreativeTabPoorOres;
import net.xexanos.poorores.reference.Reference;

import static java.lang.Math.min;

public class Ore extends Block {
    private String name;
    private Block baseBlock;
    private Block underlyingBlock;
    private String oreDictName;

    public String getOreDictName() {
        return oreDictName;
    }

    public void setOreDictName(String oreDictName) {
        this.oreDictName = oreDictName;
    }

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

    public Ore(String name, Block baseBlock, Block underlyingBlock, int hardness) {
        super(Material.rock);
        this.setBlockName("poor_" + name + "_ore");
        this.setName(name);
        this.setBaseBlock(baseBlock);
        this.setUnderlyingBlock(underlyingBlock);
        this.setHardness(hardness);
        this.setOreDictName("poorOre" + Character.toString(name.charAt(0)).toUpperCase() + name.substring(1));
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setCreativeTab(CreativeTabs.tabAllSearch);
        this.setCreativeTab(CreativeTabPoorOres.POOR_ORES_TAB);
    }

    public void registerOreDict() {
        OreDictionary.registerOre(this.getOreDictName(), this);
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
