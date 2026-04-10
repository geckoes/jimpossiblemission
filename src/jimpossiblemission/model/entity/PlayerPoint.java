package jimpossiblemission.model.entity;

/**
 * PlayerPoint is the point where the player returns in play
 * 
 * @author Filippo Taiuti
 *
 */
public class PlayerPoint extends GameObject
{
    /**
     * Constructor
     * @param x initial x
     * @param y initial y
     */
    public PlayerPoint(int x, int y)
    {
        super(x, y);
        collider = new BoxCollider(this);
        collider.disableCollider();
    }

}
