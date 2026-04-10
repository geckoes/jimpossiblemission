package jimpossiblemission.controller.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import jimpossiblemission.audio.AudioManager;
import jimpossiblemission.model.entity.Computer.TypeOfComputer;
import jimpossiblemission.model.entity.Player;
import jimpossiblemission.model.entity.Player.PlayerState;
import jimpossiblemission.model.entity.PlayerPoint;
import jimpossiblemission.model.game.Game;
import jimpossiblemission.model.game.Game.GameState;
import jimpossiblemission.view.ComputerMenuListener;
import jimpossiblemission.view.GameMap;
import jimpossiblemission.view.SuperComputerMenuListener;
import jimpossiblemission.view.game.SpriteManager;

/**
 * Impossible Mission Game Controller. It represents the core of the game, beat
 * the time, create levels, check when game's started, won or over.
 * 
 * @author Filippo Taiuti
 *
 */
@SuppressWarnings("deprecation")
public class GameController extends Observable
{
    // configuration for level map
    private static final String LEVEL_JSON_FILE = "/Levels/levels.json";
    private static final int INITIAL_MAP_ROW = 0;
    private static final int INITIAL_MAP_COL = 1;

    // Timer
    private static final int FPS = 60;
    private static final int MILLISECONDS = 1000;

    // pause frames after collision between player and robot
    private static final int FRAMES_AFTER_ELECTRIFIED = 180; // 3 seconds
                                                             // (x/FPS)

    // gap used to move elevator and player during the change level panel
    private static final int GAP_BETWEEN_FLOORS = 300;

    private Player player;

    private Timer gameTimer;
    private int timerCounter;

    private int pauseAfterDeath;

    private Game gameModel;
    private GameMap gameMap;
    private GameOverListener gameOverListener;
    private GameWinListener gameWinListener;

    /**
     * Constructor needs Game model and GameMap view
     * 
     * @param game model
     * @param game view
     */
    public GameController(Game game, GameMap gm)
    {
        gameModel = game;
        gameMap = gm;

        // timer
        ActionListener gamePerformer = new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                timerCounter++;
                if (gameModel.getState() == GameState.PAUSE)

                    if ((player.getPlayerState() == PlayerState.Electrified
                            || player.getPlayerState() == PlayerState.FallenDown) && pauseAfterDeath < 0)
                    {
                        if (player.isAlive())
                        {
                            resetLevel(player);
                            game.startGame();
                        } else
                            gameOver();
                    } else if (gm.isComputerPanelVisible())
                    {
                        game.startGame();
                    }
                if (game.getState() == GameState.PLAY)
                {
                    CollisionManager.getInstance().checkCollision(player);
                    if ((player.getPlayerState() == PlayerState.Electrified
                            || player.getPlayerState() == PlayerState.FallenDown) && pauseAfterDeath < 0)
                    {
                        pauseAfterDeath = FRAMES_AFTER_ELECTRIFIED;
                        game.pauseGame();
                    }
                    setChanged();
                    notifyObservers();
                    if (player.getPlayerState() == PlayerState.Hacking)
                    {
                        stopTimer();
                        if (player.getLastComputerHacked() == TypeOfComputer.Computer)
                            gameMap.showComputerPanel();
                        else if (player.getLastComputerHacked() == TypeOfComputer.SuperComputer)
                            gameMap.showSuperComputerPanel();
                    }
                    switchGamePanel();
                }
                pauseAfterDeath--;
            }
        };
        gameTimer = new Timer(MILLISECONDS / FPS, gamePerformer); // ~60 FPS

        // create the player
        player = new Player(150, 130, 2);

        // create player animation
        SpriteManager playerAnimationManager = new SpriteManager();
        AnimationManager playerSpriteManager = new AnimationManager(player, playerAnimationManager);

        // game play controller to move the player
        GamePlayController gpc = new KeyboardManager(player);
        gm.addKeyListener((KeyListener) gpc);

        // add observers to player
        player.addObserver(AudioManager.getInstance());

        // add observers to GameController
        addObserver(gm);
        addObserver(player);
        addObserver(LevelManager.getInstance());
        addObserver(playerSpriteManager);

        // add observers to LevelManager
        LevelManager.getInstance().addObserver(CollisionManager.getInstance());
        LevelManager.getInstance().addObserver(gm);
        LevelManager.getInstance().addObserver(AudioManager.getInstance());

        // create levels
        LevelManager.getInstance().createLevelFromJsonFile(LEVEL_JSON_FILE);
        // after creation levels to load them in gameMap and avoid a nullerror
        gm.addLevelsToMap(LevelManager.getInstance().getLevels());
        LevelManager.getInstance().setInitialLevel(INITIAL_MAP_ROW, INITIAL_MAP_COL);
        LevelManager.getInstance().setPlayer(player);
        LevelManager.getInstance().addObserverToGameObjects(AudioManager.getInstance());

        // add observers to GameModel
        game.addObserver(AudioManager.getInstance());

        gm.addFocusListener(new FocusListener()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                pauseTimer();
            }

            @Override
            public void focusGained(FocusEvent e)
            {
                startTimer();
            }
        });

        // waiting for first panel is visible
        gm.setOnVisibleListener(() ->
        {
            game.startGame();
            // reset position to player in order to consider the image dimension
            // double invoke to be sure the GameMap panel and the first
            // GamePanel are completely loaded
            SwingUtilities.invokeLater(() ->
            {
                SwingUtilities.invokeLater(() ->
                {
                    player.resetPosition(LevelManager.getInstance().getNewPlayerPosition());
                });
            });
        });
        gm.addComputerPanelListener(new ComputerMenuListener()
        {

            @Override
            public void onResetLiftPosition()
            {
                // reset position of lifts
                LevelManager.getInstance().resetLiftPositions();
            }

            @Override
            public void onMenuClosed()
            {
                // restart game
                startTimer();
            }

            @Override
            public void onBlockEnemies()
            {
                LevelManager.getInstance().blockEnemies();
            }
        });

        gm.addSuperComputerPanelListener(new SuperComputerMenuListener()
        {

            @Override
            public void onHacker()
            {
                if (LevelManager.getInstance().getHackerKey() > 0)
                    gameWon();

            }

            @Override
            public void onMenuClosed()
            {
                // restart game
                startTimer();
            }
        });

        // add Player to gameView
        gm.addPlayer(player, playerAnimationManager);

        // start the game timer
        startTimer();
    }

    /**
     * Starter of timer
     */
    private void startTimer()
    {
        gameTimer.start();
        gameModel.startGame();
    }

    /**
     * Stop timer
     */
    private void stopTimer()
    {
        gameTimer.stop();
        pauseTimer();
    }

    /**
     * Pause timer
     */
    private void pauseTimer()
    {
        gameModel.addTimePlayed((timerCounter * FPS / MILLISECONDS) / 60);
        timerCounter = 0;
        gameModel.pauseGame();
    }

    /**
     * method used to switch between game panels
     */
    public void switchGamePanel()
    {
        if (player.getX() < -40)
        {
            LevelManager.getInstance().goLeft();
            player.resetPosition(LevelManager.getInstance().getNewPlayerPosition());

        } else if (player.getX() > 310)
        {
            LevelManager.getInstance().goRight();
            player.resetPosition(LevelManager.getInstance().getNewPlayerPosition());
        }
        if (player.isInElevator())
        {
            if (player.getY() < -40)
            {
                LevelManager.getInstance().goUp(GAP_BETWEEN_FLOORS);
                player.resetPosition(new PlayerPoint(player.getX(), player.getY() + GAP_BETWEEN_FLOORS));
            } else if (player.getY() > 270)
            {
                LevelManager.getInstance().goDown(GAP_BETWEEN_FLOORS);
                player.resetPosition(new PlayerPoint(player.getX(), player.getY() - GAP_BETWEEN_FLOORS));
            }
        }

    }

    /**
     * Game is completed
     */
    public void gameWon()
    {
        endGame(true);

        try
        {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        if (gameWinListener != null)
            gameWinListener.onGameWin();
    }

    /**
     * Game is over
     * 
     */
    public void gameOver()
    {
        endGame(false);
        // TODO change Panel record the new attempt
        if (gameOverListener != null)
            gameOverListener.onGameOver();
    }

    private void endGame(boolean hasWon)
    {
        pauseTimer();
        gameTimer.stop();
        gameModel.addLevelCompleted(LevelManager.getInstance().getLevelCompleted());
        gameModel.endGame(hasWon);
    }

    private void resetLevel(Player player)
    {
        LevelManager.getInstance().getLevels().get(LevelManager.getInstance().getNameOfCurrentLevel()).resetPositions();
        player.resetPosition(LevelManager.getInstance().getNewPlayerPosition());
        gameModel.startGame();
    }

    /**
     * Set the game over listener
     * 
     * @param listener
     */
    public void setOnGameOverListener(GameOverListener listener)
    {
        this.gameOverListener = listener;
    }

    /**
     * Set the game completed listener
     * 
     * @param listener
     */
    public void setOnGameWinListener(GameWinListener listener)
    {
        this.gameWinListener = listener;

    }

}
