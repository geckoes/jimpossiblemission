package jimpossiblemission.view;

/**
 * @author Filippo Taiuti
 *
 */
public abstract class ShapeCollider
{
    public abstract boolean isColliding(ShapeCollider sc);
    public ShapeCollider getShapeCollider() { return this; }
}
