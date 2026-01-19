/**
 * 
 */
package jimpossiblemission.view;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

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
     * 
     * @throws IOException
     */
    public Play(Navigator navigator) throws IOException
    {

        setLayout(new BorderLayout());

        this.navigator = navigator;

    }

    @Override
    public void update(Observable o, Object arg)
    {
        // TODO Auto-generated method stub

    }

    /**
     * @return
     */
    public void end()
    {
        // TODO Auto-generated method stub
        return;
    }
}
