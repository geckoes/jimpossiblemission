/**
 * 
 */
package jimpossiblemission.view.gameobject;

import jimpossiblemission.model.entity.SuperComputer;

/**
 * SuperComputer View
 * 
 * @author Filippo Taiuti
 *
 */
public class SuperComputerView extends StaticObjectView
{

    /**
     * Constructor of SuperComputer View
     * 
     * @param gameObject as superComputer
     */
    public SuperComputerView(SuperComputer gameObject)
    {
        super(gameObject, "hitboxes.csv", "/Sprites/Objects/SuperComputer/");
    }

}
