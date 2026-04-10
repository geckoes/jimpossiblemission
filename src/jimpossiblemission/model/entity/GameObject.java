package jimpossiblemission.model.entity;

/**
 * Abstarct GameObject class: this is the base for all objects in game
 * 
 * @author Filippo Taiuti
 *
 */
public abstract class GameObject extends Entity implements CanCollide
{
    protected boolean active;
    protected ShapeCollider collider;

    /**
     * Constructor gameObject with initial coordinate. The initial status of
     * GameObject is active when created.
     * 
     * @param x initial x coordinate
     * @param y initial y coordinale
     * 
     */
    public GameObject(int x, int y)
    {
        super(x, y);
        this.active = true;
    }

    /**
     * Returns the active state of the gameObject
     * 
     * @return boolean
     */
    public boolean isActive()
    {
        return active;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShapeCollider getCollider()
    {
        return collider;
    }

}
