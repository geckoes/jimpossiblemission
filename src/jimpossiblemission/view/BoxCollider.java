package jimpossiblemission.view;

import java.awt.Rectangle;
import java.awt.geom.Area;

/**
 * BoxCollider creates a Rectangle that can be used to interact to other
 * Colliders.
 * 
 * @author Filippo Taiuti
 *
 */
public class BoxCollider extends ShapeCollider
{
    protected int x, y, w, h;

    /**
     * Constructor creates a rectangle that can be used to bound an object
     * 
     * @param x top x
     * @param y top y
     * @param w width
     * @param h height
     */
    public BoxCollider(int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    /**
     * Calculate the area of the boxCollider
     * 
     * @return Area
     */
    @Override
    public Area getArea()
    {
        Rectangle r = new Rectangle(x, y, w, h);
        return new Area(r);
    }
}
