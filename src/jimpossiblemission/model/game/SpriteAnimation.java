/**
 * 
 */
package jimpossiblemission.model.game;

import java.awt.Image;
import java.awt.image.BufferedImage;

import jimpossiblemission.view.ShapeCollider;

/**
 * @author Filippo Taiuti
 *
 */

public class SpriteAnimation
{
    private BufferedImage image;
    private ShapeCollider sc;
    
    public void setImage(BufferedImage image)
    {
        this.image = image;
    }

    /**
     * @return
     */
    public Image getImage()
    {
        return image;
    }
	public void setShapeCollider(ShapeCollider sc2) {
		this.sc = sc2;
	}
	public ShapeCollider getShapeCollider() {
		return sc;
	}
    
}
