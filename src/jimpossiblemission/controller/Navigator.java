package jimpossiblemission.controller;

import java.util.Observable;

/**
 * The Navigator class is used to change view screens.
 *
 * @author Cicio Ionut, Filippo Taiuti
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class Navigator extends Observable
{
    /**
     * Enum used to navigate between Screen
     */
    public enum Screen
    {
        Menu, User, Game, Loss, Victory, Statistics, Ranking, NewUser
    }

    private Screen currentScreen = Screen.Menu;

    /**
     * Method to navigate between screens
     * 
     * @param screen
     */
    public void navigate(Screen screen)
    {
        currentScreen = screen;
        setChanged();
        notifyObservers(screen);
    }

    /**
     * Gets current screen
     * 
     * @return screen
     */
    public Screen getCurrentScreen()
    {
        return currentScreen;
    }

}
