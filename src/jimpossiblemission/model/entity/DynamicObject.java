/**
 * DynamicObject class extends GameObject
 */
package jimpossiblemission.model.entity;

import java.util.Observable;
import java.util.Observer;

/**
 * DynamicObjects are all kind of GameObject that can move
 * 
 * @author Filippo Taiuti
 *
 */
@SuppressWarnings("deprecation")
public abstract class DynamicObject extends GameObject implements Observer
{
    protected int speed;
    protected Direction currentDirection;
    protected Direction lastDirection;

    protected int initialX;
    protected int initialY;
    protected boolean onGround;

    /**
     * Constructor
     * 
     * @param x initial x
     * @param y initial y
     * @param speed of the dynamicObject
     */
    public DynamicObject(int x, int y, int speed)
    {
        super(x, y);
        initialX = x;
        initialY = y;
        this.speed = speed;
        currentDirection = Direction.NONE;
    }

    /**
     * Move the dynamicObject
     */
    public abstract void move();

    /**
     * Get the current state
     * 
     * @return string
     */
    public abstract String getState();

    /**
     * Returns the current direction of the dynamicObject
     * @return the direction
     */
    public Direction getDirection()
    {
        return currentDirection;
    }

    /**
     * DynamicObject goes up
     */
    public void goUp()
    {
        lastDirection = currentDirection;
        currentDirection = Direction.UP;
    }

    /**
     * DynamicObject goes down
     */
    public void goDown()
    {
        lastDirection = currentDirection;
        currentDirection = Direction.DOWN;
    }

    /**
     * DynamicObject goes left
     */
    public void goLeft()
    {
        lastDirection = currentDirection;
        currentDirection = Direction.LEFT;
    }

    /**
     * DynamicObject goes right
     */
    public void goRight()
    {
        lastDirection = currentDirection;
        currentDirection = Direction.RIGHT;
    }

    /**
     * DynamicObject stops
     */
    public void stop()
    {
        currentDirection = Direction.NONE;
    }

    /**
     * Get the speed of the dynamicObject
     * @return the speed
     */
    public int getSpeed()
    {
        return speed;
    }

    /**
     * Tells the dynamicObject it is not on the ground
     */
    public void notOnGround()
    {
        onGround = false;
    }

    /**
     * Tells the dynamicObject it is on the ground
     *
     * @param gameObject
     */
    public void grounded(GameObject gameObject)
    {
        onGround = true;
    }

    /**
     * Tells the dynamicObject that has touched an obstacle
     * 
     * @param gameObject
     */
    public void touchedSolidObstacle(GameObject gameObject)
    {
        if (lastDirection == Direction.RIGHT)
            x = gameObject.getCollider().getLeftBound() - getCollider().getHitBox().getX()
                    - getCollider().getHitBox().getWidth() - 1;
        else if (lastDirection == Direction.LEFT)
            x = gameObject.getCollider().getRightBound() - getCollider().getHitBox().getX() + 1;
    }

    /**
     * method called when an observed object notifies a change of state
     */
    @Override
    public void update(Observable o, Object arg)
    {
        move();
    }

    public void resetAllStatus()
    {
        x = initialX;
        y = initialY;
    }

}
