package net.xexanos.poorores.textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.xexanos.poorores.Nugget;
import net.xexanos.poorores.reference.Reference;
import net.xexanos.poorores.utility.LogHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class NuggetTexture extends TextureAtlasSprite {
    private Nugget nugget;

    public NuggetTexture(Nugget nugget) {
        super(Reference.MOD_ID.toLowerCase() + ":" + nugget.getName());
        setNugget(nugget);
    }

    public Nugget getNugget() {
        return nugget;
    }

    public void setNugget(Nugget nugget) {
        this.nugget = nugget;
    }

    @Override
    public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {

        ResourceLocation location1 = new ResourceLocation(location.getResourceDomain(), String.format("%s/%s%s", "textures/items", location.getResourcePath(), ".png"));
        try {
            // check to see if the resource can be loaded (someone added an override)
            manager.getResource(location1);
            LogHelper.info("Detected override for " + nugget.getName() + " nugget");
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    @Override
    public boolean load(IResourceManager manager, ResourceLocation location) {
        int mp = Minecraft.getMinecraft().gameSettings.mipmapLevels + 1;

        BufferedImage[] ingot_image = new BufferedImage[mp];

        BufferedImage nugget_image;
        int wi;

        AnimationMetadataSection animation;

        try {
            ItemStack ingot = getNugget().getIngot();
            if (ingot == null) {
                LogHelper.error("Could not get corresponding ingot for " + getNugget().getName());
                return true;
            } else {
                String ingotName = ingot.getItem().getIcon(ingot, 0).getIconName();
                int i = ingotName.indexOf(":");
                String ingotMod = "minecraft";
                if (i != -1) {
                    ingotMod = ingotName.substring(0, i);
                }
                ingotName = ingotName.substring(i + 1);
                IResource iResourceIngot = manager.getResource(new ResourceLocation(ingotMod.toLowerCase(), "textures/items/" + ingotName + ".png"));
                IResource iResourceNugget;
                if (getNugget().getDust()) {
                    iResourceNugget = manager.getResource(new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/items/dust_" + getNugget().getNuggetRenderType() + ".png"));
                } else {
                    iResourceNugget = manager.getResource(new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/items/nugget_" + getNugget().getNuggetRenderType() + ".png"));
                }

                // load the ore texture
                ingot_image[0] = ImageIO.read(iResourceIngot.getInputStream());

                // load animation
                animation = (AnimationMetadataSection) iResourceIngot.getMetadata("animation");

                nugget_image = ImageIO.read(iResourceNugget.getInputStream());

                wi = ingot_image[0].getWidth();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }

        int h = ingot_image[0].getHeight();
        int wn = nugget_image.getWidth();

        h = (h * wn) / wi;

        BufferedImage output_image = new BufferedImage(wn, h, BufferedImage.TYPE_4BYTE_ABGR);

        int[] ingot_data = new int[wi * wi];
        int[] nugget_data = new int[wn * wn];

        nugget_image.getRGB(0, 0, wn, wn, nugget_data, 0, wn);

        for (int y = 0; y < h; y += wn) {
            ingot_image[0].getRGB(0, y / wn * wi, wi, wi, ingot_data, 0, wi);

            int sumR = 0, sumG = 0, sumB = 0;
            int div = 0;
            for (int i = 0; i < ingot_data.length; i++) {
                int col = ingot_data[i];
                if (getAlpha(col) != 0x00) {
                    sumR += getRed(col);
                    sumG += getGreen(col);
                    sumB += getBlue(col);
                    div++;
                }
            }
            int avgCol = makeCol(sumR/div, sumG/div, sumB/div);

            int[] new_data = nugget_data.clone();

            for (int i = 0; i < new_data.length; i++) {
                int col = new_data[i];
                if (getAlpha(col) != 0x00) {
                    int mult = getRed(col);

//                    new_data[i] = makeCol((getRed(avgCol) + mult) / 2, (getGreen(avgCol) + mult) / 2, (getBlue(avgCol) + mult) / 2);
                    new_data[i] = makeCol((getRed(avgCol) * mult) / 0xff, (getGreen(avgCol) * mult) / 0xff, (getBlue(avgCol) * mult) / 0xff);
                }
            }

            // write the new image data to the output image buffer
            output_image.setRGB(0, y, wn, wn, new_data, 0, wn);
        }

        // replace the old texture
        ingot_image[0] = output_image;

/*
        try {
            ImageIO.write(output_image, "png", new File(getNugget().getName() + "_" + (getNugget().getNuggetRenderType() + 2) + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        // load the texture
        this.loadSprite(ingot_image, animation, (float) Minecraft.getMinecraft().gameSettings.anisotropicFiltering > 1.0F);

        LogHelper.info("Successfully generated texture for \"" + nugget.getName() + "\". Place \"" + nugget.getName() + ".png\" in the assets folder to override.");
        return false;
    }

    public static int getAlpha(int col) {
        return (col & 0xff000000) >> 24;
    }

    public static int getRed(int col) {
        return (col & 0x00ff0000) >> 16;
    }

    public static int getGreen(int col) {
        return (col & 0x0000ff00) >> 8;
    }

    public static int getBlue(int col) {
        return col & 0x000000ff;
    }

    public static int makeCol(int red, int green, int blue) {
        return makeCol(red, green, blue, 0xff);
    }

    public static int makeCol(int red, int green, int blue, int alpha) {
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }
}