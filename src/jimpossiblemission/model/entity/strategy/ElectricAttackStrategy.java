package jimpossiblemission.model.entity.strategy;

import jimpossiblemission.model.entity.Direction;
import jimpossiblemission.model.entity.Enemy;
import jimpossiblemission.model.entity.ShapeCollider;

/**
 * Electric Attack starts at the end of patrol and continue unitil timer is completed
 * 
 * @author Filippo Taiuti
 *
 */
public class ElectricAttackStrategy extends AbstractAttackStrategy
{
    private static final int ATTACK_DURATION = 50;

    private static final int WIDTH = 50;
    private static final int HEIGHT = 10;

    /**
     * {@inheritDoc}
     */
    @Override
    public void attack(Enemy enemy)
    {
        attackDirection = enemy.getDirection();
        int x = enemy.getCollider().getLeftBound() + (enemy.getCollider().getHitBox().getWidth()) / 2;
        if (enemy.getDirection() == Direction.LEFT)
        {
            x -= WIDTH;
        }
        hitBox.updateBox(x, enemy.getY(), WIDTH, HEIGHT);
        if (enemy.getMoveStrategy().isEndOfPatrol() && timer < 0)
        {
            timer = ATTACK_DURATION;
            attacking = true;
        }
        if (attacking && timer <= 0)
        {
            attacking = false;
        }
        timer--;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAttacking()
    {
        return attacking;
    }

    @Override
    public boolean isColliding(ShapeCollider collider)
    {
        if (!isAttacking())
            return false;
        if (collider.getLeftBound() < hitBox.getX() + WIDTH && collider.getRightBound() > hitBox.getX()
                && collider.getTopBound() < hitBox.getY() + HEIGHT
                && collider.getBottomBound() > hitBox.getY() + HEIGHT)
            return true;
        return false;
    }

}
