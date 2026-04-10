package jimpossiblemission.view.gameobject.strategy;

import java.awt.Graphics2D;

import jimpossiblemission.model.entity.strategy.AttackStrategy;

/**
 * Draw NoAttackStrategy in View
 * @author Filippo Taiuti
 *
 */
public class NoAttackStrategyView extends AttackStrategyView
{
    /**
     * Constructor of NoAttackStrategyView
     * 
     * @param attackStrategy
     */
    public NoAttackStrategyView(AttackStrategy attackStrategy)
    {
        super(attackStrategy);
    }

    /**
     * Nothing to draw
     */
    @Override
    public void draw(Graphics2D g)
    {
    }

}
