package jimpossiblemission.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jimpossiblemission.audio.AudioManager;
import jimpossiblemission.controller.Navigator.Screen;
import jimpossiblemission.controller.game.GameController;
import jimpossiblemission.model.ImpossibleMission;
import jimpossiblemission.model.User;
import jimpossiblemission.model.game.Game;
import jimpossiblemission.view.GameMap;
import jimpossiblemission.view.MenuPanel;
import jimpossiblemission.view.NewUserPanel;
import jimpossiblemission.view.ProfilePanel;
import jimpossiblemission.view.RankingPanel;
import jimpossiblemission.view.StatisticPanel;

/**
 * The Impossible Mission controller.
 *
 * @author Filippo Taiuti
 * 
 */
@SuppressWarnings("deprecation")
public class ImpossibleMissionController implements Observer
{
    private Navigator navModel;
    private ImpossibleMission model;
    private JPanel currentView;
    private MenuPanel menuPanel;
    private JFrame mainFrame;

    private List<User> users;
    private User currentUser;

    /**
     * Class constructor
     * 
     * @param imModel
     * @param view
     */
    public ImpossibleMissionController(ImpossibleMission imModel, JFrame view)
    {
        navModel = new Navigator();
        // observer for choice the user
        model = imModel;
        mainFrame = view;

        navModel.addObserver(this);
        users = imModel.loadUsers();

        if (users.isEmpty())
            navModel.navigate(Screen.NewUser);
        else
            navModel.navigate(Screen.Menu);

        // last update before exit application
        view.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                if (currentUser != null)
                    currentUser.lastUpdateGamesPlayed();
                view.dispose();
                AudioManager.getInstance().dispose();
                System.exit(0);
            }

        });
    }

    /**
     * method called when an observed object notifies a change of state
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if (arg instanceof Screen)
        {
            // Remove JPanel from JFrame
            if (currentView instanceof JPanel)
                mainFrame.remove((JPanel) currentView);

            // create new view
            switchNewView((Screen) arg);
            mainFrame.validate();
            mainFrame.repaint();
        }
    }

    /**
     * Method used to switch among JPanels
     * 
     * @param screen type of screen
     */
    private void switchNewView(Screen screen)
    {
        switch (screen) {
            case NewUser:
                NewUserPanel newUserPanel = new NewUserPanel();
                currentView = newUserPanel;

                newUserPanel.addNewUserListener(e ->
                {
                    String name = newUserPanel.getNickName();
                    String path = newUserPanel.getAvatarPath();

                    // create a new user
                    User newUser = new User(name, path);
                    users.add(newUser);
                    selectProfile(newUser);

                    navModel.navigate(Screen.Menu);
                });
                newUserPanel.setSize(mainFrame.getSize());
                mainFrame.add(newUserPanel);
                break;
            case User:
                ProfilePanel profilePanel = new ProfilePanel(users);
                currentView = profilePanel;
                profilePanel.addCreateNewProfileListener(e -> navModel.navigate(Screen.NewUser));
                profilePanel.addSelectProfileListener(e ->
                {
                    User user = profilePanel.getSelectedProfile();
                    if (user != null)
                        navModel.navigate(Screen.Menu);

                });

                profilePanel.addProfileListListener(e ->
                {
                    User user = profilePanel.getSelectedProfile();
                    selectProfile(user);
                });
                mainFrame.add(profilePanel);
                break;
            case Menu:
                menuPanel = new MenuPanel(currentUser);
                currentView = menuPanel;

                menuPanel.addStartGameListener(e ->
                {
                    navModel.navigate(Screen.Game);
                });
                menuPanel.addProfileListListener(e -> navModel.navigate(Screen.User));
                menuPanel.addStatisticsListener(e -> navModel.navigate(Screen.Statistics));
                menuPanel.addRankingListener(e -> navModel.navigate(Screen.Ranking));
                menuPanel.enableBtns(currentUser != null);
                mainFrame.add(menuPanel);
                break;
            case Statistics:
                StatisticPanel statisticPanel = new StatisticPanel(currentUser);
                currentView = statisticPanel;
                statisticPanel.addReturnToMenuListener(e -> navModel.navigate(Screen.Menu));
                mainFrame.add(statisticPanel);
                break;
            case Ranking:
                List<User> sorted_users = users.stream() // Stream<List<User>>
                        .sorted(Comparator
                                .comparingInt(User::getGamesWon).reversed()
                                .thenComparingInt(User::getGamesPlayed))
                        .toList(); // List<User>

                RankingPanel rankingPanel = new RankingPanel(sorted_users);
                currentView = rankingPanel;
                rankingPanel.addReturnToMenuListener(e -> navModel.navigate(Screen.Menu));
                mainFrame.add(rankingPanel);
                break;
            case Game:
                // record new game in user profile
                currentUser.addGamePlayed();

                GameMap gameMap = new GameMap();
                currentView = gameMap;

                gameMap.setPreferredSize(mainFrame.getSize());

                Game game = new Game();
                // observer to update user profile
                game.addObserver(currentUser);

                GameController gc = new GameController(game, gameMap);

                gc.setOnGameOverListener(() ->
                {
                    navModel.navigate(Screen.Loss);
                });

                gc.setOnGameWinListener(() ->
                {
                    navModel.navigate(Screen.Victory);
                });

                mainFrame.add(gameMap);
                break;
            case Victory:
                JPanel gameWinPanel = new JPanel(new GridBagLayout())
                {
                    {
                        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                        setBackground(new Color(127, 183, 126));

                        JLabel title = new JLabel("Congratulation");
                        title.setFont(new Font("Arial", Font.BOLD, 40));
                        title.setForeground(Color.WHITE);
                        title.setAlignmentX(Component.CENTER_ALIGNMENT);

                        add(Box.createVerticalGlue());
                        add(title);
                        add(Box.createVerticalStrut(50));

                        JButton goToMenu = new JButton("Go to Menu");
                        goToMenu.setFont(new Font("Arial", Font.PLAIN, 18));
                        goToMenu.setMaximumSize(new Dimension(200, 50));
                        goToMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
                        goToMenu.addActionListener(e -> navModel.navigate(Screen.Menu));
                        add(goToMenu);
                        add(Box.createVerticalStrut(100));
                    }
                };
                currentView = gameWinPanel;
                mainFrame.add(gameWinPanel);

                break;
            case Loss:
                JPanel gameOverPanel = new JPanel(new GridBagLayout())
                {
                    {
                        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                        setBackground(new Color(244, 91, 38));

                        JLabel title = new JLabel("Game Over");
                        title.setFont(new Font("Arial", Font.BOLD, 40));
                        title.setForeground(Color.WHITE);
                        title.setAlignmentX(Component.CENTER_ALIGNMENT);

                        add(Box.createVerticalGlue());
                        add(title);
                        add(Box.createVerticalStrut(50));

                        JButton goToMenu = new JButton("Go to Menu");
                        goToMenu.setFont(new Font("Arial", Font.PLAIN, 18));
                        goToMenu.setMaximumSize(new Dimension(200, 50));
                        goToMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
                        goToMenu.addActionListener(e -> navModel.navigate(Screen.Menu));
                        add(goToMenu);
                        add(Box.createVerticalStrut(100));

                    }
                };
                currentView = gameOverPanel;
                mainFrame.add(gameOverPanel);

                break;
        }
    }

    /**
     * Method used to select user profile
     * 
     * @param profile
     */
    private void selectProfile(User profile)
    {
        if (currentUser != null)
            currentUser.deleteObservers();
        currentUser = profile;
        currentUser.addObserver(model);
    }

}
