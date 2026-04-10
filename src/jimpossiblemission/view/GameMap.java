package jimpossiblemission.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import jimpossiblemission.controller.game.LevelManager;
import jimpossiblemission.exception.GameObjectViewCreationException;
import jimpossiblemission.model.entity.GameObject;
import jimpossiblemission.model.entity.Player;
import jimpossiblemission.model.entity.SearchableObject;
import jimpossiblemission.model.game.Level;
import jimpossiblemission.view.game.SpriteManager;
import jimpossiblemission.view.gameobject.GameObjectView;
import jimpossiblemission.view.gameobject.GameObjectViewFactory;
import jimpossiblemission.view.gameobject.PlayerView;
import jimpossiblemission.view.gameobject.StaticObjectView;

/**
 * Game JPanel
 * Composed by cardLayouts one for level loaded in game,
 * 			computer menu panel,
 * 			supercompter menu panel. 
 * 
 * @author Filippo Taiuti
 *
 */
@SuppressWarnings({ "deprecation" })
public class GameMap extends JPanel implements Observer
{
    private static final long serialVersionUID = 1L;
    private Map<String, GamePanel> panels;
    private JPanel container;
    private ComputerPanel computerPanel;
    private SuperComputerPanel superComputerPanel;
    private CardLayout cardLayout;
    protected PlayerView playerView;
    private int xScale = 312; // 512;
    private int yScale = 252; // 384;

    private JLabel lblLives;
    private JLabel lblLevel;
    private JLabel lblBlockEnemyKey;
    private JLabel lblLiftResetKey;
    private JLabel lblHackerKey;

    private PanelVisibleListener listener;

    /**
     * Constructor of GameMap
     * 
     */
    public GameMap()
    {
        setLayout(new BorderLayout());
        panels = new HashMap<>();
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);
        container.setBounds(0, 0, 1024, 768);
        // add(container, BorderLayout.CENTER);
        JLayeredPane layeredPane = new JLayeredPane();
        container.setVisible(true);
        layeredPane.add(container, JLayeredPane.DEFAULT_LAYER);

        computerPanel = new ComputerPanel();
        int x = (1024 - computerPanel.getPreferredSize().width) / 2;
        int y = (768 - computerPanel.getPreferredSize().height) / 2;
        computerPanel.setBounds(x, y, computerPanel.getPreferredSize().width, computerPanel.getPreferredSize().height);

        layeredPane.add(computerPanel, JLayeredPane.PALETTE_LAYER);

        superComputerPanel = new SuperComputerPanel();
        superComputerPanel.setBounds(x, y, superComputerPanel.getPreferredSize().width,
                superComputerPanel.getPreferredSize().height);

        layeredPane.add(superComputerPanel, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER);

        JPanel statGame = new JPanel();

        lblLives = new JLabel("Lives: ", JLabel.CENTER);
        lblLives.setFont(new Font("Arial", Font.BOLD, 16));
        statGame.add(lblLives);

        lblLevel = new JLabel("level: level00", JLabel.CENTER);
        lblLevel.setFont(new Font("Arial", Font.BOLD, 16));
        statGame.add(lblLevel);

        lblBlockEnemyKey = new JLabel("Block Robot: 0", JLabel.CENTER);
        lblBlockEnemyKey.setFont(new Font("Arial", Font.BOLD, 16));
        statGame.add(lblBlockEnemyKey);

        lblLiftResetKey = new JLabel("Lift reset: 0", JLabel.CENTER);
        lblLiftResetKey.setFont(new Font("Arial", Font.BOLD, 16));
        statGame.add(lblLiftResetKey);

        lblHackerKey = new JLabel("Hacker Key: 1", JLabel.CENTER);
        lblHackerKey.setFont(new Font("Arial", Font.BOLD, 16));
        statGame.add(lblHackerKey);

        GridLayout statLayout = new GridLayout(0, 5);

        statGame.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        statGame.setLayout(statLayout);
        add(statGame, BorderLayout.NORTH);

        setFocusable(true);
        setRequestFocusEnabled(true);
        setVisible(true);
    }

    /**
     * Shows the panel required in panelName
     * 
     * @param panelName
     */
    public void showPanel(String panelName)
    {
        // check if the panel exists
        if (panels.containsKey(panelName))
        {
            cardLayout.show(container, panelName);
        } else
        {
            // Something went wrong TODO reset position ?
        }
    }

    /**
     * Add player in PlayerView and set the player animation
     * 
     * @param player
     * @param playerAnimationManager
     */
    public void addPlayer(Player player, SpriteManager playerAnimationManager)
    {
        playerView = new PlayerView(player, playerAnimationManager);
        playerAnimationManager.setNewSprite(player.getPlayerState().name());
    }

    /**
     * Add levels to map using GameObjectViewFactory
     * 
     * @param levels
     */
    public void addLevelsToMap(Map<String, Level<GameObject>> levels)
    {
        for (Map.Entry<String, Level<GameObject>> level : levels.entrySet())
        {
            addLevelToMap(level.getKey(), level.getValue());
            List<GameObject> gameObjects = level.getValue().getGameObjects();

            // first draw searchable
            List<GameObject> searchableObject = gameObjects.stream()
                    .filter(so -> so instanceof SearchableObject)
                    .toList();
            addGameObjectsToGameObjectViewInLevel(searchableObject, level.getKey());

            List<GameObject> notSearchableObject = gameObjects.stream()
                    .filter(so -> !(so instanceof SearchableObject))
                    .toList();
            addGameObjectsToGameObjectViewInLevel(notSearchableObject, level.getKey());
        }
    }

    // add level to map
    private void addLevelToMap(String levelName, Level<GameObject> level)
    {
        GamePanel panel = new GamePanel();
        panels.put(levelName, panel);
        container.add(panel, levelName);
    }

    private void addGameObjectsToGameObjectViewInLevel(List<GameObject> gameObjects,
            String levelKey)
    {
        for (GameObject go : gameObjects)
        {
            GameObjectView sov;
            try
            {
                sov = GameObjectViewFactory.createGameObjectView(go);
                if (sov instanceof StaticObjectView)
                    go.getCollider().updateCollider(((StaticObjectView) sov).getSpriteAnimation().getHitBox());
                addGameObjectToMap(levelKey, sov);
            } catch (GameObjectViewCreationException e)
            {
                e.printStackTrace();
            }
        }

    }

    /**
     * Add gameObjectView in the panel in according of the passed leveName
     * 
     * @param levelName
     * @param gov
     */
    public void addGameObjectToMap(String levelName, GameObjectView gov)
    {
        panels.get(levelName).gameObjectViews.add(gov);
    }

    /**
     * method called when an observed object notifies a change of state
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof LevelManager)
        {
            showPanel(LevelManager.getInstance().getNameOfCurrentLevel());
            updateKeys(LevelManager.getInstance());
        }
        repaint();
    }

    // update values in view
    private void updateKeys(LevelManager levelManager)
    {
        lblLives.setText("Lives: " + levelManager.getLivesToPlay());
        lblLevel.setText("Level: " + levelManager.getNameOfCurrentLevel());
        lblBlockEnemyKey.setText("Block Robot: " + levelManager.getBlockEnemyKey());
        lblHackerKey.setText("Hacker Key: " + levelManager.getHackerKey());
        lblLiftResetKey.setText("Reset lift Key: " + levelManager.getResetLiftKey());
    }

    /**
     * Add listener to JPanel
     * 
     * @param listener PanelVisibleListener
     */
    public void setOnVisibleListener(PanelVisibleListener listener)
    {
        this.listener = listener;
    }

    /**
     * Shows the ComputerPanel
     */
    public void showComputerPanel()
    {
        computerPanel.setVisible(true);
    }

    /**
     * Hides the ComputerPanel
     */
    public void hideComputerPanel()
    {
        computerPanel.setVisible(false);
    }

    /**
     * Add the listener to computerMenuPanel
     * 
     * @param computerListener
     */
    public void addComputerPanelListener(ComputerMenuListener computerListener)
    {
        computerPanel.addComputerMenuListener(computerListener);
    }

    /**
     * Shows the SuperComputerPanel
     */
    public void showSuperComputerPanel()
    {
        superComputerPanel.setVisible(true);
    }

    /**
     * Hides the SuperCompterPanel
     */
    public void hideSuperComputerPanel()
    {
        superComputerPanel.setVisible(false);
    }

    /**
     * Adds the listener to SuperComputerPanel
     * 
     * @param superComputerListener
     */
    public void addSuperComputerPanelListener(SuperComputerMenuListener superComputerListener)
    {
        superComputerPanel.addSuperComputerMenuListener(superComputerListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNotify()
    {
        super.addNotify();
        SwingUtilities.invokeLater(() ->
        {
            requestFocusInWindow();

            if (listener != null)
                listener.onPanelVisible();

        });
    }

    /**
     * Checks the visibility of ComputerPanel
     * 
     * @return boolean
     */
    public boolean isComputerPanelVisible()
    {
        return computerPanel.isVisible();
    }

    /**
     * Class GamePanel that paint active gameObjectViews 
     */
    class GamePanel extends JPanel
    {
        private static final long serialVersionUID = 1L;
        private double scaleX, scaleY;

        private List<GameObjectView> gameObjectViews;

        /**
         * Constructor of GamePanel
         * 
         * @param panelName
         */
        public GamePanel()
        {

            gameObjectViews = new ArrayList<GameObjectView>();

            setLayout(new BorderLayout());

            setVisible(true);
            scaleX = GameMap.this.getPreferredSize().getWidth() / GameMap.this.xScale;
            scaleY = GameMap.this.getPreferredSize().getHeight() / GameMap.this.yScale;

        }

        /**
         * refresh (repaint) Diaplay
         */
        public void updateDisplay()
        {
            repaint();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            AffineTransform at = new AffineTransform();
            at.scale(scaleX, scaleY);
            g2.setTransform(at);
            g2.setColor(new Color(85, 107, 0));
            for (GameObjectView go : gameObjectViews)
            {
                if (go.isActive())
                    go.draw(g2);
            }
            if (GameMap.this.playerView != null)
                GameMap.this.playerView.draw(g2);
            g2.dispose();
        }
    }

}
