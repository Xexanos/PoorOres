package net.xexanos.poorores.worldgen;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.xexanos.poorores.PoorOre;
import net.xexanos.poorores.reference.Reference;
import net.xexanos.poorores.utility.LogHelper;

import java.util.Random;

public class Generator implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        for (PoorOre poorOre : Reference.ORES_LIST) {
            if (poorOre.getVeinRate() != 0 && poorOre.getVeinHeight() != 0 && poorOre.getVeinSize() != 0 && (poorOre.getDimWhiteList().contains(world.provider.dimensionId) || (poorOre.getDimWhiteList().isEmpty() && !poorOre.getDimBlackList().contains(world.provider.dimensionId)))) {
                for (int i = 0; i <= poorOre.getVeinRate(); i++) {
                    new WorldGenMinable(poorOre, 0, poorOre.getVeinSize(), poorOre.getUnderlyingBlock()).generate(world, random, chunkX * 16  + random.nextInt(16), random.nextInt(poorOre.getVeinHeight()), chunkZ * 16  + random.nextInt(16));
                }
            }
        }
    }
}
