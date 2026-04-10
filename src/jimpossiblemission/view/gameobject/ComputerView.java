/**
 * 
 */
package jimpossiblemission.view.gameobject;

import jimpossiblemission.model.entity.Computer;

/**
 * Computer View
 * @author Filippo Taiuti
 *
 */
public class ComputerView extends StaticObjectView
{

    /**
     * Constructor of Computer View
     * 
     * @param computer
     */
    public ComputerView(Computer computer)
    {
        super(computer, "hitboxes.csv", "/Sprites/Objects/Computer/");
    }

}
