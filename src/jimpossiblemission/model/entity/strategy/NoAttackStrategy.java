package jimpossiblemission.model.entity.strategy;

import jimpossiblemission.model.entity.Enemy;
import jimpossiblemission.model.entity.ShapeCollider;

/**
 *  No Attack starategy here
 * 
 * @author Filippo Taiuti
 *
 */
public class NoAttackStrategy extends AbstractAttackStrategy
{

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAttacking()
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attack(Enemy enemy)
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isColliding(ShapeCollider collider)
    {
        return false;
    }

}
