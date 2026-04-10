package jimpossiblemission.view.gameobject;

import jimpossiblemission.model.entity.BigBomb;

/**
 * BigBomb View
 * 
 * @author Filippo Taiuti
 *
 */
public class BigBombView extends EnemyView
{

    /**
     * Constructor of BigBomb View
     * 
     * @param gameObject as BigBomb
     */
    public BigBombView(BigBomb gameObject)
    {
        super(gameObject, "hitboxes.csv", "/Sprites/Enemies/SPHERE/");
    }

}
