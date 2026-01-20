package impossiblemission;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

// ===== MVC PATTERN - MODEL =====
class GameModel extends Observable
{
    private UserProfile currentUser;
    private GameSession currentSession;
    private List<UserProfile> profiles;
    private int currentLevel;
    private List<Level> levels;

    public GameModel()
    {
        this.profiles = new ArrayList<>();
        this.levels = createLevels();
        this.currentLevel = 0;
        loadProfiles();
    }

    private List<Level> createLevels()
    {
        return Arrays.asList(new Level(1, "Training Ground", 5, 3, Arrays.asList("PATROL", "STATIC")),
                new Level(2, "Factory Floor", 6, 4, Arrays.asList("PATROL", "CIRCULAR")),
                new Level(3, "Underground Base", 7, 5, Arrays.asList("PATROL", "CIRCULAR", "TELEPORT")),
                new Level(4, "Security Center", 8, 6, Arrays.asList("PATROL", "CIRCULAR", "TELEPORT")),
                new Level(5, "Data Vault", 9, 7, Arrays.asList("CIRCULAR", "TELEPORT", "HUNTER")),
                new Level(6, "Control Room", 10, 8, Arrays.asList("PATROL", "HUNTER", "TELEPORT")),
                new Level(7, "Command Center", 12, 9, Arrays.asList("CIRCULAR", "HUNTER", "TELEPORT")),
                new Level(8, "Final Mission", 15, 10, Arrays.asList("HUNTER", "TELEPORT", "BOSS")));
    }

    public void setCurrentUser(UserProfile user)
    {
        this.currentUser = user;
        setChanged();
        notifyObservers("USER_CHANGED");
    }

    public void startNewGame()
    {
        currentSession = new GameSession(currentUser, levels.get(currentLevel));
        setChanged();
        notifyObservers("GAME_STARTED");
    }

    public void nextLevel()
    {
        if (currentLevel < levels.size() - 1)
        {
            currentLevel++;
            currentSession.setLevel(levels.get(currentLevel));
            setChanged();
            notifyObservers("LEVEL_CHANGED");
        } else
        {
            gameWon();
        }
    }

    public void gameOver()
    {
        if (currentSession != null)
        {
            currentUser.addGame(false, currentSession.getScore());
            saveProfiles();
            setChanged();
            notifyObservers("GAME_OVER");
        }
    }

    public void gameWon()
    {
        if (currentSession != null)
        {
            currentUser.addGame(true, currentSession.getScore());
            currentUser.completeLevel(currentLevel + 1);
            saveProfiles();
            setChanged();
            notifyObservers("GAME_WON");
        }
    }

    // Stream usage for leaderboard
    public List<UserProfile> getLeaderboard()
    {
        return profiles.stream().sorted((a, b) -> Integer.compare(b.getBestScore(), a.getBestScore())).limit(10)
                .collect(Collectors.toList());
    }

    // Stream usage for level statistics
    public Map<Integer, Long> getLevelCompletionStats()
    {
        return profiles.stream().flatMap(p -> p.getCompletedLevels().stream())
                .collect(Collectors.groupingBy(l -> l, Collectors.counting()));
    }

    private void loadProfiles()
    {
        profiles.add(new UserProfile("Player1", "avatar1.png"));
        profiles.add(new UserProfile("TestUser", "avatar2.png"));
    }

    private void saveProfiles()
    {
        System.out.println("Profiles saved");
    }

    // Getters
    public UserProfile getCurrentUser()
    {
        return currentUser;
    }

    public GameSession getCurrentSession()
    {
        return currentSession;
    }

    public List<UserProfile> getProfiles()
    {
        return profiles;
    }

    public Level getCurrentLevel()
    {
        return currentLevel < levels.size() ? levels.get(currentLevel) : null;
    }

    public List<Level> getLevels()
    {
        return levels;
    }

    public void addProfile(UserProfile profile)
    {
        profiles.add(profile);
    }

    public void resetLevel()
    {
        currentLevel = 0;
    }
}

class UserProfile
{
    private String nickname;
    private String avatar;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int level;
    private int bestScore;
    private List<Integer> completedLevels;

    public UserProfile(String nickname, String avatar)
    {
        this.nickname = nickname;
        this.avatar = avatar;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.gamesLost = 0;
        this.level = 1;
        this.bestScore = 0;
        this.completedLevels = new ArrayList<>();
    }

    public void addGame(boolean won, int score)
    {
        gamesPlayed++;
        if (won)
        {
            gamesWon++;
            if (score > bestScore)
                bestScore = score;
        } else
        {
            gamesLost++;
        }
        updateLevel();
    }

    private void updateLevel()
    {
        level = 1 + (gamesWon / 3);
    }

    public void completeLevel(int levelNumber)
    {
        if (!completedLevels.contains(levelNumber))
        {
            completedLevels.add(levelNumber);
        }
    }

    public String getNickname()
    {
        return nickname;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public int getGamesPlayed()
    {
        return gamesPlayed;
    }

    public int getGamesWon()
    {
        return gamesWon;
    }

    public int getGamesLost()
    {
        return gamesLost;
    }

    public int getLevel()
    {
        return level;
    }

    public int getBestScore()
    {
        return bestScore;
    }

    public List<Integer> getCompletedLevels()
    {
        return completedLevels;
    }

    public double getWinRate()
    {
        return gamesPlayed > 0 ? (double) gamesWon / gamesPlayed * 100 : 0;
    }

    @Override
    public String toString()
    {
        return String.format("%s - Level %d - Win Rate: %.1f%%", nickname, level, getWinRate());
    }
}

class GameSession
{
    private UserProfile user;
    private Level level;
    private int score;
    private int lives;
    private int puzzlePieces;
    private int requiredPieces;
    private boolean elevatorActivated;
    private boolean robotsBlocked;
    private long robotBlockTime;
    private static final long ROBOT_BLOCK_DURATION = 5000;

    public GameSession(UserProfile user, Level level)
    {
        this.user = user;
        this.level = level;
        this.score = 0;
        this.lives = 3;
        this.puzzlePieces = 0;
        this.requiredPieces = level.getRequiredPieces();
        this.elevatorActivated = false;
        this.robotsBlocked = false;
    }

    public void blockRobots()
    {
        robotsBlocked = true;
        robotBlockTime = System.currentTimeMillis();
    }

    public void updateRobotBlock()
    {
        if (robotsBlocked && System.currentTimeMillis() - robotBlockTime > ROBOT_BLOCK_DURATION)
        {
            robotsBlocked = false;
        }
    }

    public boolean canActivateElevator()
    {
        return puzzlePieces >= requiredPieces;
    }

    public int getScore()
    {
        return score;
    }

    public void addScore(int points)
    {
        this.score += points;
    }

    public int getLives()
    {
        return lives;
    }

    public void loseLife()
    {
        this.lives--;
    }

    public int getPuzzlePieces()
    {
        return puzzlePieces;
    }

    public void addPuzzlePiece()
    {
        this.puzzlePieces++;
    }

    public int getRequiredPieces()
    {
        return requiredPieces;
    }

    public boolean isElevatorActivated()
    {
        return elevatorActivated;
    }

    public void setElevatorActivated(boolean activated)
    {
        this.elevatorActivated = activated;
    }

    public boolean areRobotsBlocked()
    {
        return robotsBlocked;
    }

    public Level getLevel()
    {
        return level;
    }

    public void setLevel(Level level)
    {
        this.level = level;
        this.puzzlePieces = 0;
        this.requiredPieces = level.getRequiredPieces();
        this.elevatorActivated = false;
    }
}

class Level
{
    private int number;
    private String name;
    private int terminalCount;
    private int requiredPieces;
    private List<String> robotTypes;

    public Level(int number, String name, int terminalCount, int requiredPieces, List<String> robotTypes)
    {
        this.number = number;
        this.name = name;
        this.terminalCount = terminalCount;
        this.requiredPieces = requiredPieces;
        this.robotTypes = robotTypes;
    }

    public int getNumber()
    {
        return number;
    }

    public String getName()
    {
        return name;
    }

    public int getTerminalCount()
    {
        return terminalCount;
    }

    public int getRequiredPieces()
    {
        return requiredPieces;
    }

    public List<String> getRobotTypes()
    {
        return robotTypes;
    }
}

// ===== AUDIO MANAGER (SINGLETON) =====
class AudioManager
{
    private static AudioManager instance;
    private Map<String, Clip> audioClips;
    private boolean soundEnabled = true;

    private AudioManager()
    {
        audioClips = new HashMap<>();
        loadAudioClips();
    }

    public static AudioManager getInstance()
    {
        if (instance == null)
        {
            instance = new AudioManager();
        }
        return instance;
    }

    private void loadAudioClips()
    {
        audioClips.put("jump", createSilentClip());
        audioClips.put("collect", createSilentClip());
        audioClips.put("death", createSilentClip());
        audioClips.put("levelComplete", createSilentClip());
        audioClips.put("gameOver", createSilentClip());
        audioClips.put("robotBlock", createSilentClip());
    }

    private Clip createSilentClip()
    {
        try
        {
            AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
            byte[] silentData = new byte[1024];
            AudioInputStream stream = new AudioInputStream(new ByteArrayInputStream(silentData), format,
                    silentData.length / format.getFrameSize());
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            return clip;
        } catch (Exception e)
        {
            return null;
        }
    }

    public void playSound(String soundName)
    {
        if (soundEnabled && audioClips.containsKey(soundName))
        {
            Clip clip = audioClips.get(soundName);
            if (clip != null)
            {
                clip.setFramePosition(0);
                clip.start();
            }
        }
    }

    public void setSoundEnabled(boolean enabled)
    {
        this.soundEnabled = enabled;
    }
}

// ===== MVC PATTERN - VIEW (SWING) =====
abstract class GameView extends JPanel implements Observer
{
    protected GameModel model;

    public GameView(GameModel model)
    {
        this.model = model;
        model.addObserver(this);
        setBackground(Color.BLACK);
    }

    public abstract void update(Observable o, Object arg);
}

class MainMenuView extends GameView
{
    private GameController controller;

    public MainMenuView(GameModel model, GameController controller)
    {
        super(model);
        this.controller = controller;
        createMenuView();
    }

    private void createMenuView()
    {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("IMPOSSIBLE MISSION");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.CYAN);
        gbc.gridy = 0;
        add(title, gbc);

        String[] buttonLabels = { "New Game", "Profile Manager", "Leaderboard", "Settings", "Exit" };
        for (int i = 0; i < buttonLabels.length; i++)
        {
            JButton btn = new JButton(buttonLabels[i]);
            btn.setPreferredSize(new Dimension(200, 40));
            btn.setFont(new Font("Arial", Font.PLAIN, 16));

            final int index = i;
            btn.addActionListener(e -> handleButtonClick(index));

            gbc.gridy = i + 1;
            add(btn, gbc);
        }
    }

    private void handleButtonClick(int index)
    {
        switch (index) {
            case 0:
                controller.startNewGame();
                break;
            case 1:
                controller.showProfileManager();
                break;
            case 2:
                controller.showLeaderboard();
                break;
            case 3:
                JOptionPane.showMessageDialog(this, "Settings not implemented yet");
                break;
            case 4:
                System.exit(0);
                break;
        }
    }

    @Override
    public void update(Observable o, Object arg)
    {
        repaint();
    }
}

class ProfileManagerView extends GameView
{
    private GameController controller;
    private JList<UserProfile> profileList;
    private DefaultListModel<UserProfile> listModel;

    public ProfileManagerView(GameModel model, GameController controller)
    {
        super(model);
        this.controller = controller;
        createProfileView();
    }

    private void createProfileView()
    {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Profile Manager", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        for (UserProfile profile : model.getProfiles())
        {
            listModel.addElement(profile);
        }

        profileList = new JList<>(listModel);
        profileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        profileList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(profileList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.BLACK);

        JButton selectBtn = new JButton("Select Profile");
        JButton createBtn = new JButton("Create New Profile");
        JButton backBtn = new JButton("Back to Menu");

        selectBtn.addActionListener(e ->
        {
            UserProfile selected = profileList.getSelectedValue();
            if (selected != null)
            {
                controller.selectProfile(selected);
            }
        });

        createBtn.addActionListener(e -> showCreateProfileDialog());
        backBtn.addActionListener(e -> controller.showMainMenu());

        buttonPanel.add(selectBtn);
        buttonPanel.add(createBtn);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void showCreateProfileDialog()
    {
        String nickname = JOptionPane.showInputDialog(this, "Enter nickname:");
        if (nickname != null && !nickname.trim().isEmpty())
        {
            UserProfile newProfile = new UserProfile(nickname, "default.png");
            model.addProfile(newProfile);
            listModel.addElement(newProfile);
        }
    }

    @Override
    public void update(Observable o, Object arg)
    {
        listModel.clear();
        for (UserProfile profile : model.getProfiles())
        {
            listModel.addElement(profile);
        }
    }
}

class GamePlayView extends GameView
{
    private GameController controller;
    private GameCanvas gameCanvas;
    private JLabel scoreLabel, livesLabel, piecesLabel, levelLabel;
    private JProgressBar elevatorProgress;

    public GamePlayView(GameModel model, GameController controller)
    {
        super(model);
        this.controller = controller;
        createGameView();
    }

    private void createGameView()
    {
        setLayout(new BorderLayout());

        // Top UI Panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        topPanel.setBackground(Color.DARK_GRAY);

        levelLabel = createLabel("Level: 1");
        scoreLabel = createLabel("Score: 0");
        livesLabel = createLabel("Lives: 3");
        piecesLabel = createLabel("Pieces: 0/0");

        elevatorProgress = new JProgressBar(0, 100);
        elevatorProgress.setPreferredSize(new Dimension(150, 20));
        elevatorProgress.setStringPainted(true);
        elevatorProgress.setString("Elevator");

        topPanel.add(levelLabel);
        topPanel.add(scoreLabel);
        topPanel.add(livesLabel);
        topPanel.add(piecesLabel);
        topPanel.add(elevatorProgress);

        add(topPanel, BorderLayout.NORTH);

        // Game Canvas
        gameCanvas = new GameCanvas(model);
        add(gameCanvas, BorderLayout.CENTER);

        // Bottom UI Panel
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(Color.DARK_GRAY);

        JButton pauseBtn = new JButton("Pause");
        JButton blockRobotsBtn = new JButton("Block Robots");
        JButton activateElevatorBtn = new JButton("Activate Elevator");

        pauseBtn.addActionListener(e -> controller.pauseGame());
        blockRobotsBtn.addActionListener(e -> controller.blockRobots());
        activateElevatorBtn.addActionListener(e -> controller.activateElevator());

        bottomPanel.add(pauseBtn);
        bottomPanel.add(blockRobotsBtn);
        bottomPanel.add(activateElevatorBtn);

        JLabel instructions = new JLabel("ARROWS: move, SPACE: jump, ENTER: search terminals");
        instructions.setForeground(Color.WHITE);
        bottomPanel.add(instructions);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text)
    {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    public GameCanvas getGameCanvas()
    {
        return gameCanvas;
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if (model.getCurrentSession() != null)
        {
            GameSession session = model.getCurrentSession();
            scoreLabel.setText("Score: " + session.getScore());
            livesLabel.setText("Lives: " + session.getLives());
            piecesLabel.setText(String.format("Pieces: %d/%d", session.getPuzzlePieces(), session.getRequiredPieces()));

            if (model.getCurrentLevel() != null)
            {
                levelLabel.setText("Level: " + model.getCurrentLevel().getNumber());
            }

            int progress = session.getRequiredPieces() > 0
                    ? (int) ((double) session.getPuzzlePieces() / session.getRequiredPieces() * 100)
                    : 0;
            elevatorProgress.setValue(progress);
        }
    }
}

class GameCanvas extends JPanel
{
    private GameModel model;
    private GameEngine engine;

    public GameCanvas(GameModel model)
    {
        this.model = model;
        setPreferredSize(new Dimension(800, 500));
        setBackground(Color.BLACK);
        setFocusable(true);
    }

    public void setEngine(GameEngine engine)
    {
        this.engine = engine;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (engine != null)
        {
            engine.render((Graphics2D) g);
        }
    }
}

class LeaderboardView extends GameView
{
    private GameController controller;

    public LeaderboardView(GameModel model, GameController controller)
    {
        super(model);
        this.controller = controller;
        createLeaderboardView();
    }

    private void createLeaderboardView()
    {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Leaderboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.CYAN);
        add(title, BorderLayout.NORTH);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        List<UserProfile> leaders = model.getLeaderboard();
        for (int i = 0; i < leaders.size(); i++)
        {
            UserProfile profile = leaders.get(i);
            listModel.addElement(String.format("#%d - %s - Score: %d - Level: %d", i + 1, profile.getNickname(),
                    profile.getBestScore(), profile.getLevel()));
        }

        JList<String> leaderList = new JList<>(listModel);
        leaderList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(leaderList);
        add(scrollPane, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back to Menu");
        backBtn.addActionListener(e -> controller.showMainMenu());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        removeAll();
        createLeaderboardView();
        revalidate();
        repaint();
    }
}

// ===== MVC PATTERN - CONTROLLER =====
class GameController
{
    private GameModel model;
    private JFrame frame;
    private GameEngine gameEngine;

    private MainMenuView mainMenuView;
    private ProfileManagerView profileManagerView;
    private GamePlayView gamePlayView;
    private LeaderboardView leaderboardView;

    public GameController(GameModel model, JFrame frame)
    {
        this.model = model;
        this.frame = frame;

        mainMenuView = new MainMenuView(model, this);
        profileManagerView = new ProfileManagerView(model, this);
        gamePlayView = new GamePlayView(model, this);
        leaderboardView = new LeaderboardView(model, this);

        showMainMenu();
    }

    public void showMainMenu()
    {
        switchPanel(mainMenuView);
    }

    public void showProfileManager()
    {
        switchPanel(profileManagerView);
    }

    public void showLeaderboard()
    {
        switchPanel(leaderboardView);
    }

    public void selectProfile(UserProfile profile)
    {
        model.setCurrentUser(profile);
        JOptionPane.showMessageDialog(frame, "Profile selected: " + profile.getNickname());
        showMainMenu();
    }

    public void startNewGame()
    {
        if (model.getCurrentUser() == null)
        {
            JOptionPane.showMessageDialog(frame, "Please select a profile first!", "No Profile",
                    JOptionPane.WARNING_MESSAGE);
            showProfileManager();
            return;
        }

        model.startNewGame();
        switchPanel(gamePlayView);

        GameCanvas canvas = gamePlayView.getGameCanvas();
        gameEngine = new GameEngine(model, this, canvas);
        canvas.setEngine(gameEngine);

        // Setup key listeners
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e)
            {
                gameEngine.handleKeyPressed(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                gameEngine.handleKeyReleased(e.getKeyCode());
            }
        });

        canvas.requestFocusInWindow();
        gameEngine.start();
    }

    public void pauseGame()
    {
        if (gameEngine != null)
        {
            gameEngine.togglePause();
        }
    }

    public void blockRobots()
    {
        if (model.getCurrentSession() != null)
        {
            model.getCurrentSession().blockRobots();
            AudioManager.getInstance().playSound("robotBlock");
        }
    }

    public void activateElevator()
    {
        if (model.getCurrentSession() != null && model.getCurrentSession().canActivateElevator())
        {
            model.getCurrentSession().setElevatorActivated(true);
            nextLevel();
        } else
        {
            JOptionPane.showMessageDialog(frame, "Collect all puzzle pieces first!");
        }
    }

    public void nextLevel()
    {
        model.nextLevel();
        if (gameEngine != null && model.getCurrentLevel() != null)
        {
            gameEngine.loadLevel(model.getCurrentLevel());
        }
        AudioManager.getInstance().playSound("levelComplete");
    }

    public void gameOver()
    {
        if (gameEngine != null)
        {
            gameEngine.stop();
        }
        model.gameOver();

        int result = JOptionPane.showConfirmDialog(frame,
                "Game Over! Final Score: " + model.getCurrentSession().getScore() + "\n\nContinue?", "Game Over",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION)
        {
            model.resetLevel();
            startNewGame();
        } else
        {
            showMainMenu();
        }

        AudioManager.getInstance().playSound("gameOver");
    }

    public void gameWon()
    {
        if (gameEngine != null)
        {
            gameEngine.stop();
        }
        model.gameWon();

        JOptionPane.showMessageDialog(frame,
                "Mission Accomplished!\nAll levels completed!\nFinal Score: " + model.getCurrentSession().getScore(),
                "Victory!", JOptionPane.INFORMATION_MESSAGE);

        showMainMenu();
    }

    private void switchPanel(JPanel panel)
    {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }
}

// ===== GAME ENGINE =====
class GameEngine
{
    private GameModel model;
    private GameController controller;
    private GameCanvas canvas;
    private Set<Integer> activeKeys;
    private Player player;
    private List<GameObject> platforms;
    private List<Robot> robots;
    private List<Terminal> terminals;
    private Timer gameTimer;
    private boolean paused;
    private ParticleSystem particleSystem;

    public GameEngine(GameModel model, GameController controller, GameCanvas canvas)
    {
        this.model = model;
        this.controller = controller;
        this.canvas = canvas;
        this.activeKeys = new HashSet<>();
        this.paused = false;
        this.particleSystem = new ParticleSystem();
        initializeLevel();
    }

    private void initializeLevel()
    {
        Level currentLevel = model.getCurrentLevel();
        if (currentLevel == null)
            return;

        platforms = new ArrayList<>();
        robots = new ArrayList<>();
        terminals = new ArrayList<>();

        // Create platforms
        platforms.add(new Platform(0, 480, 800, 20));
        platforms.add(new Platform(150, 400, 100, 20));
        platforms.add(new Platform(300, 320, 120, 20));
        platforms.add(new Platform(500, 240, 100, 20));
        platforms.add(new Platform(650, 160, 100, 20));
        platforms.add(new Platform(200, 180, 80, 20));

        // Create robots based on level
        List<String> robotTypes = currentLevel.getRobotTypes();
        if (robotTypes.contains("PATROL"))
        {
            robots.add(new Robot(300, 300, new PatrolMovement(2, 280, 420)));
        }
        if (robotTypes.contains("CIRCULAR"))
        {
            robots.add(new Robot(400, 300, new CircularMovement(400, 300, 50, 0.05)));
        }
        if (robotTypes.size() > 2)
        {
            robots.add(new Robot(150, 380, new PatrolMovement(1.5, 150, 250)));
        }

        // Create terminals
        for (int i = 0; i < currentLevel.getTerminalCount() && i < 6; i++)
        {
            int x = 150 + i * 120;
            int y = 360 - i * 60;
            terminals.add(new Terminal(x, y));
        }

        // Create player
        player = new Player(100, 400);
    }

    public void start()
    {
        gameTimer = new Timer(16, e ->
        { // ~60 FPS
            if (!paused)
            {
                update();
                canvas.repaint();
            }
        });
        gameTimer.start();
    }

    public void stop()
    {
        if (gameTimer != null)
        {
            gameTimer.stop();
        }
    }

    public void togglePause()
    {
        paused = !paused;
    }

    private void update()
    {
        if (model.getCurrentSession() == null)
            return;

        GameSession session = model.getCurrentSession();
        session.updateRobotBlock();

        // Handle input
        if (activeKeys.contains(KeyEvent.VK_LEFT))
        {
            player.moveLeft();
        } else if (activeKeys.contains(KeyEvent.VK_RIGHT))
        {
            player.moveRight();
        } else
        {
            player.stopMoving();
        }

        // Update game objects
        player.update();
        handlePlatformCollisions();

        if (!session.areRobotsBlocked())
        {
            robots.forEach(Robot::update);
        }

        // Check robot collisions
        for (Robot robot : robots)
        {
            if (player.intersects(robot))
            {
                player.resetPosition();
                session.loseLife();
                particleSystem.createExplosion(player.getX(), player.getY(), Color.RED);
                AudioManager.getInstance().playSound("death");

                if (session.getLives() <= 0)
                {
                    controller.gameOver();
                    return;
                }
                break;
            }
        }

        // Check terminal interactions
        if (activeKeys.contains(KeyEvent.VK_ENTER))
        {
            for (Terminal terminal : terminals)
            {
                if (player.intersects(terminal) && !terminal.isSearched())
                {
                    boolean foundPiece = terminal.search();
                    if (foundPiece)
                    {
                        session.addPuzzlePiece();
                        session.addScore(200);
                        particleSystem.createExplosion(terminal.getX(), terminal.getY(), Color.GREEN);
                        AudioManager.getInstance().playSound("collect");
                    }
                    session.addScore(50);
                    break;
                }
            }
        }

        // Update particle system
        particleSystem.update();

        // Check win condition
        if (session.isElevatorActivated())
        {
            if (model.getCurrentLevel().getNumber() == 8)
            {
                controller.gameWon();
            }
        }

        model.hasChanged();
        model.notifyObservers("GAME_UPDATE");
    }

    private void handlePlatformCollisions()
    {
        player.setOnGround(false);
        for (GameObject platform : platforms)
        {
            if (player.intersects(platform) && player.getVelocityY() >= 0)
            {
                double playerBottom = player.getY() + player.getHeight();
                if (playerBottom > platform.getY() && playerBottom < platform.getY() + 20)
                {
                    player.setY(platform.getY() - player.getHeight());
                    player.setVelocityY(0);
                    player.setOnGround(true);
                }
            }
        }
    }

    public void render(Graphics2D g)
    {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 500);

        // Draw platforms
        g.setColor(Color.GRAY);
        for (GameObject platform : platforms)
        {
            g.fillRect((int) platform.getX(), (int) platform.getY(), (int) platform.getWidth(),
                    (int) platform.getHeight());
        }

        // Draw terminals
        for (Terminal terminal : terminals)
        {
            terminal.render(g);
        }

        // Draw robots with visual effect if blocked
        if (model.getCurrentSession() != null && model.getCurrentSession().areRobotsBlocked())
        {
            g.setColor(new Color(255, 255, 0, 100));
            g.fillRect(0, 0, 800, 500);
        }

        for (Robot robot : robots)
        {
            robot.render(g);
        }

        // Draw player
        player.render(g);

        // Draw particles
        particleSystem.render(g);

        // Draw level info
        if (model.getCurrentLevel() != null)
        {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Level: " + model.getCurrentLevel().getName(), 10, 20);
        }
    }

    public void handleKeyPressed(int keyCode)
    {
        activeKeys.add(keyCode);
        if (keyCode == KeyEvent.VK_SPACE && player.isOnGround())
        {
            player.jump();
            AudioManager.getInstance().playSound("jump");
        }
    }

    public void handleKeyReleased(int keyCode)
    {
        activeKeys.remove(keyCode);
    }

    public void loadLevel(Level level)
    {
        initializeLevel();
    }
}

// ===== STRATEGY PATTERN - MOVEMENT =====
interface MovementStrategy
{
    void move(GameObject obj);
}

class PatrolMovement implements MovementStrategy
{
    private double speed;
    private double leftBound;
    private double rightBound;
    private double direction;

    public PatrolMovement(double speed, double leftBound, double rightBound)
    {
        this.speed = speed;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.direction = 1;
    }

    @Override
    public void move(GameObject obj)
    {
        obj.setX(obj.getX() + speed * direction);
        if (obj.getX() <= leftBound || obj.getX() >= rightBound - obj.getWidth())
        {
            direction *= -1;
        }
    }
}

class CircularMovement implements MovementStrategy
{
    private double centerX, centerY;
    private double radius;
    private double angle;
    private double speed;

    public CircularMovement(double centerX, double centerY, double radius, double speed)
    {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.speed = speed;
        this.angle = 0;
    }

    @Override
    public void move(GameObject obj)
    {
        angle += speed;
        obj.setX(centerX + Math.cos(angle) * radius - obj.getWidth() / 2);
        obj.setY(centerY + Math.sin(angle) * radius - obj.getHeight() / 2);
    }
}

class StaticMovement implements MovementStrategy
{
    @Override
    public void move(GameObject obj)
    {
        // Object stays still
    }
}

// ===== GAME OBJECTS =====
abstract class GameObject
{
    protected double x, y, width, height;
    protected boolean active;

    public GameObject(double x, double y, double width, double height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.active = true;
    }

    public abstract void update();

    public abstract void render(Graphics2D g);

    public boolean intersects(GameObject other)
    {
        return x < other.x + other.width && x + width > other.x && y < other.y + other.height && y + height > other.y;
    }

    // Getters and setters
    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public double getWidth()
    {
        return width;
    }

    public double getHeight()
    {
        return height;
    }

    public boolean isActive()
    {
        return active;
    }
}

class Player extends GameObject
{
    private double velocityX, velocityY;
    private boolean onGround;
    private static final double SPEED = 3;
    private static final double JUMP_STRENGTH = -12;
    private static final double GRAVITY = 0.5;
    private double startX, startY;

    public Player(double x, double y)
    {
        super(x, y, 20, 30);
        this.velocityX = 0;
        this.velocityY = 0;
        this.onGround = false;
        this.startX = x;
        this.startY = y;
    }

    @Override
    public void update()
    {
        if (!onGround)
        {
            velocityY += GRAVITY;
        }

        x += velocityX;
        y += velocityY;

        // Keep in bounds
        if (x < 0)
            x = 0;
        if (x > 800 - width)
            x = 800 - width;
        if (y > 500)
        {
            resetPosition();
        }
    }

    @Override
    public void render(Graphics2D g)
    {
        g.setColor(Color.BLUE);
        g.fillRect((int) x, (int) y, (int) width, (int) height);

        // Draw simple face
        g.setColor(Color.WHITE);
        g.fillOval((int) x + 5, (int) y + 8, 3, 3);
        g.fillOval((int) x + 12, (int) y + 8, 3, 3);
    }

    public void moveLeft()
    {
        velocityX = -SPEED;
    }

    public void moveRight()
    {
        velocityX = SPEED;
    }

    public void stopMoving()
    {
        velocityX = 0;
    }

    public void jump()
    {
        if (onGround)
        {
            velocityY = JUMP_STRENGTH;
            onGround = false;
        }
    }

    public void resetPosition()
    {
        x = startX;
        y = startY;
        velocityX = 0;
        velocityY = 0;
        onGround = false;
    }

    public boolean isOnGround()
    {
        return onGround;
    }

    public void setOnGround(boolean onGround)
    {
        this.onGround = onGround;
    }

    public double getVelocityY()
    {
        return velocityY;
    }

    public void setVelocityY(double vy)
    {
        this.velocityY = vy;
    }
}

class Robot extends GameObject
{
    private MovementStrategy movementStrategy;
    private Color color;

    public Robot(double x, double y, MovementStrategy strategy)
    {
        super(x, y, 15, 20);
        this.movementStrategy = strategy;
        this.color = Color.RED;
    }

    @Override
    public void update()
    {
        movementStrategy.move(this);
    }

    @Override
    public void render(Graphics2D g)
    {
        g.setColor(color);
        g.fillRect((int) x, (int) y, (int) width, (int) height);

        // Draw eyes
        g.setColor(Color.YELLOW);
        g.fillOval((int) x + 3, (int) y + 5, 3, 3);
        g.fillOval((int) x + 9, (int) y + 5, 3, 3);

        // Draw antenna
        g.setColor(Color.RED);
        g.drawLine((int) x + (int) width / 2, (int) y, (int) x + (int) width / 2, (int) y - 5);
        g.fillOval((int) x + (int) width / 2 - 2, (int) y - 8, 4, 4);
    }

    public void setMovementStrategy(MovementStrategy strategy)
    {
        this.movementStrategy = strategy;
    }
}

class Terminal extends GameObject
{
    private boolean searched;
    private Random random = new Random();

    public Terminal(double x, double y)
    {
        super(x, y, 20, 20);
        this.searched = false;
    }

    @Override
    public void update()
    {
        // Terminals don't move
    }

    @Override
    public void render(Graphics2D g)
    {
        g.setColor(searched ? new Color(0, 100, 0) : Color.GREEN);
        g.fillRect((int) x, (int) y, (int) width, (int) height);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("T", (int) x + 6, (int) y + 15);

        // Draw blinking effect if not searched
        if (!searched && System.currentTimeMillis() % 1000 < 500)
        {
            g.setColor(new Color(0, 255, 0, 100));
            g.fillRect((int) x, (int) y, (int) width, (int) height);
        }
    }

    public boolean search()
    {
        if (!searched)
        {
            searched = true;
            return random.nextDouble() < 0.3; // 30% chance of finding puzzle piece
        }
        return false;
    }

    public boolean isSearched()
    {
        return searched;
    }
}

class Platform extends GameObject
{
    public Platform(double x, double y, double width, double height)
    {
        super(x, y, width, height);
    }

    @Override
    public void update()
    {
        // Platforms don't move
    }

    @Override
    public void render(Graphics2D g)
    {
        g.setColor(Color.GRAY);
        g.fillRect((int) x, (int) y, (int) width, (int) height);

        // Add some texture
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < width; i += 20)
        {
            g.drawLine((int) x + i, (int) y, (int) x + i, (int) y + (int) height);
        }
    }
}

// ===== PARTICLE SYSTEM (for visual effects) =====
class ParticleSystem
{
    private List<Particle> particles;

    public ParticleSystem()
    {
        this.particles = new ArrayList<>();
    }

    public void createExplosion(double x, double y, Color color)
    {
        for (int i = 0; i < 20; i++)
        {
            double angle = Math.random() * Math.PI * 2;
            double speed = 2 + Math.random() * 3;
            particles.add(new Particle(x, y, Math.cos(angle) * speed, Math.sin(angle) * speed, color));
        }
    }

    public void update()
    {
        particles.removeIf(p -> !p.isAlive());
        particles.forEach(Particle::update);
    }

    public void render(Graphics2D g)
    {
        particles.forEach(p -> p.render(g));
    }
}

class Particle
{
    private double x, y, vx, vy;
    private Color color;
    private int life;

    public Particle(double x, double y, double vx, double vy, Color color)
    {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.color = color;
        this.life = 30;
    }

    public void update()
    {
        x += vx;
        y += vy;
        vy += 0.2; // Gravity
        life--;
    }

    public void render(Graphics2D g)
    {
        int alpha = (int) (255.0 * life / 30.0);
        g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
        g.fillOval((int) x, (int) y, 4, 4);
    }

    public boolean isAlive()
    {
        return life > 0;
    }
}

// ===== MAIN APPLICATION =====
public class ImpossibleMissionGame
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            JFrame frame = new JFrame("Impossible Mission");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 700);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);

            GameModel model = new GameModel();
            GameController controller = new GameController(model, frame);

            frame.setVisible(true);
        });
    }
}