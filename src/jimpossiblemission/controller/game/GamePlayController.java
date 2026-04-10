package jimpossiblemission.controller.game;

import jimpossiblemission.model.entity.Player;
import jimpossiblemission.model.entity.command.InputFactory;
import jimpossiblemission.model.entity.command.InputHandler;

/**
 * Game Controller move the player in the game. It uses a Command Pattern to
 * invoke player move using player's strategy movement.
 * 
 * @author Filippo Taiuti
 *
 */
public abstract class GamePlayController
{
    protected InputHandler inputHandler;
    protected Player player;

    /**
     * Constructor of GamePlayController
     * 
     * @param player
     */
    public GamePlayController(Player player)
    {
        this.player = player;
        inputHandler = InputFactory.createInput();
    }
}
