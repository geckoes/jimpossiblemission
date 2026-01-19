package jimpossiblemission.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import jimpossiblemission.model.ImpossibleMission;

/**
 * The Menu class is used to start a Minesweeper game.
 *
 * @author Cicio Ionut
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class Menu extends JPanel implements Observer
{

    private JLabel name, games, victories;
    private JButton playMine;
    private JButton play;
    private JButton user;

    private Navigator navigator;

    /**
     * Class constructor specifying the navigator used to change the screen of the
     * game.
     *
     * @param navigator the navigator used to change the screen of the game
     */
    Menu(Navigator navigator)
    {
        super(new GridBagLayout());
        this.navigator = navigator;

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

                add(new JPanel(new GridLayout(3, 1, 20, 10))
                {
                    {
                        add(play = new JButton("Play")
                        {
                            {
                                addActionListener(e -> navigator.navigate(Screen.Game));
                            }
                        });
                        add(user = new JButton("User")
                        {
                            {
                                addActionListener(e -> navigator.navigate(Screen.User));
                            }
                        });

                        add(name = Factory.label(""));
                        add(games = Factory.label("games played: "));
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
    public JButton playMine()
    {
        return playMine;
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
     * Returns the user button.
     *
     * @return the user button
     */
    public JButton user()
    {
        return user;
    }

    /**
     * Updates when notified by Impossible Mission.
     *
     * @param o   the Impossible Mission
     * @param arg not relevant
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof ImpossibleMission impossibleMission)
        {
            if (impossibleMission.getUSer().isPresent())
            {
                name.setText(impossibleMission.getUSer().get().getNickname());
                games.setText("games played: " + impossibleMission.getUSer().get().getGamesPlayed());
                victories.setText("games won: " + impossibleMission.getUSer().get().getGamesWon());
            } else
            {
                // navigator.navigate(Screen.User);

            }
        }
    }

}
