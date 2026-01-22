/**
 * 
 */
package jimpossiblemission.model.game.controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Filippo Taiuti
 *
 */
public class KeyboardController extends GamePlayController implements KeyListener
{
    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();
        currentDirection = Direction.NONE;
        if (code == KeyEvent.VK_UP)
        {
            currentDirection = Direction.UP;
        }
        if (code == KeyEvent.VK_DOWN)
        {
            currentDirection = Direction.DOWN;
        }
        if (code == KeyEvent.VK_LEFT)
        {
            currentDirection = Direction.LEFT;
        }
        if (code == KeyEvent.VK_RIGHT)
        {
            currentDirection = Direction.RIGHT;
        }
        if (code == KeyEvent.VK_SPACE)
        {
            currentDirection = Direction.JUMP;
        }
        setChanged();
        notifyObservers(currentDirection);
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        currentDirection = Direction.NONE;
    }

    public Direction getDirection()
    {
        return currentDirection;
    }

}
