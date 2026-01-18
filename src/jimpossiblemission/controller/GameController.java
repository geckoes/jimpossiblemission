/**
 * 
 */
package jimpossiblemission.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.Timer;

import jimpossiblemission.model.ImpossibleMission;
import jimpossiblemission.view.GamePanel;

/**
 * @author Filippo Taiuti
 *
 */
@SuppressWarnings("deprecation")
public class GameController extends Observable
{
    // FPS
    final int FPS = 60;
    final int MILLISECONDS = 1000;

    Timer gameTimer;

    /**
     * @param model
     * @param gp
     */
    public GameController(ImpossibleMission model, GamePanel gp)
    {
        ActionListener gamePerformer = new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                setChanged();
                notifyObservers("test");
            }
        };

        gameTimer = new Timer(MILLISECONDS / FPS, gamePerformer);
    }

    public void startGame()
    {
        gameTimer.start();
    }

    public void stopGame()
    {
        if (gameTimer != null)
            gameTimer.stop();
    }

}
