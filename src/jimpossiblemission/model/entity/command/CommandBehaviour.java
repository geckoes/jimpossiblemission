
package jimpossiblemission.model.entity.command;

import jimpossiblemission.model.entity.Player;

/**
 * Functional interface to get commands to GameObject
 * 
 * @author Filippo Taiuti
 *
 */
@FunctionalInterface
public interface CommandBehaviour
{
    /**
     * Executes the command to move the player
     * 
     * @param player
     */
    void execute(Player player);
}
