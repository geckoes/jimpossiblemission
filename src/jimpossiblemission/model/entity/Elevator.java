package jimpossiblemission.model.entity;

/**
 * Represent the objects that cannot move. They can contain badges to use with
 * computers.
 *
 * @author Filippo Taiuti
 *
 */
public class Elevator extends DynamicObject
{
    private Player player;
    private int floor;
    private static final int MAX_FLOOR = 2;
    private boolean moving;

    /**
     * Costructor
     * 
     * @param x coordinate
     * @param y coordinate
     * @param speed of elevator 
     */
    public Elevator(int x, int y, int speed)
    {
        super(x, y, speed);
        collider = new BoxCollider(this);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move()
    {
        if (player != null && player.isInElevator())
        {
            int dy = 0;
            if (currentDirection == Direction.UP && (floor > 0 || y > initialY))
            {
                dy -= speed;
            } else if (currentDirection == Direction.DOWN && floor <= MAX_FLOOR)
            {
                dy += speed;
            }
            y += dy;
            if (!moving && dy != 0)
            {
                moving = true;
                setChanged();
                notifyObservers(currentDirection);
            } else if (moving && y == initialY)
            {
                currentDirection = Direction.NONE;
                moving = false;
                setChanged();
                notifyObservers(currentDirection);
            }
            player.inMainElevator(this);
        }
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
     * Reset the position to newer coordinate
     * 
     * @param newX coordinate
     * @param newY coordinate
     */
    public void resetPosition(int newX, int newY)
    {
        initialX = newX;
        initialY = newY;
        resetAllStatus();
    }

    /**
     * Update the vertical position 
     * 
     * @param verticalPosition y coordinate
     */
    public void updateVerticalPosition(int verticalPosition)
    {
        y = verticalPosition;
    }

    /**
     * Set the player in elevator
     *  
     * @param player
     */
    public void setPlayerInElevator(Player player)
    {
        this.player = player;
        player.inMainElevator(this);
        if (player.getDirection() == Direction.UP || player.getDirection() == Direction.DOWN)
        {
            lastDirection = currentDirection;
            currentDirection = player.getDirection();
        }
    }

    /**
     * Updates floor number
     *  
     * @param deltaFloor
     */
    public void updateFloor(int deltaFloor)
    {
        floor += deltaFloor;
    }
}
