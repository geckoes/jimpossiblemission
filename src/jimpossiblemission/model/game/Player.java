/**
 * 
 */
package jimpossiblemission.model.game;

import java.util.Observable;
import java.util.Observer;

import jimpossiblemission.controller.GamePlayController;
import jimpossiblemission.view.ShapeCollider;

/**
 * @author Filippo Taiuti
 *
 */
@SuppressWarnings("deprecation")
public class Player extends GameObject implements Observer, CanWalk, CanJump, CanFall
{
    private boolean isAlive = true;
    private double speed;
    GamePlayController gameplayController;
    String oldDirection;
    protected String direction;

    private double jump_speed = 15.0;

    public Player(double x, double y, double speed, double gravity)
    {
        super(x, y);
        this.speed = speed;
        this.onGround = false;
        this.gravity = gravity;
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
    public boolean isMoving() {
    	return super.isMoving() || !onGround;
    }
    @Override
    public void jump()
    {
        if (!onGround)
        {
            y -= jump_speed;
            onGround = false;
        }
    }

    @Override
    public void walk()
    {
        switch (gameplayController.getDirection()) {
            case Direction.UP:
                System.out.println("Player UP");
                oldDirection = "up";
                isMoving = true;
                y -= speed;
                break;
            case Direction.DOWN:
                System.out.println("Player DOWN");
                oldDirection = "down";
                isMoving = true;
                y += speed;
                break;
            case Direction.LEFT:
                System.out.println("Player LEFT");
                oldDirection = "left";
                isMoving = true;
                x -= speed;
                break;
            case Direction.RIGHT:
                System.out.println("Player RIGHT");
                oldDirection = "right";
                isMoving = true;
                x += speed;
                break;
            case Direction.SPACE:
                System.out.println("Player SPACE");
                oldDirection = "jump";
                isMoving = true;
                jump();
                x += speed;
                break;
            case Direction.NONE:
            default:
                isMoving = false;
                oldDirection = "none";
                break;
        }
    }

    public Direction getDirection()
    {
        return gameplayController.getDirection();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        walk();
        fall();
    }

	@Override
	public void fall() {
        if (!onGround)
            y += gravity;
	}

}
