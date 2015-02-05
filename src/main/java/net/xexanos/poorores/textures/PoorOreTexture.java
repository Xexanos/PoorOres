package net.xexanos.poorores.textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.util.ResourceLocation;
import net.xexanos.poorores.PoorOre;
import net.xexanos.poorores.reference.Reference;
import net.xexanos.poorores.utility.LogHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class PoorOreTexture extends TextureAtlasSprite {
    private PoorOre poorOre;

    public PoorOreTexture(PoorOre poorOre) {
        super(Reference.MOD_ID.toLowerCase() + ":poor_" + poorOre.getName() + "_ore");
        this.poorOre = poorOre;
    }

    @Override
    public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {

        ResourceLocation location1 = new ResourceLocation(location.getResourceDomain(), String.format("%s/%s%s", "textures/blocks", location.getResourcePath(), ".png"));
        try {
            // check to see if the resource can be loaded (someone added an override)
            manager.getResource(location1);
            LogHelper.info("Detected override for poor " + poorOre.getName() + " ore");
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    @Override
    public boolean load(IResourceManager manager, ResourceLocation location) {
        int mp = Minecraft.getMinecraft().gameSettings.mipmapLevels + 1;

        BufferedImage[] ore_image = new BufferedImage[mp];

        BufferedImage underlying_image;
        BufferedImage alpha_image;
        int w;

        AnimationMetadataSection animation;

        try {
            int index = poorOre.getBaseBlockTexture().indexOf(":");
            IResource iResourceBase = manager.getResource(new ResourceLocation(poorOre.getBaseBlockTexture().substring(0, index), "textures/blocks/" + poorOre.getBaseBlockTexture().substring(index + 1) + ".png"));
            index = poorOre.getUnderlyingBlockName().indexOf(":");
            IResource iResourceUnderlying = manager.getResource(new ResourceLocation(poorOre.getUnderlyingBlockName().substring(0, index), "textures/blocks/" + poorOre.getUnderlyingBlockName().substring(index + 1) + ".png"));
            IResource iResourceAlpha = manager.getResource(new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/blocks/ore_poor_alpha_" + poorOre.getOreRenderType() + ".png"));

            // load the ore texture
            ore_image[0] = ImageIO.read(iResourceBase.getInputStream());

            // load animation
            animation = (AnimationMetadataSection) iResourceBase.getMetadata("animation");

            // load the underlying texture
            underlying_image = ImageIO.read(iResourceUnderlying.getInputStream());

            alpha_image = ImageIO.read(iResourceAlpha.getInputStream());

            w = ore_image[0].getWidth();

            if (underlying_image.getWidth() != w) {
                List resourcePacks = manager.getAllResources(new ResourceLocation(poorOre.getUnderlyingBlockName().substring(0, index), "textures/blocks/" + poorOre.getUnderlyingBlockName().substring(index + 1) + ".png"));
                for (int i = resourcePacks.size() - 1; i >= 0; --i) {
                    IResource resource = (IResource) resourcePacks.get(i);
                    underlying_image = ImageIO.read(resource.getInputStream());

                    if (underlying_image.getWidth() == w)
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }

        if (underlying_image.getWidth() != w) {
            LogHelper.error("Error generating texture for \"" + poorOre.getName() + "\". Unable to find underlying texture with same size.");
            return true;
        }

        int div = w / alpha_image.getWidth();

        int h = ore_image[0].getHeight();

        BufferedImage output_image = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);

        int[] ore_data = new int[w * w];
        int[] underlying_data = new int[w * w];
        int[] alpha_data = new int[(w / div) * (w / div)];

        underlying_image.getRGB(0, 0, w, w, underlying_data, 0, w);
        alpha_image.getRGB(0, 0, w / div, w / div, alpha_data, 0, w / div);

        for (int y = 0; y < h; y += w) {
            ore_image[0].getRGB(0, y, w, w, ore_data, 0, w);

            int[] new_data = new int[w * w];

            for (int ih = 0; ih < w; ih++) {
                for (int iw = 0; iw < w; iw++) {

                    float alpha = ((float) ((alpha_data[iw / div + (ih / div) * alpha_image.getWidth()]) & 0xffffff)) / 0xffffff;
                    new_data[iw + ih * w] = 0xff000000 + ((int) (ore_data[iw + ih * w] * alpha + underlying_data[iw + ih * w] * (1 - alpha)) & 0xffffff);
                }
            }

            // write the new image data to the output image buffer
            output_image.setRGB(0, y, w, w, new_data, 0, w);
        }

        // replace the old texture
        ore_image[0] = output_image;

/*
        try {
            ImageIO.write(output_image, "png", new File(poorOre.getName() + "_" + (poorOre.getOreRenderType()) + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        // load the texture
        this.loadSprite(ore_image, animation, (float) Minecraft.getMinecraft().gameSettings.anisotropicFiltering > 1.0F);

        LogHelper.info("Successfully generated texture for \"" + poorOre.getName() + "\" with background \"" + poorOre.getUnderlyingBlock().getLocalizedName() + "\". Place \"poor_" + poorOre.getName() + "_ore.png\" in the assets folder to override.");
        return false;
    }
}
