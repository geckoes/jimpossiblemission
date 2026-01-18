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
    private int x, y, w, h;
    
    public void setImage(BufferedImage image)
    {
        this.image = image;
    }
    public int getX()
    {
        return x;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public int getY()
    {
        return y;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public int getW()
    {
        return w;
    }
    public void setW(int w)
    {
        this.w = w;
    }
    public int getH()
    {
        return h;
    }
    public void setH(int h)
    {
        this.h = h;
    }
    /**
     * @return
     */
    public Image getImage()
    {
        return image;
    }
    
}
