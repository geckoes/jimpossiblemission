package jimpossiblemission.model.entity;

/**
 * Platform is a gameObject that other gameObject can walk on 
 * 
 * @author Filippo Taiuti
 *
 */
public class Platform extends GameObject
{
    /**
     * Constructor
     * @param x initial x
     * @param y initial y
     */
    public Platform(int x, int y)
    {
        super(x, y);
        collider = new BoxCollider(this);
    }

}
