/**
 * 
 */
package jimpossiblemission.model.game;

import java.util.Observable;
import java.util.Observer;

import jimpossiblemission.controller.GamePlayController;

/**
 * @author Filippo Taiuti
 *
 */
@SuppressWarnings("deprecation")
public class Player extends GameObject implements Observer, CanWalk, CanJump, CanCollide
{
    private final static double INITIAL_SPEED = 4.0;
    private boolean isAlive = true;
    private double speed;
    GamePlayController gameplayController;
    String oldDirection;
    protected String direction;

    private double jump_speed = 15.0;
    private double gravity = 0.5;

    private boolean onGround;

    public Player(double x, double y, double width, double height, double speed)
    {
        super(x, y, width, height);
        this.speed = speed;
        this.onGround = false;
    }

    public Player(double x, double y, double width, double height)
    {
        this(x, y, width, height, INITIAL_SPEED);
    }

    public void takeDamage()
    {
        isAlive = false;
    }

    public boolean isAlive()
    {
        return isAlive;
    }

    public void setController(GamePlayController gameplayController)
    {
        this.gameplayController = gameplayController;
    }

    @Override
    public void update()
    {
        jump();
        walk();
    }

    @Override
    public void jump()
    {
        if (onGround)
        {
            y += jump_speed;
            onGround = false;
        }
    }

    @Override
    public void walk()
    {
        switch (gameplayController.getDirection()) {
            case Direction.UP:
                System.out.println("Player UP");
                direction = "up";
                isMoving = true;
                y -= speed;
                break;
            case Direction.DOWN:
                System.out.println("Player DOWN");
                direction = "down";
                isMoving = true;
                y += speed;
                break;
            case Direction.LEFT:
                System.out.println("Player LEFT");
                direction = "left";
                isMoving = true;
                x -= speed;
                break;
            case Direction.RIGHT:
                System.out.println("Player RIGHT");
                direction = "right";
                isMoving = true;
                x += speed;
                break;
            case Direction.SPACE:
                System.out.println("Player SPACE");
                direction = "jump";
                isMoving = true;
                jump();
                x += speed;
                break;
            case Direction.NONE:
            default:
                isMoving = false;
                direction = "none";
                break;
        }
        oldDirection = direction;
    }

    public Direction getDirection()
    {
        return gameplayController.getDirection();
    }

    @Override
    public void setCollider(ShapeCollider collider)
    {

    }

    @Override
    public void update(Observable o, Object arg)
    {
        jump();
        walk();
    }
}
