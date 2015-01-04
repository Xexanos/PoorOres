package net.xexanos.poorores;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.xexanos.poorores.creativetab.CreativeTabPoorOres;
import net.xexanos.poorores.reference.Reference;
import net.xexanos.poorores.textures.PoorOreTexture;
import net.xexanos.poorores.utility.LogHelper;

import java.util.ArrayList;
import java.util.List;

public class PoorOre extends Block {
    private String name;
    private Nugget nugget;
    private int veinRate;
    private int veinSize;
    private int veinHeight;
    private List<Integer> dimWhiteList;
    private List<Integer> dimBlackList;
    private Block baseBlock;
    private String modID;
    private String baseBlockName;
    private int meta;
    private String baseBlockTexture;
    private Block underlyingBlock;
    private String underlyingBlockName;
    private String oreDictName;
    private int oreRenderType;

    public PoorOre(String name, String modID, String baseBlockName, int meta, String baseBlockTexture, Block underlyingBlock, String underlyingBlockName, int oreRenderType, int veinRate, int veinSize, int veinHeight, List<Integer> dimWhiteList, List<Integer> dimBlackList) {
        super(Material.rock);
        setBlockName("poor_" + name + "_ore");
        setName(name);
        setVeinRate(veinRate);
        setVeinSize(veinSize);
        setVeinHeight(veinHeight);
        setDimWhiteList(dimWhiteList);
        setDimBlackList(dimBlackList);
        setModID(modID);
        setBaseBlockName(baseBlockName);
        setMeta(meta);
        setBaseBlockTexture(baseBlockTexture);
        setUnderlyingBlock(underlyingBlock);
        setUnderlyingBlockName(underlyingBlockName);
        setOreRenderType(oreRenderType);
        setOreDictName("orePoorAll");
        setOreDictName("orePoor" + Character.toString(name.charAt(0)).toUpperCase() + name.substring(1));
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

    public Nugget getNugget() {
        return nugget;
    }

    public void setNugget(Nugget nugget) {
        this.nugget = nugget;
    }

    public int getVeinRate() {
        return veinRate;
    }

    public void setVeinRate(int veinRate) {
        this.veinRate = veinRate;
    }

    public int getVeinSize() {
        return veinSize;
    }

    public void setVeinSize(int veinSize) {
        this.veinSize = veinSize;
    }

    public int getVeinHeight() {
        return veinHeight;
    }

    public void setVeinHeight(int veinHeight) {
        this.veinHeight = veinHeight;
    }

    public List<Integer> getDimWhiteList() {
        return dimWhiteList;
    }

    public void setDimWhiteList(List<Integer> dimWhiteList) {
        this.dimWhiteList = dimWhiteList;
    }

    public List<Integer> getDimBlackList() {
        return dimBlackList;
    }

    public void setDimBlackList(List<Integer> dimBlackList) {
        this.dimBlackList = dimBlackList;
    }

    public Block getBaseBlock() {
        if (baseBlock == null) {
            if (setBaseBlock(Block.getBlockFromName(getModID() + ":" + getBaseBlockName())) == null) {
                LogHelper.error(getName() + ": Could not get baseBlock.");
            }
        }
        return baseBlock;
    }

    public Block setBaseBlock(Block baseBlock) {
        this.baseBlock = baseBlock;
        return baseBlock;
    }

    public String getModID() {
        return modID;
    }

    public void setModID(String modID) {
        this.modID = modID;
    }

    public String getBaseBlockName() {
        return baseBlockName;
    }

    public void setBaseBlockName(String baseBlockName) {
        this.baseBlockName = baseBlockName;
    }

    public int getMeta() {
        return meta;
    }

    public void setMeta(int meta) {
        this.meta = meta;
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

    @Override
    public float getBlockHardness(World p_149712_1_, int p_149712_2_, int p_149712_3_, int p_149712_4_) {
        return getBaseBlock().getBlockHardness(p_149712_1_, p_149712_2_, p_149712_3_, p_149712_4_);
    }

    public void registerOreDict() {
        OreDictionary.registerOre(getOreDictName(), this);
    }

    @Override
    public int getHarvestLevel(int metadata)
    {
        return getBaseBlock().getHarvestLevel(metadata);
    }

    @Override
    public String getHarvestTool(int metadata)
    {
        return getBaseBlock().getHarvestTool(metadata);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        if (iconRegister instanceof TextureMap) {
            TextureMap map = (TextureMap) iconRegister;

            String name = Reference.MOD_ID.toLowerCase() + ":poor_" + getName() + "_ore";

            //load texture from file or generate from baseBlock
            TextureAtlasSprite texture = map.getTextureExtry(name);
            if (texture == null) {
                texture = new PoorOreTexture(this);
                if (!map.setTextureEntry(name, texture)) {
                    LogHelper.error(getName() + ": Could not add texture after creation!");
                }
            }

            blockIcon = map.getTextureExtry(name);
        }
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        for(ItemStack drops : baseBlock.getDrops(world, x, y, z, metadata, fortune)) {
            if (drops.getItem() == Item.getItemFromBlock(baseBlock)) {
                ret.add(new ItemStack(this, drops.stackSize));
            } else {
                ret.add(new ItemStack(this.getNugget(), drops.stackSize));
            }
        }

        return ret;
    }
}
