/**
 * 
 */
package jimpossiblemission.view.gameobject;

import jimpossiblemission.model.entity.Elevator;

/**
 * Elevator View
 * @author Filippo Taiuti
 *
 */
public class ElevatorView extends StaticObjectView
{

    /**
     * Constructor of Elevator View
     * 
     * @param elevator
     */
    public ElevatorView(Elevator elevator)
    {
        super(elevator, "hitboxes.csv", "/Sprites/Elevator/");
    }

}
