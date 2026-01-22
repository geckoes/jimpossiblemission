package jimpossiblemission.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jimpossiblemission.model.game.Platform;
import jimpossiblemission.model.game.Player;

/**
 * @author Filippo Taiuti
 *
 */
@SuppressWarnings("deprecation")
public class GamePanel extends JPanel implements Observer
{
    private static final long serialVersionUID = 1L;

    Thread gameThread;

    DecoratorPlayer decoratorPlayer;
    DecoratorPlatform decoratorPlatform;

    Navigator navigator;

    Canvas canvas;

    private JLabel time, flags, mines;
    private JButton end;

    public GamePanel(Navigator navigator)
    {
        setLayout(new BorderLayout());

        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        this.navigator = navigator;
        add(new JPanel(new GridBagLayout())
        {
            {
                setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
                GridBagConstraints constraints = new GridBagConstraints();

                constraints.weightx = 1;
                constraints.weighty = 1;
                constraints.anchor = GridBagConstraints.LINE_START;

                add(new JPanel(new GridLayout(1, 3, 10, 10))
                {
                    {
                        add(time = Factory.label("time: 0s"));
                        add(mines = Factory.label("mines: 0"));
                        add(flags = Factory.label("flags: 0"));
                    }
                }, constraints);

                constraints.gridx = 1;
                constraints.fill = GridBagConstraints.BOTH;

                add(new JPanel(), constraints);

                constraints.anchor = GridBagConstraints.LINE_END;
                constraints.fill = GridBagConstraints.NONE;
                constraints.gridx = 2;

                end = new JButton("end");
                end.addActionListener(e -> navigator.navigate(Screen.Menu));
                add(end, constraints);
            }
        }, BorderLayout.NORTH);
        add(canvas = new Canvas(), BorderLayout.CENTER);

    }

    public void addPlayer(Player player) throws IOException
    {
        decoratorPlayer = new DecoratorPlayer(player, "hitboxes.csv", "/Sprites/Player/Running/");
        // decorationPlayer.addObserver(player);
    }

    public void addPlatform(Platform platform) throws IOException
    {
        decoratorPlatform = new DecoratorPlatform(platform, "hitboxes.csv", "/Sprites/Levels/LevelTiles/platform/");
        // decorationPlayer.addObserver(player);
        decoratorPlayer.addObserver(decoratorPlatform);
        
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (decoratorPlayer != null)
            decoratorPlayer.draw(g2);
        if (decoratorPlatform != null)
            decoratorPlatform.draw(g2);
        g2.dispose();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        repaint();
    }

    /**
     * @return
     */
    public Canvas canvas()
    {
        // TODO Auto-generated method stub
        return canvas;
    }

}
