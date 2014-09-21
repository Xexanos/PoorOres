package net.xexanos.poorores;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.oredict.OreDictionary;
import net.xexanos.poorores.creativetab.CreativeTabPoorOres;
import net.xexanos.poorores.reference.Reference;
import net.xexanos.poorores.textures.PoorOreTexture;
import net.xexanos.poorores.utility.LogHelper;

import java.io.FileNotFoundException;

import static java.lang.Math.min;

public class PoorOre extends Block {
    private String name;
    private Block baseBlock;
    private String baseBlockTexture;
    private Block underlyingBlock;
    private String underlyingBlockName;
    private String oreDictName;
    private int oreRenderType;

    public PoorOre(String name, Block baseBlock, String baseBlockTexture, Block underlyingBlock, String underlyingBlockName, int hardness, int oreRenderType) {
        super(Material.rock);
        setBlockName("poor_" + name + "_ore");
        setName(name);
        setBaseBlock(baseBlock);
        setBaseBlockTexture(baseBlockTexture);
        setUnderlyingBlock(underlyingBlock);
        setUnderlyingBlockName(underlyingBlockName);
        setHardness(hardness);
        setOreRenderType(oreRenderType);
        setOreDictName("poorOre" + Character.toString(name.charAt(0)).toUpperCase() + name.substring(1));
        setCreativeTab(CreativeTabs.tabBlock);
        setCreativeTab(CreativeTabs.tabAllSearch);
        setCreativeTab(CreativeTabPoorOres.POOR_ORES_TAB);
    }

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

    public String getBaseBlockTexture() {
        return baseBlockTexture;
    }

    public void setBaseBlockTexture(String baseBlockTexture) {
        this.baseBlockTexture = baseBlockTexture;
    }

    public String getUnderlyingBlockName() {
        return underlyingBlockName;
    }

    public void setUnderlyingBlockName(String underlyingBlockName) {
        this.underlyingBlockName = underlyingBlockName;
    }

    public Block getUnderlyingBlock() {
        return underlyingBlock;
    }

    public void setUnderlyingBlock(Block underlyingBlock) {
        this.underlyingBlock = underlyingBlock;
    }

    public int getOreRenderType() {
        return oreRenderType;
    }

    public void setOreRenderType(int oreRenderType) {
        this.oreRenderType = oreRenderType;
    }

    public void registerOreDict() {
        OreDictionary.registerOre(getOreDictName(), this);
    }

    public void setHarvestLevel() {
        setHarvestLevel("pickaxe", min(getBaseHarvestLevel(), 0));
    }

    private int getBaseHarvestLevel() {
        int i = 0;
        while (i < 16 && getBaseBlock().getHarvestLevel(i) == -1) {
            i++;
        }
        return (i < 16) ? i : 2;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
//        blockIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(getUnlocalizedName().indexOf(".") + 1));

        if (iconRegister instanceof TextureMap) {
            TextureMap map = (TextureMap) iconRegister;

            String name = Reference.MOD_ID.toLowerCase() + ":poor_" + getName() + "_ore";

            //load texture from file or generate from baseBlock
            TextureAtlasSprite texture = map.getTextureExtry(name);
            if (texture == null) {
                texture = new PoorOreTexture(this);
                if (!map.setTextureEntry(name, texture)) {
                    LogHelper.error("Could not add texture for " + name + " after creation");
                }
            }

            blockIcon = map.getTextureExtry(name);
        }
    }
}
