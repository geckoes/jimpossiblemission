package jimpossiblemission.model.entity.command;

import jimpossiblemission.model.entity.Player;

/**
 * Functional interface to send command to stop to Player
 * 
 * @author Filippo Taiuti
 *
 */
public class IdleCommand implements CommandBehaviour
{
    /**
     * Executes the stop command 
     */
    @Override
    public void execute(Player player)
    {
        player.stop();
    }

}
