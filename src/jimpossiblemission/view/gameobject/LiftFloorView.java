package jimpossiblemission.view.gameobject;

import jimpossiblemission.model.entity.LiftFloor;

/**
 * LiftFloor View
 * @author Filippo Taiuti
 *
 */
public class LiftFloorView extends StaticObjectView
{

    /**
     * Constructor of LiftFloor View
     * 
     * @param gameObject as LiftFloor
     */
    public LiftFloorView(LiftFloor gameObject)
    {
        super(gameObject, "hitboxes.csv", "/Sprites/Levels/LevelTiles/lift/");
    }

}
