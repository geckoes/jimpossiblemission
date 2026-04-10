/**
 * 
 */
package jimpossiblemission.model.entity.command;

import jimpossiblemission.model.entity.Player;

/**
 * 
 * Functional interface to send command to go up to Player
 * 
 * 
 * @author Filippo Taiuti
 *
 */
public class UpCommand implements CommandBehaviour
{
	/**
	 * Ecevutes the upward movement to player
	 * 
	 * @param player
	 */
    @Override
    public void execute(Player player)
    {
        player.goUp();
    }

}
