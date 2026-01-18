package jimpossiblemission.controller;

import java.io.IOException;
import java.util.Observer;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import jimpossiblemission.model.game.GameModel;
import jimpossiblemission.model.game.Player;
import jimpossiblemission.model.ImpossibleMission;
import jimpossiblemission.view.View;

/**
 * The Impossible Mission view controller.
 *
 * @author Filippo Taiuti
 * 
 */
@SuppressWarnings("deprecation")
public class Controller
{
    private Optional<ScheduledFuture<?>> timer;
    private ScheduledExecutorService scheduler;
    private Optional<jimpossiblemission.model.Game> gameMine;

    /**
     * Class constructor.
     */
    public Controller(ImpossibleMission model, View view)
    {
        scheduler = Executors.newScheduledThreadPool(1);
        model.addObserver(view.menu());
        model.load();

        view.menu().playMine().addActionListener(e ->
        {
        	jimpossiblemission.model.Game game = new jimpossiblemission.model.Game();

            game.addObserver(model);
            game.addObserver(view.playMine());
            game.addObserver(view.play());
            game.start();

            gameMine = Optional.of(game);
            timer = Optional.of(scheduler.scheduleAtFixedRate(() -> game.update(), 1, 1, TimeUnit.SECONDS));
        });

        view.playMine().end().addActionListener(e ->
        {
            timer.ifPresent(t -> t.cancel(true));
            gameMine.ifPresent(jimpossiblemission.model.Game::end);
        });

        view.menu().play().addActionListener(e ->
        {
        	Player pl = new Player(0, 0, 0, 0, 0);
        	try {
				view.play().addPlayer(pl);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	KeyboardController keyb = new KeyboardController();
//        	pl.setController(keyb);
        	view.play().addKeyListener(keyb);
        	GameModel gm = new GameModel();
        	GameController gc = new GameController(gm, view.play());
            gc.addObserver(view.play());
            gc.addObserver(pl);
            gc.startGame();
            
        });

    }

}
