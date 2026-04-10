package jimpossiblemission.model.entity.command;

import java.util.HashMap;
import java.util.Map;

import jimpossiblemission.model.entity.Player;

/**
 * Maps input action to their corresponding command behaviours
 * and sends them to the player
 * 
 * @author Filippo Taiuti
 *
 */
public class InputHandler
{
    private Map<InputAction, CommandBehaviour> commands = new HashMap<InputAction, CommandBehaviour>();

    public void addCommand(InputAction action, CommandBehaviour command)
    {
        commands.put(action, command);
    }

    public void handleAction(InputAction action, Player player)
    {
        CommandBehaviour cmd = commands.get(action);
        if (cmd != null)
        {
            cmd.execute(player);
        }
    }

}
