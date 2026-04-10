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
public class ElectricAttackStrategyView extends AttackStrategyView
{
    private int numberOfLights;

    /**
     * Constructor of ElectricAttackStrategyView
     * 
     * @param attackStrategy
     */
    public ElectricAttackStrategyView(AttackStrategy attackStrategy)
    {
        super(attackStrategy);
        numberOfLights = 2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics2D g2)
    {
        g2.setColor(Color.GREEN);

        double random;

        for (int i = 0; i < numberOfLights; i++)
        {
            int lightY = attackStrategy.getHitBox().getHeight() / (i + 1)
                    + (int) Math.random() * attackStrategy.getHitBox().getHeight() / (i + 1);

            for (int w = 0; w < attackStrategy.getHitBox().getWidth(); w++)
            {
                int x = attackStrategy.getHitBox().getX() + w;
                random = Math.random();
                int dy = random < .33 ? -1 : random > .66 ? 1 : 0;
                lightY += dy;

                lightY = Math.max(lightY, 0);
                lightY = Math.min(lightY, attackStrategy.getHitBox().getHeight());

                g2.drawLine(x, attackStrategy.getHitBox().getY() + lightY, x,
                        attackStrategy.getHitBox().getY() + lightY);
            }
        }
    }

}
