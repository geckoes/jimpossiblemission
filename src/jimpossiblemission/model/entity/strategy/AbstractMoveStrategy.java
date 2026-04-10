package jimpossiblemission.model.entity.strategy;

/**
 * Abstract class for movement strategy
 * @author Filippo Taiuti
 *
 */
public abstract class AbstractMoveStrategy implements MoveStrategy
{
    protected boolean endOfPatrol;

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetMovement()
    {
        endOfPatrol = false;
    }
}
