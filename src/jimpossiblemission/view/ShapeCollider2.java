package jimpossiblemission.view;

/**
 * @author Filippo Taiuti
 *
 */
public abstract class ShapeCollider2
{
    public abstract boolean isColliding(ShapeCollider2 sc);
    public ShapeCollider2 getShapeCollider() { return this; }
}
