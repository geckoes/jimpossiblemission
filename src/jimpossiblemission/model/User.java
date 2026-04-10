package jimpossiblemission.model;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import jimpossiblemission.model.entity.Player;
import jimpossiblemission.model.game.Game;

/**
 * User Profile
 * 
 * @author Filippo Taiuti
 */
@SuppressWarnings("deprecation")
public class User extends Observable implements Serializable, Observer
{
    private static final long serialVersionUID = 1L;

    private String nickname;
    private String avatar;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int timePlayed;
    private int levelsCompleted;

    /**
     * Constructor of user
     * 
     * @param nickname
     * @param avatar
     */
    public User(String nickname, String avatar)
    {
        this.nickname = nickname;
        this.avatar = avatar;
        gamesPlayed = 0;
        gamesWon = 0;
        gamesLost = 0;
        timePlayed = 0;
        levelsCompleted = 0;
    }

    /**
     * Returns the nickname of the user
     * 
     * @return string nickname
     */
    public String getNickname()
    {
        return nickname;
    }

    /**
     * Set the nickname of the user
     * 
     * @param nickname
     */
    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    /**
     * Returns the avatar of the user
     * 
     * @return string avatar
     */
    public String getAvatar()
    {
        return avatar;
    }

    /**
     * Set the avatar of the user
     * 
     * @param avatar
     */
    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    /**
     * Returns how many games the User has played
     * 
     * @return int number of game
     */
    public int getGamesPlayed()
    {
        return gamesPlayed;
    }

    /**
     * Adds one player game
     * 
     */
    public void addGamePlayed()
    {
        gamesPlayed += 1;
        setChanged();
        notifyObservers();
    }

    /**
     * Returns how many games won the User has played
     * 
     * @return int number of game won
     */
    public int getGamesWon()
    {
        return gamesWon;
    }

    /**
     * Adds one won game to user
     * 
     */
    public void addGamesWon()
    {
        gamesWon += 1;
        setChanged();
        notifyObservers();
    }

    /**
     * Returns how many games lost the User has played
     * 
     * @return int number of game lost
     */
    public int getGamesLost()
    {
        return gamesLost;
    }

    /**
     * Adds one lost game to user
     * 
     */
    public void addGamesLost()
    {
        gamesLost += 1;
        setChanged();
        notifyObservers();
    }

    /**
     * Returns how many minutes the User has played
     * 
     * @return int minutes played
     */
    public int getTimePlayed()
    {
        return timePlayed;
    }

    /**
     * Adds minutes played to user
     * 
     * @param timePlayed
     */
    public void addTimePlayed(int timePlayed)
    {
        this.timePlayed = timePlayed;
        setChanged();
        notifyObservers();
    }

    /**
     * Returns how many levels the User has completed
     * 
     * @return int number of completed levels
     */
    public int getLevelsCompleted()
    {
        return levelsCompleted;
    }

    /**
     * Add one completed level to user
     * 
     */
    public void addCompletedLevel()
    {
        levelsCompleted += 1;
        setChanged();
        notifyObservers();
    }

    /**
     * Last update before quit the game
     * 
     */
    public void lastUpdateGamesPlayed()
    {
        gamesLost = gamesPlayed - gamesWon;
        setChanged();
        notifyObservers();
        deleteObservers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return new StringBuilder().append("Nick name: ").append(nickname).append(" - ").append("games played: ")
                .append(gamesPlayed).append(" - ").append("games won: ").append(gamesWon).append(" - ")
                .append("time played: ").append(timePlayed).toString();
    }

    /**
     * method called when an observed object notifies a change of state
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if (o == null && arg == null)
            return;
        if (o instanceof Player)
        {

        } else if (o instanceof Game)
        {
            Game gt = (Game) o;
            switch (gt.getState()) {
                case Game.GameState.START:
                    gamesPlayed += 1;
                    break;
                case Game.GameState.VICTORY:
                    gamesWon += 1;
                    timePlayed += gt.getTimePlayed();
                    levelsCompleted += gt.getLevelcompleted();
                    break;
                case Game.GameState.GAMEOVER:
                    gamesLost += 1;
                    timePlayed += gt.getTimePlayed();
                    levelsCompleted += gt.getLevelcompleted();
                    break;
                case Game.GameState.PAUSE:
                    timePlayed += gt.getTimePlayed();
                    break;

                default:
                    break;
            }
            setChanged();
            notifyObservers();
        }
    }

}
