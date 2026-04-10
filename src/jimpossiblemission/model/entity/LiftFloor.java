package jimpossiblemission.model.entity;

/**
 * LiftFloor is a platform that can move.
 * Usually it is part of a Lift.
 * 
 * 
 * @author Filippo Taiuti
 *
 */
public class LiftFloor extends DynamicObject
{
    private int nextY;

    /**
     * Constructor
     * @param x inital x
     * @param y inital y
     * @param nextY next vertical stop
     * @param speed
     */
    public LiftFloor(int x, int y, int nextY, int speed)
    {
        super(x, y, speed);
        initialY = y;
        this.nextY = nextY;
        collider = new BoxCollider(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move()
    {
        if (currentDirection == Direction.NONE)
            return;
        int currentSpeed = 0;
        if (currentDirection == Direction.UP && (y > nextY || y > initialY))
            currentSpeed = -1 * speed;
        else if (currentDirection == Direction.DOWN && (y < nextY || y < initialY))
            currentSpeed = speed;
        y += currentSpeed;
        if (y == nextY || y == initialY)
            currentDirection = Direction.NONE;
        setChanged();
        notifyObservers(currentDirection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getState()
    {
        return null;
    }

    /**
     * The lift moves in the passed direction
     * 
     * @param direction
     */
    public void moveLift(Direction direction)
    {
        if (currentDirection != Direction.NONE)
            return;
        currentDirection = direction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetAllStatus()
    {
        y = initialY;
        currentDirection = Direction.NONE;
    }
}
