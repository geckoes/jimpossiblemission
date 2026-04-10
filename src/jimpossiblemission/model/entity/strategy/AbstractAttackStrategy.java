package jimpossiblemission.model.entity.strategy;

import jimpossiblemission.model.entity.Direction;
import jimpossiblemission.model.entity.Enemy;
import jimpossiblemission.model.entity.HitBox;

/**
 * Abstract class for attack strategies.
 * 
 * @author Filippo Taiuti
 *
 */
public abstract class AbstractAttackStrategy implements AttackStrategy
{
    protected HitBox hitBox;
    protected boolean attacking;
    protected int timer;
    protected Enemy enemy;
    protected Direction attackDirection;

    /**
     * Constructor initializes the strategy by creating an empty hitbox. 
     */
    public AbstractAttackStrategy()
    {
        hitBox = new HitBox();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HitBox getHitBox()
    {
        return hitBox;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetAttack()
    {
        attacking = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attack(Enemy enemy)
    {
        attackDirection = enemy.getDirection();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Direction getAttackDirection()
    {
        return attackDirection;
    }
}
