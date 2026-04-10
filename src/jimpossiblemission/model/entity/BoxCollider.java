package jimpossiblemission.model.entity;

/**
 * BoxCollider is the default collider and it is a rectangle with the same
 * dimension of the parent collider
 * 
 * @author Filippo Taiuti
 *
 */
public class BoxCollider extends ShapeCollider
{
    /**
     * Constructor creates a boxCollider for the GameObject with an empty Box
     * 
     * @param gameObject owner of the collider
     * 
     */
    public BoxCollider(GameObject gameObject)
    {
        super(gameObject);
    }

    /**
     * Constructor creates a boxCollider for the GameObject
     * 
     * @param gameObject
     * @param x for offsetX
     * @param y for offsetX
     * @param w for width
     * @param h for height
     */
    public BoxCollider(GameObject gameObject, int x, int y, int w, int h)
    {
        super(gameObject, x, y, w, h);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean intersects(ShapeCollider otherCollider)
    {
        return getTopBound() <= otherCollider.getBottomBound() && getBottomBound() >= otherCollider.getTopBound()
                && getLeftBound() <= otherCollider.getRightBound() && getRightBound() >= otherCollider.getLeftBound();
    }

}
