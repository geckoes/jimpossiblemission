/**
 * 
 */
package jimpossiblemission.view;

import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import jimpossiblemission.model.game.GameObject;

/**
 * @author Filippo Taiuti
 *
 */
@SuppressWarnings({ "deprecation" })
public abstract class DecoratorObject extends Observable implements Observer
{
    // SCREEN SETTINGS
    protected final static int tileSize = 32;
    protected final static int scale = 3;
    protected int width;
    protected int height;

    protected final GameObject gameObject;

    public DecoratorObject(GameObject gameObject)
    {
        this.gameObject = gameObject;
        this.width = tileSize * scale;
        this.height = tileSize;
    }

    public boolean intersects(DecoratorObject other)
    {
        return gameObject.getX() < other.gameObject.getX() + other.width
                && gameObject.getX() + width > other.gameObject.getX()
                && gameObject.getY() < other.gameObject.getY() + other.height
                && gameObject.getY() + height > other.gameObject.getY();
    }

    public abstract void draw(Graphics2D g);

}
