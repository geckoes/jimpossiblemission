package impossiblemission.controller;

import java.awt.Canvas;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import impossiblemission.model.Game;
import impossiblemission.model.ImpossibleMission;
import impossiblemission.view.View;

/**
 * The Impossible Mission view controller.
 *
 * @author Filippo Taiuti
 * @version 1.0
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

        view.menu().play().addActionListener(e ->
        {
            Game game = new Game();

            game.addObserver(model);
            game.addObserver(view.play());
            game.addObserver(view.play().canvas());
            game.start();

            this.game = Optional.of(game);
            timer = Optional.of(scheduler.scheduleAtFixedRate(() -> game.update(), 1, 1, TimeUnit.SECONDS));
        });

        view.play().end().addActionListener(e ->
        {
            timer.ifPresent(t -> t.cancel(true));
            game.ifPresent(Game::end);
        });

        view.play().canvas().addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                game.ifPresent(game ->
                {
                    Canvas canvas = view.play().canvas();

                    int x = (e.getX() - canvas.getWidth() / 2 + 5 * Canvas.SCALE) / 30;
                    int y = (e.getY() - canvas.getHeight() / 2 + 5 * Canvas.SCALE) / 30;

                    switch (e.getButton()) {
                        case MouseEvent.BUTTON1 -> game.tiles[y * 10 + x].reveal();
                        case MouseEvent.BUTTON3 -> game.tiles[y * 10 + x].flag();
                        default -> {
                        }
                    }
                });
            }
        });
    }

}
