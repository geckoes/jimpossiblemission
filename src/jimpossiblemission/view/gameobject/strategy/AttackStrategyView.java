package jimpossiblemission.view.gameobject.strategy;

import java.awt.Graphics2D;

import jimpossiblemission.model.entity.strategy.AttackStrategy;

/**
 * Abstract AttackStrategy View
 * 
 * @author Filippo Taiuti
 *
 */
public abstract class AttackStrategyView
{
    protected AttackStrategy attackStrategy;

    /**
     * Constructor of AttackStrategyView
     * 
     * @param attackStrategy
     */
    public AttackStrategyView(AttackStrategy attackStrategy)
    {
        this.attackStrategy = attackStrategy;
    }

    /**
     * Draw attack strategy
     * 
     * @param g Graphics2D
     */
    public abstract void draw(Graphics2D g);
}
