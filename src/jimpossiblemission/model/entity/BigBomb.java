package jimpossiblemission.model.entity;

import jimpossiblemission.model.entity.strategy.AttackStrategy;
import jimpossiblemission.model.entity.strategy.MoveStrategy;

/**
 * Class BigBomb exteds Enemy
 * 
 * @author Filippo Taiuti
 *
 */
public class BigBomb extends Enemy
{
    /**
     * Constructor of BigBomb
     * 
     * @param x initial x
     * @param y initial y
     * @param speed of gameObject
     * @param moveStrategy strategy to move
     * @param attackStrategy strategy to attack
     */
    public BigBomb(int x, int y, int speed, MoveStrategy moveStrategy, AttackStrategy attackStrategy)
    {
        super(x, y, speed, moveStrategy, attackStrategy);
        collider = new BoxCollider(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getState()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move()
    {
        super.move();
        setChanged();
        notifyObservers();
    }

}
