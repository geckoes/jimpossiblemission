package jimpossiblemission.model.entity.strategy;

import jimpossiblemission.model.entity.DynamicObject;
import jimpossiblemission.model.entity.Enemy;

/**
 * Follow Strategy moves the GameObject through the target.
 * 
 * @author Filippo Taiuti
 *
 */
public class FollowStrategy extends AbstractMoveStrategy
{
    private DynamicObject target;
    private int counter;
    private final static int MODULE = 3;

    /**
     * {@inheritDoc}
     */
    @Override
    public void move(Enemy enemy)
    {
        if (target == null)
            return;
        counter++;

        if (counter % MODULE == 0)
        {
            int xSpeed = 0, ySpeed = 0;
            int horizontalDistance = target.getX() - enemy.getX();
            int verticalDistance = target.getY() - enemy.getY();

            int speed = enemy.getSpeed();
            if (Math.abs(horizontalDistance) > Math.abs(verticalDistance))
            {
                // move horizontally
                xSpeed = speed;
                if (horizontalDistance < 0)
                    xSpeed *= -1;
            } else
            {
                // move vertically
                ySpeed = speed;
                if (verticalDistance < 0)
                    ySpeed *= -1;
            }
            enemy.setX(enemy.getX() + (int) xSpeed);
            enemy.setY(enemy.getY() + (int) ySpeed);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEndOfPatrol()
    {
        return false;
    }

    /**
     * Set target to follow
     * 
     * @param target to follow
     */
    public void setTarget(DynamicObject target)
    {
        this.target = target;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMoving()
    {
        return target != null;
    }
}
