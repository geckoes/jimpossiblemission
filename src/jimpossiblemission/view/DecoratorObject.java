/**
 * 
 */
package jimpossiblemission.view;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import jimpossiblemission.model.game.GameObject;
import jimpossiblemission.model.game.SpriteAnimation;

/**
 * @author Filippo Taiuti
 *
 */
public abstract class DecoratorObject extends Observable implements Observer
{
    // SCREEN SETTINGS
    protected final static int tileSize = 32;
    protected final static int scale = 4;

    protected GameObject gameObject;

    public DecoratorObject(GameObject gameObject)
    {
        this.gameObject = gameObject;
    }

    public GameObject getGameObject()
    {
        return gameObject;
    }

    public abstract ShapeCollider getShapeCollision();
    
    public abstract void draw(Graphics2D g);

}
