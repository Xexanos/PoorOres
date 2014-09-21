package net.xexanos.poorores;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.xexanos.poorores.creativetab.CreativeTabPoorOres;
import net.xexanos.poorores.reference.Reference;
import net.xexanos.poorores.textures.NuggetTexture;
import net.xexanos.poorores.textures.PoorOreTexture;
import net.xexanos.poorores.utility.LogHelper;

public class Nugget extends Item {
    private String name;
    private PoorOre poorOre;
    private String baseMod;
    private String oreDictName;
    private int meta;
    private int nuggetRenderType;

    public Nugget(String name, PoorOre poorOre, String baseMod, int meta, int nuggetRenderType) {
        super();
        setName(name + "_nugget");
        setUnlocalizedName(this.getName());
        setPoorOre(poorOre);
        setBaseMod(baseMod);
        setMeta(meta);
        setNuggetRenderType(nuggetRenderType);
        setOreDictName("nugget" + Character.toString(name.charAt(0)).toUpperCase() + name.substring(1));
        setCreativeTab(CreativeTabs.tabAllSearch);
        setCreativeTab(CreativeTabPoorOres.POOR_ORES_TAB);
        OreDictionary.registerOre(this.getOreDictName(), this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PoorOre getPoorOre() {
        return poorOre;
    }

    public void setPoorOre(PoorOre poorOre) {
        this.poorOre = poorOre;
    }

    public String getBaseMod() {
        return baseMod;
    }

    public void setBaseMod(String baseMod) {
        this.baseMod = baseMod;
    }

    public String getOreDictName() {
        return oreDictName;
    }

    public void setOreDictName(String oreDictName) {
        this.oreDictName = oreDictName;
    }

    public int getMeta() {
        return meta;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    public int getNuggetRenderType() {
        return nuggetRenderType;
    }

    public void setNuggetRenderType(int nuggetRenderType) {
        this.nuggetRenderType = nuggetRenderType;
    }

    public ItemStack getIngot() {
        return FurnaceRecipes.smelting().getSmeltingResult(new ItemStack(getPoorOre().getBaseBlock(), 1, getMeta()));
    }

    public void registerRS() {
        GameRegistry.addRecipe(new ShapedOreRecipe(this.getIngot(), "nnn","nnn","nnn",'n', this.getOreDictName()));
        GameRegistry.addSmelting(this.getPoorOre(), new ItemStack(this), 0.1f);
    }

    public void registerOreDict() {
        OreDictionary.registerOre(getOreDictName(), this);
    }

    @Override
    public Item setUnlocalizedName(String unlocalizedName) {
        super.setUnlocalizedName(Reference.RESOURCE_PREFIX + unlocalizedName);
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
//        itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(getUnlocalizedName().indexOf(".") + 1));

        if (iconRegister instanceof TextureMap) {
            TextureMap map = (TextureMap) iconRegister;

            String name = Reference.MOD_ID.toLowerCase() + ":" + getName();

            //load texture from file or generate from baseBlock
            TextureAtlasSprite texture = map.getTextureExtry(name);
            if (texture == null) {
                texture = new NuggetTexture(this);
                if (!map.setTextureEntry(name, texture)) {
                    LogHelper.error("Could not add texture for " + name + " after creation");
                }
            }

            itemIcon = map.getTextureExtry(name);
        }
    }
}
