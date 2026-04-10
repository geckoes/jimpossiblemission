package jimpossiblemission.model.entity;

/**
 * Abstract class used to create ShapeCollider with different shape.
 * 
 * @author Filippo Taiuti
 *
 */
public abstract class ShapeCollider
{
    protected GameObject gameObject;
    protected HitBox hitBox;
    protected boolean active;

    /**
     * Constructor requires the owner of the ShapeCollider
     * 
     * @param gameObject GameObject owner of the ShapeCollider
     */
    public ShapeCollider(GameObject gameObject)
    {
        this.gameObject = gameObject;
        hitBox = new HitBox();
        enableCollider();
    }

    /**
     * Constructor creates a shapeCollider for the GameObject
     * 
     * @param x for offsetX
     * @param y for offsetX
     * @param w for width
     * @param h for height
     */
    public ShapeCollider(GameObject gameObject, int x, int y, int w, int h)
    {
        this(gameObject);
        hitBox.updateBox(x, y, w, h);
    }

    /**
     * Returns the shapeColliders
     * 
     * @return shapeCollider
     */
    public ShapeCollider getShapeCollider()
    {
        return this;
    }

    /**
     * Method to get the Right Bound of the ShapeCollider
     * 
     * @return int
     */
    public int getRightBound()
    {
        return gameObject.getX() + hitBox.getX() + hitBox.getWidth();
    }

    /**
     * Method to get the Left Bound of the ShapeCollider
     * 
     * @return int
     */
    public int getLeftBound()
    {
        return gameObject.getX() + hitBox.getX();

    }

    /**
     * Method to get the Top Bound of the ShapeCollider
     * 
     * @return int
     */
    public int getTopBound()
    {
        return gameObject.getY() + hitBox.getY();

    }

    /**
     * Method to get the Bottom Bound of the ShapeCollider
     * 
     * @return int
     */
    public int getBottomBound()
    {
        return gameObject.getY() + hitBox.getY() + hitBox.getHeight();

    }

    /**
     * Method to return the HitBox Container
     */
    public HitBox getHitBox()
    {
        return hitBox;
    }

    /**
     * Method to disable the Collider
     */
    public void disableCollider()
    {
        active = false;
    }

    /**
     * Method to enable the collider
     */
    private void enableCollider()
    {
        active = true;
    }

    /**
     * Method to update the bounds of shapeCollider
     * 
     * @param x for offsetX
     * @param y for offsetX
     * @param w for width
     * @param h for height
     */
    public void updateCollider(int x, int y, int w, int h)
    {
        hitBox.updateBox(x, y, w, h);
    }

    /**
     * Method to update the bounds of shapeCollider
     * 
     * @param otherHitBox has the values to update
     * 
     */
    public void updateCollider(HitBox otherHitBox)
    {
        hitBox.updateBox(otherHitBox);
    }

    /**
     * Method to check the collisions between colliders. It will check with a
     * possible tollerance due to a not uniform shape
     * 
     * @param otherCollider
     * @return boolean true if they collide
     */
    public abstract boolean intersects(ShapeCollider otherCollider);

    public GameObject getGameObject()
    {
        return gameObject;
    }
}
