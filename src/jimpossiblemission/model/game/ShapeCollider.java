package jimpossiblemission.model.game;

import java.awt.geom.Area;

/**
 * Abstract class used to create ShapeCollider with different shape. It uses
 * Areas to check whether two areas collide.
 * 
 * @author Filippo Taiuti
 *
 */
public abstract class ShapeCollider
{
    /**
     * Check if the shapeCollider collides with another shapeCollider
     * 
     * @param otherObj SharpeCollider of other object
     * @return
     */
    public abstract boolean isColliding(ShapeCollider otherObj);
}
