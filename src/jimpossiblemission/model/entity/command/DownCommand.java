package jimpossiblemission.model.entity.command;

import jimpossiblemission.model.entity.Player;

/**
 * Functional interface to send command to go down to Player
 * 
 * @author Filippo Taiuti
 *
 */
public class DownCommand implements CommandBehaviour
{
	/**
     * Executes the downward movement to player
	 * 
	 * @param player
	 */
    @Override
    public void execute(Player player)
    {
        player.goDown();
    }

}
