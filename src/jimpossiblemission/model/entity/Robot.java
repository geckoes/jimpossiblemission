/**
 * 
 */
package jimpossiblemission.model.entity;

import jimpossiblemission.model.entity.strategy.AttackStrategy;
import jimpossiblemission.model.entity.strategy.MoveStrategy;

/**
 * Class Robot
 * 
 * @author Filippo Taiuti
 *
 */
public class Robot extends Enemy
{
    private RobotState robotState;

    public enum RobotState
    {
        IDLE, RUN, TURN, ATTACK
    }

    /**
     * Contructor
     * 
     * @param x initial x
     * @param y initial y
     */
    public Robot(int x, int y, int speed, MoveStrategy moveStrategy, AttackStrategy attackStrategy)
    {
        super(x, y, speed, moveStrategy, attackStrategy);
        collider = new BoxCollider(this);
        robotState = RobotState.IDLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getState()
    {
        return robotState.name();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetAllStatus()
    {
        super.resetAllStatus();
        robotState = RobotState.IDLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move()
    {
        super.move();
        robotState = RobotState.IDLE;
        if (moveStrategy.isMoving())
            if (robotState != RobotState.RUN)
            {
                robotState = RobotState.RUN;
            }
        if (!attackStrategy.isAttacking())
        {
            setChanged();
            notifyObservers(robotState);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attack()
    {
        super.attack();
        if (attackStrategy.isAttacking())
        {
            robotState = RobotState.ATTACK;

            setChanged();
            notifyObservers(robotState);
        }
    }

}
