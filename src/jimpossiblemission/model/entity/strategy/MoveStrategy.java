package jimpossiblemission.model.entity.strategy;

import jimpossiblemission.model.entity.Enemy;

/**
 * Interface move strategy to used to create a new strategy of movement
 * 
 * @author Filippo Taiuti
 *
 */
public interface MoveStrategy
{
    /**
     * move the enemy
     * 
     * @param enemy owner of movestrategy
     */
    void move(Enemy enemy);

    /**
     * Tells if the patrol is end
     * @return boolean
     */
    boolean isEndOfPatrol();

    /**
     *  Resets the movement flags 
     */
    void resetMovement();

    /**
     * Tells if the gameObject is moving
     * @return boolean
     */
    boolean isMoving();
}
