/**
 * 
 */
package jimpossiblemission.view.gameobject;

import jimpossiblemission.model.entity.Platform;

/**
 * Platform View
 * 
 * @author Filippo Taiuti
 *
 */
public class PlatformView extends StaticObjectView
{

    /**
     * Constructor of Platform View
     * 
     * @param gameObject as Platform
     */
    public PlatformView(Platform gameObject)
    {
        super(gameObject, "hitboxes.csv", "/Sprites/Levels/LevelTiles/platform/");
    }

}
