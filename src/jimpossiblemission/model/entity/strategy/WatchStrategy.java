package jimpossiblemission.model.entity.strategy;

import jimpossiblemission.model.entity.Enemy;

/**
 * Watch Strategy doesn't move the GameObject but only watches
 * 
 * @author Filippo Taiuti
 *
 */
public class WatchStrategy extends AbstractMoveStrategy
{
    /**
     * {@inheritDoc}
     */
    @Override
    public void move(Enemy enemy)
    {
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
     * {@inheritDoc}
     */
    @Override
    public boolean isMoving()
    {
        return false;
    }

}
