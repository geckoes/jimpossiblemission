/**
 * 
 */
package jimpossiblemission.model.entity.command;

import jimpossiblemission.model.entity.Player;

/**
 * Functional interface to send command to go left to Player
 * 
 * @author Filippo Taiuti
 *
 */
public class MoveLeftCommand implements CommandBehaviour
{
	/**
	 * Execute the leftward movement to player
	 * 
	 * @param player
	 */
    @Override
    public void execute(Player player)
    {
        player.goLeft();
    }

}
