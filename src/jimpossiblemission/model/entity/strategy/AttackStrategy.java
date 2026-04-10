package jimpossiblemission.model.entity.strategy;

import jimpossiblemission.model.entity.Direction;
import jimpossiblemission.model.entity.Enemy;
import jimpossiblemission.model.entity.HitBox;
import jimpossiblemission.model.entity.ShapeCollider;

/**
 * Interface to implements to create a new attack strategy
 * 
 * @author Filippo Taiuti
 *
 */
public interface AttackStrategy
{
    /**
     * Attack describe how enemy attack
     * 
     * @param enemy that can attack
     */
    void attack(Enemy enemy);

    /**
     * Tells if enemy is attacking
     * 
     * @return boolean if enemy attacks
     */
    boolean isAttacking();

    /**
     * Tells if enemy is colliding to another collider
     * 
     * @param shapeCollider is the collider
     * @return boolean if enemy collides
     */
    boolean isColliding(ShapeCollider shapeCollider);

    /**
     * Returns the hitbox
     * 
     * @return hitbox
     */
    HitBox getHitBox();

    /**
     * Reset attack state
     */
    void resetAttack();

    /**
     * Return the attack direction
     * 
     * @return direction of attack
     */
    Direction getAttackDirection();

}
