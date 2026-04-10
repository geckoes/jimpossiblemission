package jimpossiblemission.view.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import jimpossiblemission.model.entity.HitBox;

/**
 * Class SpriteAnimation 
 */
public class SpriteAnimation
{
    private BufferedImage image;
    private HitBox hitBox;

    /**
     * Constructor of Sprite animation.
     * Initializes image and hitBox
     * 
     * @param image
     * @param hitBox
     * @throws IOException
     */
    public SpriteAnimation(BufferedImage image, HitBox hitBox) throws IOException
    {
        if (image == null)
            throw new IOException("no image");
        this.image = image;
        this.hitBox = hitBox;
    }

    /**
     * Return the BufferedImage
     * 
     * @return bufferedImage
     */
    public BufferedImage getImage()
    {
        return image;
    }

    /**
     * Return the hitBox
     * 
     * @return hitBox
     */
    public HitBox getHitBox()
    {
        return hitBox;
    }

}
