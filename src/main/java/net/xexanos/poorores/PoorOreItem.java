package net.xexanos.poorores;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class PoorOreItem extends ItemBlock {
    private PoorOre poorOre;

    public PoorOreItem(Block poorOre) {
        super(poorOre);

        setPoorOre((PoorOre) poorOre);
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack) {
        String prefix = ("" + StatCollector.translateToLocal("poorores.prefix")).trim();
        return prefix.replaceFirst("ORENAME", new ItemStack(getPoorOre().getBaseBlock()).getDisplayName());
    }


    public PoorOre getPoorOre() {
        return poorOre;
    }

    public void setPoorOre(PoorOre poorOre) {
        this.poorOre = poorOre;
    }
}
