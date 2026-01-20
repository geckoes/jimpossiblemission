/**
 * 
 */
package jimpossiblemission.model.game;

import jimpossiblemission.view.DecoratorObject;

/**
 * @author Filippo Taiuti
 *
 */
public abstract class GameObject
{
    protected double x, y;
    protected boolean active;
    protected boolean isMoving = false;
    protected boolean onGround;
    protected double gravity;

    public GameObject(double x, double y)
    {
        this(x, y, 2d);
    }

    public GameObject(double x, double y, double gravity)
    {
        this.x = x;
        this.y = y;
        this.gravity = gravity;
        this.active = true;
        this.onGround = false;
    }

    public boolean collision(DecoratorObject otherObj)
    {
        return x < otherObj.x + otherObj.width && x + width > otherObj.x && y < otherObj.y + otherObj.height
                && y + height > otherObj.y;
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

    public double getWidth()
    {
        return width;
    }

    public double getHeight()
    {
        return height;
    }

    public boolean isActive()
    {
        return active;
    }

    public boolean isMoving()
    {
        return isMoving;
    }

    public void gravityY()
    {
        this.y += gravity;
    }
}
