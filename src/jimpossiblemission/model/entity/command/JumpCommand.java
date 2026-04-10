package jimpossiblemission.model.entity.command;

import jimpossiblemission.model.entity.Player;

/**
 * Functional interface to send command to jump to Player
 * 
 * @author Filippo Taiuti
 *
 */
public class JumpCommand implements CommandBehaviour
{
	/**
	 * Executes the jump movement to player
	 * 
	 * @param player
	 */
    @Override
    public void execute(Player player)
    {
        player.jump();
    }

}
