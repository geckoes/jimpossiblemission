
package jimpossiblemission.model.game;

/**
 * Abstarct GameObject class: this is the base for all objects in game
 * 
 * @author Filippo Taiuti
 *
 */
public abstract class GameObject
{
    protected int x, y;
    protected boolean active;
    protected boolean isMoving;
    protected boolean onGround;
    protected int gravity;

    /**
     * Constructor gameObject with initial coordinate passed like parameters and
     * without gravity (gameobject won't fall). Gravity can be set when necessary
     * 
     * @param x initial x coordinate
     * @param y initial y coordinale
     * 
     */
    public GameObject(int x, int y)
    {
        this(x, y, 0);
    }

    /**
     * Constructor gameObject with initial coordinate passed like parameters and
     * with gravity (gravity have to be a positive value)
     * 
     * @param x initial x coordinate
     * @param y initial y coordinale #param gravity set gravity for the object
     */
    public GameObject(int x, int y, int gravity)
    {
        this.x = x;
        this.y = y;
        this.gravity = Math.max(0, gravity); // only positive gravity
        this.isMoving = false;
        this.active = true;
        this.onGround = false;
    }

    // Getters and setters
    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
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

    public void setGravity(int gravity)
    {
        this.gravity += gravity;
    }

    public boolean getOnGround()
    {
        return onGround;
    }

    public void setOnGround(boolean grounded)
    {
        onGround = grounded;
    }
}
