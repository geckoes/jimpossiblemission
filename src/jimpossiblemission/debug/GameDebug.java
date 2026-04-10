package jimpossiblemission.debug;

import javax.swing.JFrame;

import jimpossiblemission.controller.game.GameController;
import jimpossiblemission.model.game.Game;
import jimpossiblemission.view.GameMap;

/**
 * Dev Env
 * @author Filippo Taiuti
 *
 */
public class GameDebug
{

    public GameDebug()
    {
        JFrame jFrameDegub = new JFrame();
        jFrameDegub.setTitle("Impossible Mission");
        jFrameDegub.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrameDegub.setLocationRelativeTo(null);

        jFrameDegub.setSize(1024, 768);
        jFrameDegub.setVisible(true);

        GameMap gameMap = new GameMap();
        gameMap.setPreferredSize(jFrameDegub.getSize());

        Game game = new Game();
        GameController gc = new GameController(game, gameMap);

        jFrameDegub.add(gameMap);
        jFrameDegub.validate();
        jFrameDegub.repaint();
    }

    public static void main(String[] args)
    {
        new GameDebug();
    }
}
