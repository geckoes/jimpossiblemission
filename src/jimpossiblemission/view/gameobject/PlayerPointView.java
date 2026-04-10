package jimpossiblemission.view.gameobject;

import java.awt.Graphics2D;

import jimpossiblemission.model.entity.PlayerPoint;

/**
 * PlayerPoint View
 * 
 * @author Filippo Taiuti
 *
 */
public class PlayerPointView extends GameObjectView
{

	/**
	 * Contructor of PlayerPoit View
	 * 
	 * @param gameObject as PlayerPoint
	 */
    public PlayerPointView(PlayerPoint gameObject)
    {
        super(gameObject);
    }

    /**
     * Nothing to draw
     */
    @Override
    public void draw(Graphics2D g)
    {
    }

}
