package jimpossiblemission.model.entity;

/**
 * Wall is a gameObject that other gameObject cannot pass 
 * 
 * @author Filippo Taiuti
 *
 */
public class Wall extends GameObject
{
    /**
     * Constructor
     * @param x initial x
     * @param y initial y
     */
    public Wall(int x, int y)
    {
        super(x, y);
        collider = new BoxCollider(this);
    }

}
