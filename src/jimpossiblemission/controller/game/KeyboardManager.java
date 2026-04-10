package jimpossiblemission.controller.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import jimpossiblemission.model.entity.Player;
import jimpossiblemission.model.entity.command.InputAction;

/**
 * Public class to create Game Controller using Keyboard. It maps some keys to
 * move the player in game
 * 
 * @author Filippo Taiuti
 *
 */
public class KeyboardManager extends GamePlayController implements KeyListener
{
    /**
     * Constructor to create Game Controller using Keyboard
     * 
     * @param player
     */
    public KeyboardManager(Player player)
    {
        super(player);
    }

    /**
     * Key typed event
     * 
     * @param e KeyEvent
     */
    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    /**
     * Key pressed event
     * 
     * @param e KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP)
            inputHandler.handleAction(InputAction.UP, player);
        if (code == KeyEvent.VK_DOWN)
            inputHandler.handleAction(InputAction.DOWN, player);
        if (code == KeyEvent.VK_LEFT)
            inputHandler.handleAction(InputAction.MOVELEFT, player);
        if (code == KeyEvent.VK_RIGHT)
            inputHandler.handleAction(InputAction.MOVERIGHT, player);
        if (code == KeyEvent.VK_SPACE)
            inputHandler.handleAction(InputAction.JUMP, player);
    }

    /**
     * Key release event
     * 
     * @param e KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
        inputHandler.handleAction(InputAction.IDLE, player);
    }

}
