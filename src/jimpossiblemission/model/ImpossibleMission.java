package jimpossiblemission.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

/**
 * 
 * 
 * @author Filippo Taiuti
 *
 */
@SuppressWarnings("deprecation")
public class ImpossibleMission extends Observable implements Observer
{
    private static final String DATABASE = "games.db";
    private User user;

    /**
     * Loads user stats from file and notifies observers.
     */
    @SuppressWarnings("unchecked")
    public void load()
    {
        try
        {
            ObjectInputStream stream = new ObjectInputStream(new FileInputStream(DATABASE));
            user = (User) stream.readObject();
            stream.close();
        } catch (IOException | ClassNotFoundException e)
        {
            System.out.println(e.getMessage());

        }

        setChanged();
        notifyObservers();
    }

    /**
     * Returns the number of games played.
     *
     * @return the number of games played
     */
    public Optional<User> getUSer()
    {
        return Optional.ofNullable(user);
    }

    /**
     * Updates when notified by a game.
     *
     * @param o   the game
     * @param arg the result of the game
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if (!(o instanceof Game && arg instanceof Game.Result result))
            return;

        if (user == null)
        {
            user = new User("John Doe", "avatar0");
        }
        user.addGamePlayed();
        if (result == Game.Result.Victory)
            user.addGamesWon();
        else
            user.addGamesLost();

        try
        {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(DATABASE));
            stream.writeObject(user);
            stream.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        setChanged();
        notifyObservers();
    }

}
