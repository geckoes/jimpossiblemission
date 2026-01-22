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

public class SpriteAnimation
{
    private BufferedImage image;
    private int width, height;

    public void setImage(BufferedImage image, int width, int height)
    {
        this.image = image;
        this.width = width;
        this.height = height;
    }

    /**
     * @return
     */
    public Image getImage()
    {
        return image;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

}
