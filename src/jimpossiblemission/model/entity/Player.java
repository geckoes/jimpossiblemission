/**
 * Player class
 */
package jimpossiblemission.model.entity;

import jimpossiblemission.model.entity.Computer.TypeOfComputer;

/**
 * Player is a dynamicObject that can fall, can collide and can jump.
 * It also can take damage from other gameObjects (Enemy)
 * 
 * @author Filippo Taiuti
 *
 */
public class Player extends DynamicObject implements CanFall, CanCollide, CanJump
{
    private static final double GRAVITY = 1;
    private static final int MAX_LIFE = 3;
    private static final int JUMP_FORCE = 8;
    private static final int STEP_FRAMES = 24;

    private int lives;
    private double jumpVelocity;
    private int startJump;
    private int steps;
    private PlayerState playerState;

    private boolean inElevator;

    private TypeOfComputer lastComputerHacked;

    /**
     * Player state
     *
     */
    public enum PlayerState
    {
        Standing, Running, Jumping, Searching, Electrified, Hacking, FallenDown
    }

    /**
     * Constructor
     * @param x initial x
     * @param y initial y
     * @param speed of player
     */
    public Player(int x, int y, int speed)
    {
        super(x, y, speed);
        lives = MAX_LIFE;
        onGround = false;
        inElevator = false;
        jumpVelocity = JUMP_FORCE;
        playerState = PlayerState.Standing;
        lastDirection = Direction.LEFT;

        collider = new BoxCollider(this);
    }

    /**
     * Returns the state of player
     * @return playerState
     */
    public PlayerState getPlayerState()
    {
        return playerState;
    }

    /**
     * The player takes damage from enemy
     * 
     * @param playerState
     */
    public void takeDamage(PlayerState playerState)
    {
        lives--;
        this.playerState = playerState;
        setChanged();
        notifyObservers();
    }

    /**
     * The player falls
     */
    public void fall()
    {
        if (!onGround)
        {
            y += GRAVITY;
        }
        if (y > 300 && !inElevator)
        {
            takeDamage(PlayerState.FallenDown);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move()
    {
        if (playerState != PlayerState.Standing)
            if (playerState == PlayerState.Jumping)
                jumping();
            else if (currentDirection == Direction.LEFT)
            {
                x -= speed;
                steps++;
                lastDirection = Direction.LEFT;
            } else if (currentDirection == Direction.RIGHT)
            {
                x += speed;
                steps++;
                lastDirection = Direction.RIGHT;
            }
        if (steps == STEP_FRAMES) {
        	steps = 0;
        	setChanged();
        	notifyObservers();
        }
        fall();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getState()
    {
        return playerState.name();
    }

    // jump
    private void jumping()
    {
        if (playerState != PlayerState.Electrified)
        {
            int speedJump = speed + 2;
            if (lastDirection == Direction.LEFT)
                x -= speedJump;
            else
                x += speedJump;

            jumpVelocity -= GRAVITY;
            y -= jumpVelocity;
            jumpVelocity = Math.max(0, jumpVelocity);
            if (y >= startJump)
            {
                playerState = PlayerState.Standing;
                // currentDirection = Direction.NONE;
                startJump = 0;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goLeft()
    {
        if (playerState != PlayerState.Electrified)
        {
            super.goLeft();
            playerState = PlayerState.Running;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goRight()
    {
        if (playerState != PlayerState.Electrified)
        {
            super.goRight();
            playerState = PlayerState.Running;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Direction getDirection()
    {
        if (playerState == PlayerState.Jumping)
        {
            return lastDirection;
        }
        return currentDirection;
    }

    public Direction getLastDirection()
    {
        return lastDirection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop()
    {
        if (playerState != PlayerState.Electrified)
        {
            super.stop();
            if (playerState == PlayerState.Jumping)
                return;
            playerState = PlayerState.Standing;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void jump()
    {
        if (!inElevator)
        {
            startJump = y;
            y -= jumpVelocity;
            playerState = PlayerState.Jumping;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void grounded(GameObject ground)
    {
        onGround = true;
        inElevator = false;

        jumpVelocity = JUMP_FORCE;
        y = ground.y - collider.getHitBox().getY() - collider.getHitBox().getHeight();
        if (playerState == PlayerState.Jumping)
        {
            y -= 8;
            playerState = PlayerState.Standing;
        }
    }

    /**
     * Set the player in the Elevator
     * 
     * @param elevator
     */
    public void inMainElevator(Elevator elevator)
    {
        y = elevator.getY() + elevator.getCollider().getHitBox().getY() - collider.getHitBox().getY()
                - collider.getHitBox().getHeight();
        inElevator = true;
    }

    /**
     * Tells if the player is in elevator
     * 
     * @return boolean
     */
    public boolean isInElevator()
    {
        return inElevator;
    }

    /**
     * Tells if the player is still alive
     * @return
     */
    public boolean isAlive()
    {
        return lives > 0;
    }

    /**
     * Gets the number of remaining lives
     * 
     * @return int
     */
    public int getLives()
    {
        return lives;
    }

    /**
     * Reset the player position
     * 
     * @param playerPoint
     */
    public void resetPosition(PlayerPoint playerPoint)
    {
        playerState = PlayerState.Standing;

        x = playerPoint.x;
        y = playerPoint.y - collider.getHitBox().getHeight() - 1;
    }

    /**
     * Player is searching
     */
    public void search()
    {
        playerState = PlayerState.Searching;
    }

    /**
     * Player is hacking the computer
     * 
     * @param typeOfComputer
     */
    public void hacking(TypeOfComputer typeOfComputer)
    {
        playerState = PlayerState.Hacking;
        lastComputerHacked = typeOfComputer;
        setChanged();
        notifyObservers();
    }

    /**
     * Returns the last computer hacked
     * @return
     */
    public TypeOfComputer getLastComputerHacked()
    {
        return lastComputerHacked;
    }

}
