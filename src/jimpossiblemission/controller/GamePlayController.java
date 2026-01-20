/**
 * 
 */
package jimpossiblemission.controller;

import java.util.Observable;

import jimpossiblemission.model.game.Direction;

/**
 * @author Filippo Taiuti
 *
 */
public abstract class GamePlayController extends Observable
{
    public abstract Direction getDirection();

}
