package jimpossiblemission.model.entity;

import java.util.Observable;

/**
 * Abstract Class Entity extends Observable
 *  
 * @author Filippo Taiuti
 *
 */
@SuppressWarnings("deprecation")
public abstract class Entity extends Observable
{
    protected int x, y;

    /**
     * Constructor
     * @param x initial x
     * @param y initial y
     */
    public Entity(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Get x
     * @return int x
     */
    public int getX()
    {
        return x;
    }

    /**
     * Get y
     * @return int y
     */
    public int getY()
    {
        return y;
    }
}
