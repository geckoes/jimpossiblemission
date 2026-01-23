/**
 * 
 */
package jimpossiblemission.model.game;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * @author Filippo Taiuti
 *
 */

public class Sprite
{
    private BufferedImage image;

    /**
     * Constructors of a single sprite
     * @param image
     */
    public Sprite(BufferedImage image)
    {
        this.image = image;
    }

    /**
     * Constructors of a single sprite
     * @param image
     */
    public Sprite(BufferedImage image, int width, int height)
    {
        this.image = image;
    }

    /**
     * Return the BufferedImage
     * @return bufferedImage
     */
    public BufferedImage getImage()
    {
        return image;
    }

    /**
     * Return the width of the image
     * @return int
     */
    public int getWidth()
    {
        return image.getWidth();
    }

    /**
     * Return the height of the image
     * @return int
     */
    public int getHeight()
    {
        return image.getHeight();
    }
}
