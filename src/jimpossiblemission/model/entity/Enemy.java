package jimpossiblemission.model.entity;

import jimpossiblemission.model.entity.strategy.AttackStrategy;
import jimpossiblemission.model.entity.strategy.MoveStrategy;

/**
 * Class Enemy
 * 
 * @author Filippo Taiuti
 *
 */
public abstract class Enemy extends DynamicObject
{
    protected MoveStrategy moveStrategy;
    protected AttackStrategy attackStrategy;
    protected boolean blocked;

    /**
     * Constructor
     * 
     * @param x initial x
     * @param y initial y
     * @param speed of the enemy
     * @param moveStrategy strategy to move
     * @param attackStrategy strategy to attack
     */
    public Enemy(int x, int y, int speed, MoveStrategy moveStrategy, AttackStrategy attackStrategy)
    {
        super(x, y, speed);
        this.moveStrategy = moveStrategy;
        this.attackStrategy = attackStrategy;
    }

    /**
     * Tells if the Enemy cannot move
     * 
     * @return boolean
     */
    public boolean isBlocked()
    {
        return blocked;
    }

    /**
     * Set the blocked state
     * 
     * @param blocked
     */
    public void setBlocked(boolean blocked)
    {
        this.blocked = blocked;
    }

    /**
     * Enemy attacks
     */
    public void attack()
    {
        attackStrategy.attack(this);
    }

    /**
     * Enemy changes direction
     */
    public void turnDirection()
    {
        currentDirection = currentDirection == Direction.LEFT ? Direction.RIGHT : Direction.LEFT;
    }

    /**
     * Set x
     * @param newX
     */
    public void setX(int newX)
    {
        x = newX;
    }

    /**
     * Set y
     * @param newY
     */
    public void setY(int newY)
    {
        y = newY;
    }

    /**
     * Return the attack strategy
     * 
     * @return attackStrategy
     */
    public AttackStrategy getAttackStrategy()
    {
        return attackStrategy;
    }

    /**
     * Return the move strategy
     * 
     * @return moveStrategy
     */
    public MoveStrategy getMoveStrategy()
    {
        return moveStrategy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move()
    {
        moveStrategy.move(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetAllStatus()
    {
        super.resetAllStatus();
        attackStrategy.resetAttack();
        moveStrategy.resetMovement();
        blocked = false;
    }

    public void setDirection(Direction direction)
    {
        currentDirection = direction;
    }
}
