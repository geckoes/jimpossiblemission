package jimpossiblemission.model.entity.command;

/**
 * InputFactory is the design to add command moves to Player
 * 
 * @author Filippo Taiuti
 *
 */
public class InputFactory
{
    public static InputHandler createInput()
    {
        InputHandler handler = new InputHandler();
        handler.addCommand(InputAction.IDLE, new IdleCommand());
        handler.addCommand(InputAction.JUMP, new JumpCommand());
        handler.addCommand(InputAction.MOVELEFT, new MoveLeftCommand());
        handler.addCommand(InputAction.MOVERIGHT, new MoveRightCommand());
        handler.addCommand(InputAction.UP, new UpCommand());
        handler.addCommand(InputAction.DOWN, new DownCommand());

        return handler;
    }
}
