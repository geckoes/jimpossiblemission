package jimpossiblemission.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jimpossiblemission.model.User;

/**
 * The Menu class is used to start an Impossible Mission game.
 *
 * @author Filippo Taiuti
 */
@SuppressWarnings("deprecation")
public class MenuPanel extends JPanel
{

    private static final long serialVersionUID = 1L;

    private JButton btn1, btn2, btn3, btn4;

    /**
     * Class constructor specifying the navigator used to change the screen of the
     * game.
     *
     * @param user chosen
     */
    public MenuPanel(User user)
    {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLUE);

        JLabel title = new JLabel("IMPOSSIBLE MISSION");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());
        add(title);
        add(Box.createVerticalStrut(50));

        btn1 = new JButton("New Game");
        btn1.setFont(new Font("Arial", Font.PLAIN, 18));
        btn1.setMaximumSize(new Dimension(200, 50));
        btn1.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(btn1);
        add(Box.createVerticalStrut(20));

        btn2 = new JButton("Profile Players");
        btn2.setFont(new Font("Arial", Font.PLAIN, 18));
        btn2.setMaximumSize(new Dimension(200, 50));
        btn2.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(btn2);
        add(Box.createVerticalStrut(20));
        btn3 = new JButton("Statistics Profile");
        btn3.setFont(new Font("Arial", Font.PLAIN, 18));
        btn3.setMaximumSize(new Dimension(200, 50));
        btn3.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(btn3);
        add(Box.createVerticalStrut(20));
        btn4 = new JButton("Ranking Profiles");
        btn4.setFont(new Font("Arial", Font.PLAIN, 18));
        btn4.setMaximumSize(new Dimension(200, 50));
        btn4.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(btn4);
        add(Box.createVerticalGlue());

    }

    /**
     * Add listener to start the game menu
     * 
     * @param listener ActionListener
     */
    public void addStartGameListener(ActionListener listener)
    {
        btn1.addActionListener(listener);
    }

    /**
     * Add listener to profile list menu
     * 
     * @param listener ActionListener
     */
    public void addProfileListListener(ActionListener listener)
    {
        btn2.addActionListener(listener);
    }

    /**
     * Add listener to statistics menu
     * 
     * @param listener ActionListener
     */
    public void addStatisticsListener(ActionListener listener)
    {
        btn3.addActionListener(listener);
    }

    /**
     * Add listener to ranking menu
     * 
     * @param listener ActionListener
     */
    public void addRankingListener(ActionListener listener)
    {
        btn4.addActionListener(listener);
    }

    public void enableBtns(boolean enabled)
    {
        btn1.setEnabled(enabled);
        btn3.setEnabled(enabled);
    }

}
