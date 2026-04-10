package jimpossiblemission.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Lift are platform that can move up and down.
 * Usual composed by 2 or 3 platforms that move simultaneously
 * 
 * @author Filippo Taiuti
 *
 */
public class Lift
{
    private List<LiftFloor> lift = new ArrayList<>();

    /**
     * Constructor of lift
     * 
     */
    public Lift()
    {
    }

    /**
     * Adds a liftFloor to lift
     * 
     * @param liftFloor
     */
    public void addLift(LiftFloor liftFloor)
    {
        lift.add(liftFloor);
    }

    /**
     * Get all liftFloor in lift
     * @return
     */
    public List<LiftFloor> getLifts()
    {
        return lift;
    }

    /**
     * Move lift up or down
     * 
     * @param direction
     */
    public void move(Direction direction)
    {
        if (direction == Direction.UP || direction == Direction.DOWN)
        {
            lift.forEach(l ->
            {
                l.moveLift(direction);
            });
        }
    }

}
