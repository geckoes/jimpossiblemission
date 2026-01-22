package jimpossiblemission.controller;

import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Optional;

import jimpossiblemission.model.Game;
import jimpossiblemission.model.ImpossibleMission;
import jimpossiblemission.model.game.GameModel;
import jimpossiblemission.model.game.Platform;
import jimpossiblemission.model.game.Player;
import jimpossiblemission.model.game.controllers.GamePlayController;
import jimpossiblemission.model.game.controllers.KeyboardController;
import jimpossiblemission.view.MainView;

/**
 * The Impossible Mission view controller.
 *
 * @author Filippo Taiuti
 * 
 */
public class MainController
{
    private Optional<Game> game;

    /**
     * Class constructor.
     */
    public MainController(ImpossibleMission model, MainView view)
    {
        // observer for user in menu
        model.addObserver(view.menu());
        // load data from db
        model.load();

        GamePlayController gpc = new KeyboardController();
        view.menu().play().addActionListener(e ->
        {
            GameModel gm = new GameModel();

            gm.addObserver(model);
            gm.addObserver(view.play());
            gm.addObserver(view.play().canvas());

            Player pl = new Player(10, 0, 2, 2);
            pl.setController(gpc);
            Platform platform = new Platform(5, 300);

            try
            {
                view.play().addPlayer(pl);
                view.play().addPlatform(platform);
            } catch (IOException e1)
            {
                e1.printStackTrace();
            }

            gm.addObserver(view.play());
            gm.start();

            GameController gc = new GameController(gm, view.play());
            gc.addObserver(model);
            gc.addObserver(view.play());
            gc.addObserver(pl);
            gc.startGame();

        });
        view.play().addKeyListener((KeyListener) gpc);
        view.menu().user().addActionListener(e ->
        {

        });

    }

}
