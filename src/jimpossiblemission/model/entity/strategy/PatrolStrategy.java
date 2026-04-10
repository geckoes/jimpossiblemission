package jimpossiblemission.model.entity.strategy;

import jimpossiblemission.model.entity.Direction;
import jimpossiblemission.model.entity.Enemy;

/**
 * Patrol Strategy moves the GameObject between two x coordinates.
 *  
 * @author Filippo Taiuti
 *
 */
public class PatrolStrategy extends AbstractMoveStrategy
{
    private int startX, endX;

    public PatrolStrategy(int startX, int endX)
    {
        this.startX = startX;
        this.endX = endX;
        endOfPatrol = false;
    }

    @Override
    public void move(Enemy enemy)
    {
        int deltaSpeed = enemy.getSpeed();
        if (enemy.getDirection() == Direction.LEFT)
            deltaSpeed = -deltaSpeed;
        int newX = enemy.getX() + deltaSpeed;
        if (newX <= startX || newX >= endX)
        {
            if (!endOfPatrol)
            {
                endOfPatrol = true;
            } else if (!enemy.getAttackStrategy().isAttacking())
            {
                endOfPatrol = false;
                enemy.turnDirection();
            }
        }
        if (!endOfPatrol && !enemy.getAttackStrategy().isAttacking())
            enemy.setX(newX);

    }

    @Override
    public boolean isEndOfPatrol()
    {
        return endOfPatrol;
    }

    @Override
    public boolean isMoving()
    {
        return !endOfPatrol;
    }

}
