package jimpossiblemission.model.game;

import java.time.Duration;
import java.util.Observable;
import java.util.Random;

/**
 * 
 * @author Filippo Taiuti
 *
 */
public class Game extends Observable
{
    public enum Result
    {
        Loss, Victory, Terminated
    }

    private int flags = 0;
    private Duration duration = Duration.ofSeconds(0);

    /**
     * Class constructor.
     */
    public Game()
    {
        Random random = new Random();

    }

    /**
     * Returns the number of flags placed on the tiles.
     *
     * @return the number of flags placed on the tiles
     */
    public int flags()
    {
        return flags;
    }

    /**
     * Returns the duration of the game.
     *
     * @return the duration of the game
     */
    public Duration duration()
    {
        return duration;
    }

    /**
     * Notifies all listeners that the game has started (in order to reset).
     */
    public void start()
    {
        setChanged();
        notifyObservers(this);
    }

    /**
     * Updates the duration of the game.
     */
    public void update()
    {
        setChanged();
        notifyObservers(duration = duration.plusSeconds(1));
    }

    /**
     * Notifies all listeners that the game has ended.
     */
    public void end()
    {
        setChanged();
        deleteObservers();
    }

}
