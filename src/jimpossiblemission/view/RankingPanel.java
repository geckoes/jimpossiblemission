package jimpossiblemission.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import jimpossiblemission.model.User;

/**
 * Ranking Panel shows the ranking of the users
 * 
 * @author Filippo Taiuti
 *
 */
public class RankingPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JList<User> profileList;
    private DefaultListModel<User> listModel;

    private JButton menuBtn;
    private JLabel nickName, gamesPlayed, gamesWon, gamesLost, levelCompleted, timePlayed;

    /**
     * Constructor add user information in view
     *
     * @param users to show
     */
    public RankingPanel(List<User> users)
    {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.BLUE);

        JLabel title = new JLabel("Ranking Profiles", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();

        JPanel JPUsers = new JPanel();
        JPUsers.setLayout(new BoxLayout(JPUsers, BoxLayout.Y_AXIS));
        JPUsers.setBackground(Color.GREEN);

        profileList = new JList<>(listModel);
        profileList.setFont(new Font("Monospaced", Font.PLAIN, 16));
        profileList.setCellRenderer(new RankingRenderer());

        JScrollPane scrollPane = new JScrollPane(profileList);

        for (User profile : users)
        {
            listModel.addElement(profile);
        }

        JPUsers.add(scrollPane);
        add(JPUsers, BorderLayout.CENTER);

        menuBtn = new JButton("Go to Menu");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.BLUE);

        buttonPanel.add(menuBtn);
        add(buttonPanel, BorderLayout.SOUTH);
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
