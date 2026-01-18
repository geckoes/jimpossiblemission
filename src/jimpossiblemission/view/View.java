package jimpossiblemission.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * 
 * @author Filippo Taiuti
 */
@SuppressWarnings("deprecation")
public class View extends JFrame implements Observer
{
    static Font FONT = new Font("Cascadia Code", Font.PLAIN, 14);
    static String LOGO = "https://static.wikia.nocookie.net/logopedia/images/9/98/Minesweeper_1992.png/revision/latest?cb=20220716174154";

    static
    {
        UIManager.put("Label.font", FONT);
        UIManager.put("Label.foreground", Color.DARK_GRAY);
        UIManager.put("Label.background", Color.WHITE);
        UIManager.put("Button.font", FONT);
        UIManager.put("Button.foreground", new Color(224, 49, 49));
        UIManager.put("Button.background", new Color(255, 201, 201));
        UIManager.put("Button.highlight", Color.WHITE);
        UIManager.put("Button.select", Color.WHITE);
        UIManager.put("Button.focus", Color.WHITE);
        UIManager.put("Panel.background", new Color(233, 236, 239));
    }

    private JPanel deck;
    private Menu menu;
    private Play_Mine play;
    private GamePanel game;
    private UserView user;

    /**
     * Class constructor. Call
     */
    public View()
    {
        super("Impossible Mission");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try
        {
            setIconImage(ImageIO.read(new URL(LOGO)));
        } catch (Exception e)
        {
        }

        Navigator navigator = new Navigator();
        navigator.addObserver(this);

        add(deck = new JPanel(new CardLayout())
        {
            {
                add(menu = new Menu(navigator), Screen.Menu.name());
                add(play = new Play_Mine(navigator), Screen.GameMine.name());
                add(game = new GamePanel(navigator), Screen.Game.name());

                add(new JPanel(new GridBagLayout())
                {
                    {
                        setBackground(new Color(255, 201, 201));
                        JButton gameOver = new JButton("Game Over");
                        gameOver.addActionListener(e -> navigator.navigate(Screen.Menu));
                        add(gameOver);
                    }
                }, Screen.Loss.name());

                add(new JPanel(new GridBagLayout())
                {
                    {
                        setBackground(new Color(178, 242, 187));

                        add(new JButton("Victory")
                        {
                            {
                                setForeground(new Color(47, 158, 68));
                                setBackground(new Color(178, 242, 187));
                                setBorder(Factory.border(new Color(47, 158, 68)));
                                addActionListener(e -> navigator.navigate(Screen.Menu));
                            }
                        });
                    }
                }, Screen.Victory.name());
                add(user = new UserView(navigator), Screen.User.name());

            }
        });

        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Returns the menu panel.
     *
     * @return the menu panel.
     */
    public Menu menu()
    {
        return menu;
    }

    /**
     * Returns the play panel.
     *
     * @return the play panel.
     */
    public Play_Mine playMine()
    {
        return play;
    }

    /**
     * Returns the play panel.
     *
     * @return the play panel.
     */
    public GamePanel play()
    {
        return game;
    }

    /**
     * Updates when notified by a navigator.
     *
     * @param o   the navigator
     * @param arg the screen to navigate to.
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof Navigator && arg instanceof Screen screen)
            ((CardLayout) deck.getLayout()).show(deck, screen.name());

    }
}
