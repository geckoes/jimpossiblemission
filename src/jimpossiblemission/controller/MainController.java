package jimpossiblemission.controller;

import java.io.IOException;
import java.util.Optional;

import jimpossiblemission.model.Game;
import jimpossiblemission.model.ImpossibleMission;
import jimpossiblemission.model.game.GameModel;
import jimpossiblemission.model.game.Player;
import jimpossiblemission.view.MainView;

/**
 * The Impossible Mission view controller.
 *
 * @author Filippo Taiuti
 * 
 */
@SuppressWarnings("deprecation")
public class MainController
{
    private Optional<jimpossiblemission.model.Game> gameMine;
    private Optional<Game> game;

    /**
     * Class constructor.
     */
    public MainController(ImpossibleMission model, MainView view)
    {
        model.addObserver(view.menu());
        model.load();

        view.menu().play().addActionListener(e ->
        {
            GameModel gm = new GameModel();

            gm.addObserver(model);
            gm.addObserver(view.play());
            gm.addObserver(view.play().canvas());
            Player pl = new Player(0, 0, 0, 0);

            try
            {
                view.play().addPlayer(pl);
            } catch (IOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
//            gm.addObserver(view.play());
            gm.start();

//            GameController gc = new GameController(gm, view.play());
//            gc.addObserver(model);
//            gc.addObserver(view.play());
//            gc.startGame();

        });
        view.play().addKeyListener(new KeyboardController());
        view.menu().user().addActionListener(e ->
        {

        });
//        view.play().canvas().addMouseListener(new MouseAdapter()
//        {
//            @Override
//            public void mouseClicked(MouseEvent e)
//            {
//                game.ifPresent(game ->
//                {
//                    Canvas canvas = view.play().canvas();
//
//                    int x = (e.getX() - canvas.getWidth() / 2 + 5 * Canvas.SCALE) / 30;
//                    int y = (e.getY() - canvas.getHeight() / 2 + 5 * Canvas.SCALE) / 30;
//
//                    switch (e.getButton()) {
//                        case MouseEvent.BUTTON1 -> game.tiles[y * 10 + x].reveal();
//                        case MouseEvent.BUTTON3 -> game.tiles[y * 10 + x].flag();
//                        default -> {
//                        }
//                    }
//                });
//            }
//        });

    }

}
