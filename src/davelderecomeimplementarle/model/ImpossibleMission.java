package impossiblemission.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;

public class ImpossibleMission extends Observable implements Observer
{
    private static final String DATABASE = "games.db";
    private int games = 0, victories = 0;

    /**
     * Class constructor. Call
     * 
     * <pre>
     * ImpossibleMission::load
     * </pre>
     * 
     * after constructing the object to load stats.
     */
    public ImpossibleMission()
    {
    }

    /**
     * Loads stats from file and notifies observers.
     */
    public void load()
    {
        try
        {
            ObjectInputStream stream = new ObjectInputStream(new FileInputStream(DATABASE));
            this.games = (Integer) stream.readObject();
            this.victories = (Integer) stream.readObject();
            stream.close();
        } catch (IOException | ClassNotFoundException e)
        {
        }

        setChanged();
        notifyObservers();
    }

    /**
     * Returns the number of games played.
     *
     * @return the number of games played
     */
    public int games()
    {
        return games;
    }

    /**
     * Returns the number of victories.
     *
     * @return the number of victories
     */
    public int victories()
    {
        return victories;
    }

    /**
     * Updates when notified by a game.
     *
     * @param o   the game
     * @param arg the result of the game
     */
    public void update(Observable o, Object arg)
    {
        if (!(o instanceof Game && arg instanceof Game.Result result))
            return;

        games++;
        if (result == Game.Result.Victory)
            victories++;

        try
        {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(DATABASE));
            stream.writeObject(games);
            stream.writeObject(victories);
            stream.close();
        } catch (IOException e)
        {
        }

        setChanged();
        notifyObservers();
    }

}
