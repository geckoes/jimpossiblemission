package jimpossiblemission.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 * Vista principale per il progetto Impossible Mission
 * 
 * @author Filippo Taiuti
 */

@SuppressWarnings("deprecation")
public class MainView extends JFrame implements Observer
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
    private JPanel userPanel;
    private Menu menu;
    private GamePanel play;
    private UserView userView;

    /**
     * Class constructor. Call
     */
    public MainView()
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
                add(userView = new UserView(navigator), Screen.User.name());
                add(menu = new Menu(navigator), Screen.Menu.name());
                add(play = new GamePanel(navigator), Screen.GameMine.name());

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
                                addActionListener(e -> navigator.navigate(Screen.Menu));
                            }
                        });
                    }
                }, Screen.Victory.name());
            }
        });

        setSize(740, 500);
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
    public GamePanel play()
    {
        return play;
    }

    /**
     * Returns the user panel
     * 
     * @return the user panel
     */
    public UserView userView()
    {
        return userView;
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

/**
 * The Factory class is used to create Swing components.
 *
 * @author Filippo Taiuti
 * @version 1.0
 */
final class Factory
{
    private Factory()
    {
    }

    /**
     * Creates a simple compound border using the specified color.
     *
     * @param color the color of the border
     * @return the simple compound border
     */
    static Border border(Color color)
    {
        return BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(color),
                BorderFactory.createEmptyBorder(10, 15, 10, 15));
    }

    /**
     * Creates a simple JLabel with the specified text.
     *
     * @param text the text of the label
     * @return the simple JLabel
     */
    static JLabel label(String text)
    {
        return new JLabel(text)
        {
            {
                setHorizontalAlignment(SwingConstants.CENTER);
                setOpaque(true);
                setBackground(Color.WHITE);
                setBorder(Factory.border(Color.BLACK));
            }
        };
    }
}
