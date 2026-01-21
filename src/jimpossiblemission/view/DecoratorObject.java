/**
 * 
 */
package jimpossiblemission.view;

import java.awt.Graphics2D;

import jimpossiblemission.model.game.GameObject;

/**
 * @author Filippo Taiuti
 *
 */
public abstract class DecoratorObject
{
    // SCREEN SETTINGS
    protected final static int originalTileSize = 32;
    protected final static int scale = 4;

    protected final static int tileSize = originalTileSize * scale;

    protected GameObject gameObject;

    public DecoratorObject(GameObject gameObject)
    {
        this.gameObject = gameObject;
    }

    public GameObject getGameObject()
    {
        return gameObject;
    }

    public abstract void draw(Graphics2D g);

}
