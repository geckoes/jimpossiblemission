package jimpossiblemission.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jimpossiblemission.model.User;

/**
 * Statistics Panel shows the statistics of selected profile
 * 
 * @author Filippo Taiuti
 *
 */
public class StatisticPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JLabel nickName, gamesPlayed, gamesWon, gamesLost, levelCompleted, timePlayed;
    private JButton menuBtn;

    /**
     * Constructor add user information in view
     *
     * @param user to show
     */
    public StatisticPanel(User user)
    {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.BLUE);

        JPanel statistics = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        nickName = new JLabel();
        nickName.setFont(new Font("Arial", Font.BOLD, 36));
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.PAGE_START;
        c.insets = new Insets(0, 0, 20, 0);
        statistics.add(nickName, c);

        gamesPlayed = new JLabel();
        gamesPlayed.setFont(new Font("Arial", Font.BOLD, 24));
        c.gridx = 1;
        c.gridy = 3;
        c.anchor = GridBagConstraints.CENTER;
        statistics.add(gamesPlayed, c);

        gamesWon = new JLabel();
        gamesWon.setFont(new Font("Arial", Font.BOLD, 24));
        c.gridx = 1;
        c.gridy = 4;
        c.anchor = GridBagConstraints.CENTER;
        statistics.add(gamesWon, c);

        gamesLost = new JLabel();
        gamesLost.setFont(new Font("Arial", Font.BOLD, 24));
        c.gridx = 1;
        c.gridy = 5;
        c.anchor = GridBagConstraints.CENTER;
        statistics.add(gamesLost, c);

        levelCompleted = new JLabel();
        levelCompleted.setFont(new Font("Arial", Font.BOLD, 24));
        c.gridx = 1;
        c.gridy = 6;
        c.anchor = GridBagConstraints.CENTER;
        statistics.add(levelCompleted, c);

        timePlayed = new JLabel();
        timePlayed.setFont(new Font("Arial", Font.BOLD, 24));
        c.gridx = 1;
        c.gridy = 7;
        c.anchor = GridBagConstraints.CENTER;
        statistics.add(timePlayed, c);

        add(statistics, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.BLUE);

        menuBtn = new JButton("Go to Menu");
        menuBtn.setVisible(true);
        buttonPanel.add(menuBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        nickName.setText(user.getNickname());
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(user.getAvatar()));
        nickName.setIcon(imageIcon);
        gamesPlayed.setText("Partite giocate: " + user.getGamesPlayed());
        gamesWon.setText("Partite vinte: " + user.getGamesWon());
        gamesLost.setText("Partite perse: " + user.getGamesLost());
        levelCompleted.setText("Livelli completati: " + user.getLevelsCompleted());
        timePlayed.setText("Tempo di gioco: " + user.getTimePlayed() + " minuti");

    }

    /**
     * Action to return to main menu
     * 
     * @param listener ActionListener
     */
    public void addReturnToMenuListener(ActionListener listener)
    {
        menuBtn.addActionListener(listener);
    }

}
