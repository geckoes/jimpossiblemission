/**
 * 
 */
package jimpossiblemission.model.entity.command;

import jimpossiblemission.model.entity.Player;

/**
 * Functional interface to send command to go right to Player
 * 
 * @author Filippo Taiuti
 *
 */
public class MoveRightCommand implements CommandBehaviour
{
	/**
	 * Executes the rightward movement to player
	 * 
	 * @param player
	 */
    @Override
    public void execute(Player player)
    {
        player.goRight();
    }

}
