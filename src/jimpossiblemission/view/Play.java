/**
 * 
 */
package jimpossiblemission.view;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import jimpossiblemission.controller.GamePlayController;
import jimpossiblemission.controller.KeyboardController;
import jimpossiblemission.model.game.GameModel;
import jimpossiblemission.model.game.Player;

/**
 * @author Filippo Taiuti
 *
 */
public class Play extends JPanel implements Observer
{
    // SCREEN SETTINGS
    public final static int originalTileSize = 32;
    public final static int scale = 4;

    public final static int tileSize = originalTileSize * scale;
    public final static int spriteSize = originalTileSize * scale;
    public final static int screenWidth = 1600;
    public final static int screenHeight = 900;

    // PLAYER SETTINGS
    public final static int playerX = 100;
    public final static int playerY = 300;
    public final static int playerSpeed = 4;

    private Navigator navigator;

    /**
     * Class constructor.
     */
    public Play(Navigator navigator)
    {
    	
        setLayout(new BorderLayout());

        this.navigator = navigator;

        GameModel model = new GameModel();

        GamePanel gp = new GamePanel(navigator);
        GamePlayController gpController = new KeyboardController();
//    gp.addKeyListener(keyController); 
        Player pl = new Player(playerX, playerY, spriteSize, spriteSize, playerSpeed);

        add(gp);
    }

    public JPanel gamePanel()
    {
        return this;
    }

    @Override
    public void update(Observable o, Object arg)
    {
        System.out.println("test1");
        // TODO Auto-generated method stub

    }
}
