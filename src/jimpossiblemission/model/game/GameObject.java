
package jimpossiblemission.model.game;

/**
 * Abstarct GameObject class: this is the base for all objects in game
 * 
 * @author Filippo Taiuti
 *
 */
public abstract class GameObject
{
    protected double x, y;
    protected boolean active;
    protected boolean isMoving;
    protected boolean onGround;
    protected double gravity;

    /**
     * Constructor gameObject with initial coordinate passed like parameters
     * and without gravity (gameobject won't fall).
     * Gravity can be set when necessary
     * 
     * @param x initial x coordinate
     * @param y initial y coordinale
     * 
     */
    public GameObject(double x, double y)
    {
        this(x, y, 0d);
    }

    /**
     * Constructor gameObject with initial coordinate passed like parameters
     * and with gravity (gravity have to be a positive value)
     * 
     * @param x initial x coordinate
     * @param y initial y coordinale
     * #param gravity set gravity for the object
     */
    public GameObject(double x, double y, double gravity)
    {
        this.x = x;
        this.y = y;
        this.gravity = Math.max(0, gravity); // only positive gravity
        this.isMoving = false;
        this.active = true;
        this.onGround = false;
    }

    // Getters and setters
    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public boolean isActive()
    {
        return active;
    }

    public boolean isMoving()
    {
        return isMoving;
    }

    public void setGravity(double gravity)
    {
        this.gravity += gravity;
    }
}
