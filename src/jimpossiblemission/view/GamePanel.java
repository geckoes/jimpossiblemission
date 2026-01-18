package jimpossiblemission.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import jimpossiblemission.controller.KeyboardController;
import jimpossiblemission.model.game.Player;

/**
 * @author Filippo Taiuti
 *
 */
@SuppressWarnings("deprecation")
public class GamePanel extends JPanel implements Observer
{
    private static final long serialVersionUID = 1L;

    KeyboardController keyH = new KeyboardController();
    Thread gameThread;

    DecoratorPlayer decorationPlayer;

    public GamePanel(Navigator navigator)
    {
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void addPlayer(Player player) throws IOException
    {
        player.setController(keyH);
        decorationPlayer = new DecoratorPlayer(player, "hitboxes.csv", "/Sprites/Player/Running/");
        // decorationPlayer.addObserver(player);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        decorationPlayer.draw(g2);
        g2.dispose();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        repaint();
    }

}
