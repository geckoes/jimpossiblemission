package jimpossiblemission.view.gameobject;

import java.awt.Graphics2D;

import jimpossiblemission.model.entity.GameObject;

/**
 * View Class of GameObject
 * 
 * @author Filippo Taiuti
 *
 */
public abstract class GameObjectView
{
    protected int width;
    protected int height;
    public GameObject gameObject;

    /**
     * Constructor of GameObjectView. Require GameObject
     * 
     * @param gameObject
     */
    public GameObjectView(GameObject gameObject)
    {
        this.gameObject = gameObject;
    }

    public abstract void draw(Graphics2D g);

    /**
     * Return if gameObject is active
     * 
     * @return booleaan
     */
    public boolean isActive()
    {
        return gameObject.isActive();
    }

}
