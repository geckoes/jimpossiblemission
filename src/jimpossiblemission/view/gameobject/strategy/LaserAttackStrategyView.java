package jimpossiblemission.view.gameobject.strategy;

import java.awt.Color;
import java.awt.Graphics2D;

import jimpossiblemission.model.entity.strategy.AttackStrategy;

/**
 * Draw the ElectricAttackStrategy in View
 * 
 * @author Filippo Taiuti
 *
 */
public class LaserAttackStrategyView extends AttackStrategyView
{
    /**
     * Constructor of ElectricAttackStrategyView
     * 
     * @param attackStrategy
     */
    public LaserAttackStrategyView(AttackStrategy attackStrategy)
    {
        super(attackStrategy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics2D g2)
    {
        g2.setColor(Color.BLUE);

        for (int i = 0; i < 3; i++)
        {
            int lightY = attackStrategy.getHitBox().getHeight() / 2 + i - 1;

            g2.drawLine(attackStrategy.getHitBox().getX(), attackStrategy.getHitBox().getY() + lightY,
                    attackStrategy.getHitBox().getX() + attackStrategy.getHitBox().getWidth(),
                    attackStrategy.getHitBox().getY() + lightY);
        }
    }

}
