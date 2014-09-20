package net.xexanos.poorores;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.xexanos.poorores.creativetab.CreativeTabPoorOres;
import net.xexanos.poorores.reference.Reference;

public class Nugget extends Item {
    private String name;
    private Block baseBlock;
    private Ore ore;
    private String oreDictName;

    public String getOreDictName() {
        return oreDictName;
    }

    public void setOreDictName(String oreDictName) {
        this.oreDictName = oreDictName;
    }

    public Ore getOre() {
        return ore;
    }

    public void setOre(Ore ore) {
        this.ore = ore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Item getIngot() {
        return FurnaceRecipes.smelting().getSmeltingResult(new ItemStack(baseBlock)).getItem();
    }

    public void setBaseBlock(Block baseBlock) {
        this.baseBlock = baseBlock;
    }

    public Nugget(String name, Block baseBlock, Ore ore) {
        super();
        this.setName(name + "_nugget");
        this.setUnlocalizedName(this.getName());
        this.setBaseBlock(baseBlock);
        this.setOre(ore);
        this.setOreDictName("nugget" + Character.toString(name.charAt(0)).toUpperCase() + name.substring(1));
        this.setCreativeTab(CreativeTabs.tabAllSearch);
        this.setCreativeTab(CreativeTabPoorOres.POOR_ORES_TAB);
        OreDictionary.registerOre(this.getOreDictName(), this);
    }

    public void registerRS() {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this.getIngot()), "nnn","nnn","nnn",'n', this.getOreDictName()));
//        GameRegistry.addRecipe(new ItemStack(this.getIngot()), "nnn","nnn","nnn",'n', new ItemStack(this));
        GameRegistry.addSmelting(this.getOre(), new ItemStack(this), 0.1f);
    }

    public void registerOreDict() {
        OreDictionary.registerOre(this.getOreDictName(), this);
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
