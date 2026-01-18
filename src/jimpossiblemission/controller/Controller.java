package jimpossiblemission.controller;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import jimpossiblemission.model.Game;
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
    private Optional<Game> game;

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
            Game game = new Game();

            game.addObserver(model);
            game.addObserver(view.playMine());
            game.addObserver(view.play());
            game.start();

            this.game = Optional.of(game);
            timer = Optional.of(scheduler.scheduleAtFixedRate(() -> game.update(), 1, 1, TimeUnit.SECONDS));
        });

        view.playMine().end().addActionListener(e ->
        {
            timer.ifPresent(t -> t.cancel(true));
            game.ifPresent(Game::end);
        });

        view.menu().play().addActionListener(e ->
        {

            System.out.println("Go go go");
        });

    }

}
