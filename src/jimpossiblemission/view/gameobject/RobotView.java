package jimpossiblemission.view.gameobject;

import java.awt.Graphics2D;

import jimpossiblemission.model.entity.Direction;
import jimpossiblemission.model.entity.Robot;

/**
 * Robot View
 * 
 * @author Filippo Taiuti
 *
 */
public class RobotView extends EnemyView
{

    /**
     * Constructor of Robot View
     * 
     * @param gameObject as robot
     */
    public RobotView(Robot gameObject)
    {
        super(gameObject, "hitboxes.csv", "/Sprites/Enemies/ROBOT/Standing/");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics2D g2)
    {
        int x = gameObject.getX();
        int y = gameObject.getY();

        width = getSpriteAnimation().getImage().getWidth();
        height = getSpriteAnimation().getImage().getHeight();

        if (((Robot) gameObject).getDirection() == Direction.LEFT)
        {
            x = (int) gameObject.getX() + width;
            width = -width;
        }
        g2.drawImage(getSpriteAnimation().getImage(), x, y, width, height, null);
        if (((Robot) gameObject).getAttackStrategy().isAttacking())
            attackStrategyView.draw(g2);
    }

}
