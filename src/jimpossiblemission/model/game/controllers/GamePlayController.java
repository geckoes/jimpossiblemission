/**
 * 
 */
package jimpossiblemission.model.game.controllers;

import java.util.Observable;

/**
 * @author Filippo Taiuti
 *
 */
@SuppressWarnings("deprecation")
public abstract class GamePlayController extends Observable
{
    Direction currentDirection;

    public GamePlayController()
    {
        currentDirection = Direction.NONE;
    }

    public abstract Direction getDirection();

}
