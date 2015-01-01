package net.xexanos.poorores;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.xexanos.poorores.creativetab.CreativeTabPoorOres;
import net.xexanos.poorores.reference.Reference;
import net.xexanos.poorores.textures.NuggetTexture;
import net.xexanos.poorores.utility.LogHelper;

public class Nugget extends Item {
    private String name;
    private PoorOre poorOre;
    private String oreDictName;
    private int burnTime;
    private int meta;
    private int nuggetRenderType;

    public Nugget(String name, PoorOre poorOre, int burnTime, int meta, int nuggetRenderType) {
        super();
        setName(name + "_nugget");
        setUnlocalizedName(this.getName());
        setPoorOre(poorOre);
        setBurnTime(burnTime);
        setMeta(meta);
        setOreDictName("nugget" + Character.toString(name.charAt(0)).toUpperCase() + name.substring(1));
        setNuggetRenderType(nuggetRenderType);
        setCreativeTab(CreativeTabs.tabAllSearch);
        setCreativeTab(CreativeTabPoorOres.POOR_ORES_TAB);
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

    public String getOreDictName() {
        return oreDictName;
    }

    public void setOreDictName(String oreDictName) {
        this.oreDictName = oreDictName;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public void setBurnTime(int burnTime) {
        this.burnTime = burnTime;
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

    public void registerRecipes() {
        if (Reference.CONFIG_ADD_CRAFTING) {
            GameRegistry.addRecipe(new ShapedOreRecipe(this.getIngot(), "nnn","nnn","nnn",'n', this.getOreDictName()));
            GameRegistry.addShapelessRecipe(new ItemStack(this, 9), this.getIngot());
        }
        if (Reference.CONFIG_ADD_SMELTING) {
            GameRegistry.addSmelting(this.getPoorOre(), new ItemStack(this), 0.1f);
        }
    }

    public void registerOreDict() {
        OreDictionary.registerOre(getOreDictName(), this);
        OreDictionary.registerOre("nuggetAll", this);
    }

    @Override
    public String getItemStackDisplayName(ItemStack nugget) {
        String prefix = ("" + StatCollector.translateToLocal("poorores.nuggets.prefix")).trim();
        String material = ("" + StatCollector.translateToLocal("poorores.material." + getPoorOre().getName())).trim();
        if (material.length() == 0) {
            return prefix.replaceFirst("NUGGETNAME", new ItemStack(getPoorOre().getBaseBlock()).getDisplayName());
        } else {
            return prefix.replaceFirst("NUGGETNAME", material);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
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
