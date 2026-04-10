/**
 * 
 */
package jimpossiblemission.view.gameobject;

import jimpossiblemission.model.entity.Wall;

/**
 * Wall View
 * 
 * @author Filippo Taiuti
 *
 */
public class WallView extends StaticObjectView
{

    /**
     * Constructor of WallView
     * @param gameObject as Wall
     */
    public WallView(Wall gameObject)
    {
        super(gameObject, "hitboxes.csv", "/Sprites/Levels/LevelTiles/wall/");
    }

}
