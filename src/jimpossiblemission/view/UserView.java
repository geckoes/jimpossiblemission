package jimpossiblemission.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import jimpossiblemission.model.User;

@SuppressWarnings("deprecation")
public class UserView extends JPanel implements Observer
{
    private JLabel games, victories;
    private JButton play;

    /**
     * Class constructor specifying the navigator used to change the screen of the
     * game.
     *
     * @param navigator the navigator used to change the screen of the game
     */
    UserView(Navigator navigator)
    {
        super(new GridBagLayout());

        add(new JPanel(new GridBagLayout())
        {
            {
                GridBagConstraints constraints = new GridBagConstraints();

                constraints.weightx = 1;
                constraints.weighty = 1;
                constraints.insets.bottom = 20;

                add(new JLabel("Impossible Mission")
                {
                    {
                        setFont(getFont().deriveFont(35f));
                        setHorizontalAlignment(SwingConstants.CENTER);
                        setForeground(Color.BLACK);
                    }
                }, constraints);

                constraints.gridy = 1;

                add(new JPanel(new GridLayout(3, 1, 10, 10))
                {
                    {
                        add(play = new JButton("Menu")
                        {
                            {
                                addActionListener(e -> navigator.navigate(Screen.Menu));
                            }
                        });

                        add(games = Factory.label("games played: 0"));
                        add(victories = Factory.label("games won: 0"));
                    }
                }, constraints);
            }
        });
    }

    /**
     * Returns the play button.
     *
     * @return the play button
     */
    public JButton play()
    {
        return play;
    }

    /**
     * Updates when notified by ImpossibleMission.
     *
     * @param o   the ImpossibleMission
     * @param arg not relevant
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if (arg instanceof List)
            if (((List) arg).size() > 0 && (((List) arg).get(0) instanceof User))
            {
                for (User user : (List<User>) arg)
                {
                    add(new JLabel(user.getNickname()));
                    revalidate();
                    repaint();
//                    pack();
                }
            }
    }

}
