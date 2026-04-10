package jimpossiblemission.model.game;

import java.util.Observable;

/**
 * Game model is the model of the game played by user
 * @author Filippo Taiuti
 *
 */
@SuppressWarnings("deprecation")
public class Game extends Observable
{
    private GameState state = GameState.START;
    private int timePlayed;
    private int levelCompleted;

    /**
     * Game State enum
     */
    public enum GameState
    {
        START, PAUSE, PLAY, VICTORY, GAMEOVER
    }

    /**
     * Returns the state of the game
     *
     * @return the state of the game
     */
    public GameState getState()
    {
        return state;
    }

    /**
     * Updates the user experience.
     */
    public void update()
    {
        setChanged();
        notifyObservers();
    }

    /**
     * Notifies all listeners that the game has ended.
     * 
     * @param win boolean
     */
    public void endGame(boolean win)
    {
        state = win ? GameState.VICTORY : GameState.GAMEOVER;
        update();
        deleteObservers();
    }

    /**
     * Start the game
     */
    public void startGame()
    {
        if (state == GameState.START)
            update();
        state = GameState.PLAY;
        update();
    }

    /**
     * Notifies all listeners that the game has ended.
     */
    public void pauseGame()
    {
        state = GameState.PAUSE;
        update();
    }

    /**
     * Records how many minutes the user played
     * 
     * @param minutes played
     */
    public void addTimePlayed(int minutes)
    {
        timePlayed += Math.max(1, minutes);
        update();
    }

    /**
     * Returns time played in minutes
     * 
     * @return int minutes played
     */
    public int getTimePlayed()
    {
        return timePlayed;
    }

    /**
     * Records how many levels the user has completed
     * 
     * @param levelCompleted
     */
    public void addLevelCompleted(int levelCompleted)
    {
        this.levelCompleted = levelCompleted;
    }

    /**
     * Returns the number of level completed in the last game
     * 
     * @return int number of level completed
     */
    public int getLevelcompleted()
    {
        return levelCompleted;
    }

}
