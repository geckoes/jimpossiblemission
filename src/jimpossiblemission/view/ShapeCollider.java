package jimpossiblemission.view;

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
    public boolean isColliding(ShapeCollider otherObj)
    {
        Area thisArea = getArea();
        Area otherArea = otherObj.getArea();
        thisArea.intersect(otherArea);
        return !thisArea.isEmpty();
    }

    /**
     * Calculate the Area of the shape, To Override and personalize in the extended
     * class
     * 
     * @return Area of the shape
     */
    public abstract Area getArea();

    /**
     * Return the overrode shapeCollider
     * 
     * @return shapeCollider
     */
    public ShapeCollider getShapeCollider()
    {
        return this;
    }
}
