/**
 * 
 */
package jimpossiblemission.model.game;

import java.util.Observable;
import java.util.Observer;

import jimpossiblemission.model.game.controllers.Direction;
import jimpossiblemission.model.game.controllers.GamePlayController;

/**
 * @author Filippo Taiuti
 *
 */
@SuppressWarnings("deprecation")
public class Player extends GameObject implements Observer, CanWalk, CanJump, CanFall
{
    private boolean isAlive;
    private double speed;
    GamePlayController gameplayController;
    String oldDirection;
    protected String direction;

    private int jump_speed = 15;

    public Player(int x, int y, int speed, int gravity)
    {
        super(x, y);
        this.speed = speed;
        this.isAlive = true;
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
    public boolean isMoving()
    {
        return super.isMoving() || !onGround;
    }

    public boolean isSearching()
    {
        return false;
    }

    @Override
    public void jump()
    {
        if (!onGround)
        {
            x += speed;
            y -= jump_speed;
            onGround = false;
        }
    }

    @Override
    public void walk()
    {
        isMoving = true;
        onGround = false;
        switch (gameplayController.getDirection()) {
            case Direction.UP:
                // search
                oldDirection = "up";
//                y -= speed;
                break;
            case Direction.DOWN:
                oldDirection = "down";
                y += speed;
                break;
            case Direction.LEFT:
                oldDirection = "left";
                x -= speed;
                break;
            case Direction.RIGHT:
                oldDirection = "right";
                x += speed;
                break;
            case Direction.JUMP:
                oldDirection = "jump";
                jump();
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
    public void fall()
    {
        if (!onGround)
            y += gravity;
    }

}
