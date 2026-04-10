package jimpossiblemission.controller.game;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import jimpossiblemission.audio.AudioManager;
import jimpossiblemission.model.entity.BigBomb;
import jimpossiblemission.model.entity.Computer;
import jimpossiblemission.model.entity.Direction;
import jimpossiblemission.model.entity.DynamicObject;
import jimpossiblemission.model.entity.Elevator;
import jimpossiblemission.model.entity.Enemy;
import jimpossiblemission.model.entity.GameObject;
import jimpossiblemission.model.entity.Lift;
import jimpossiblemission.model.entity.LiftFloor;
import jimpossiblemission.model.entity.Platform;
import jimpossiblemission.model.entity.Player;
import jimpossiblemission.model.entity.PlayerPoint;
import jimpossiblemission.model.entity.Robot;
import jimpossiblemission.model.entity.SearchableObject;
import jimpossiblemission.model.entity.SearchableObject.Badge;
import jimpossiblemission.model.entity.SuperComputer;
import jimpossiblemission.model.entity.Wall;
import jimpossiblemission.model.entity.strategy.AttackStrategies;
import jimpossiblemission.model.entity.strategy.AttackStrategy;
import jimpossiblemission.model.entity.strategy.MoveStrategies;
import jimpossiblemission.model.entity.strategy.MoveStrategy;
import jimpossiblemission.model.game.Level;

/**
 * Level Manager (Singleton) is used to create levels.
 * Every level contains all kind of gameObject (excluded Player) static and
 * dynamic.
 * Level Manager has a main Elevator to move player between floors.
 * Also it manages all kind of keys used in game.
 * 
 * @author Filippo Taiuti
 *
 */
@SuppressWarnings("deprecation")
public class LevelManager extends Observable implements Observer
{
    private static LevelManager instance;

    // pause frames after collision between player and robot
    private static final int FRAMES_ENEMY_BLOCKING = 600; // 10 seconds (x/FPS)

    private static Map<String, Level<GameObject>> levels = new HashMap<>();
    private static final String PREFIX_LEVEL = "level";
    private int initialRow, currentRow;
    private int initialCol, currentCol;

    private Direction direction;

    private Elevator elevator;
    private Player player;

    private int framesEnemyBlocked;

    private int blockEnemyKey;
    private int resetLiftKey;
    private int hackerKey;

    /**
     * Get singleton instance
     * 
     * @return
     */
    public static LevelManager getInstance()
    {
        if (instance == null)
            instance = new LevelManager();
        return instance;
    }

    /**
     * private Constructor
     */
    private LevelManager()
    {
        // create the main elevator
        elevator = new Elevator(0, 0, 1);
    }

    /**
     * Create a level from file
     * 
     * @param jsonFilePath path where file is located in resource directory 
     */
    @SuppressWarnings("unchecked")
    public void createLevelFromJsonFile(String jsonFilePath)
    {
        InputStream is = LevelManager.class.getResourceAsStream(jsonFilePath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        JSONTokener tokener = new JSONTokener(bufferedReader);
        JSONObject json = new JSONObject(tokener);

        Iterator<String> iterLevels = json.keys();
        while (iterLevels.hasNext())
        {
            String levelName = iterLevels.next();

            Object objects = json.get(levelName);
            Level<GameObject> level = new Level<>(levelName);

            if (objects instanceof JSONObject)
            {
                Iterator<String> keyObject = ((JSONObject) objects).keys();
                while (keyObject.hasNext())
                {
                    String objstr = keyObject.next();
                    if (((JSONObject) objects).get(objstr) instanceof JSONArray)
                    {
                        JSONArray array = (JSONArray) ((JSONObject) objects).get(objstr);

                        if (objstr.equals("platforms"))
                        {
                            for (int i = 0; i < array.length(); i++)
                            {
                                JSONObject obj = (JSONObject) array.get(i);
                                Platform platform = new Platform((int) obj.get("x"), (int) obj.get("y"));
                                level.addGameObject(platform);
                            }
                        } else if (objstr.equals("enemies"))
                        {
                            for (int i = 0; i < array.length(); i++)
                            {
                                JSONObject obj = (JSONObject) array.get(i);
                                int start = 0;
                                int end = 0;
                                if (obj.has("startx"))
                                    start = obj.getInt("startx");
                                if (obj.has("startx"))
                                    end = obj.getInt("endx");
                                MoveStrategy moveStrategy = MoveStrategies.getStrategy(obj.getString("movement"), start,
                                        end);
                                AttackStrategy attackStrategy = AttackStrategies.getStrategy(obj.getString("attack"));

                                Direction direction = null;
                                if (obj.has("direction"))
                                    direction = Direction.getDirection(obj.getString("direction"));

                                Enemy enemy = null;
                                if (obj.has("type") && obj.get("type").equals("robot"))
                                    enemy = new Robot((int) obj.get("x"), (int) obj.get("y"), (int) obj.get("speed"),
                                            moveStrategy, attackStrategy);
                                if (obj.has("type") && obj.get("type").equals("bigbomb"))
                                    enemy = new BigBomb((int) obj.get("x"), (int) obj.get("y"), (int) obj.get("speed"),
                                            moveStrategy, attackStrategy);
                                if (enemy != null)
                                {
                                    if (direction != null)
                                        enemy.setDirection(direction);
                                    level.addGameObject(enemy);
                                }
                            }
                        } else if (objstr.equals("searchable"))
                        {
                            for (int i = 0; i < array.length(); i++)
                            {
                                JSONObject obj = (JSONObject) array.get(i);
                                SearchableObject searchable = new SearchableObject((int) obj.get("x"),
                                        (int) obj.get("y"), obj.getString("type"), obj.getString("badgetype"));
                                searchable.addObserver(this);
                                level.addGameObject(searchable);
                            }
                        } else if (objstr.equals("wall"))
                        {
                            for (int i = 0; i < array.length(); i++)
                            {
                                JSONObject obj = (JSONObject) array.get(i);
                                Wall wall = new Wall((int) obj.get("x"), (int) obj.get("y"));
                                level.addGameObject(wall);
                            }
                        } else if (objstr.equals("computer"))
                        {
                            for (int i = 0; i < array.length(); i++)
                            {
                                JSONObject obj = (JSONObject) array.get(i);
                                Computer computer = new Computer((int) obj.get("x"), (int) obj.get("y"));
                                level.addGameObject(computer);
                            }
                        } else if (objstr.equals("lifts"))
                        {
                            for (int i = 0; i < array.length(); i++)
                            {
                                JSONObject obj = (JSONObject) array.get(i);
                                JSONArray liftJson = (JSONArray) obj.get("lift");
                                Lift lift = new Lift();

                                for (int liftIndex = 0; liftIndex < liftJson.length(); liftIndex++)
                                {
                                    LiftFloor liftFloor = new LiftFloor(
                                            (int) liftJson.getJSONObject(liftIndex).get("x"),
                                            (int) liftJson.getJSONObject(liftIndex).get("y"),
                                            (int) liftJson.getJSONObject(liftIndex).get("nexty"), 1);
                                    lift.addLift(liftFloor);
                                }
                                level.addLift(lift);
                            }
                        } else if (objstr.equals("playerpoints"))
                        {
                            for (int i = 0; i < array.length(); i++)
                            {
                                JSONObject obj = (JSONObject) array.get(i);
                                PlayerPoint playerPoint = new PlayerPoint((int) obj.get("x"), (int) obj.get("y"));
                                level.addGameObject(playerPoint);
                            }
                        }
                    }
                    if (objstr.equals("elevator"))
                    {
                        JSONObject obj = (JSONObject) ((JSONObject) objects).get(objstr);
                        elevator.resetPosition((int) obj.get("x"), (int) obj.get("y"));
                        level.addGameObject(elevator);
                    }
                    if (objstr.equals("supercomputer"))
                    {
                        JSONObject obj = (JSONObject) ((JSONObject) objects).get(objstr);
                        SuperComputer superComputer = new SuperComputer((int) obj.get("x"), (int) obj.get("y"));
                        level.addGameObject(superComputer);
                    }
                }
            }
            levels.put(levelName, level);
        }

    }

    /**
     * Returns a map of game levels.
     * 
     * @return map of string and level gameobjects
     * 
     */
    public Map<String, Level<GameObject>> getLevels()
    {
        return levels;
    }

    /**
     * Returns the current level name
     * 
     * @return
     */
    public String getNameOfCurrentLevel()
    {
        return PREFIX_LEVEL + currentRow + currentCol;
    }

    /**
     * Returns how many block robot keys Player can use
     * 
     * @return
     */
    public int getBlockEnemyKey()
    {
        return blockEnemyKey;
    }

    /**
     * Returns how many hacker keys Player can use
     * 
     * @return
     */
    public int getHackerKey()
    {
        return hackerKey;
    }

    /**
     * Returns how many reset lift keys Player can use
     * 
     * @return
     */
    public int getResetLiftKey()
    {
        return resetLiftKey;
    }

    /**
     * Returns the remaining lives to play
     * 
     * @return
     */
    public int getLivesToPlay()
    {
        if (player == null)
            return 0;
        return player.getLives();
    }

    /**
     * Set the initial level variables
     * 
     * @param initialRow to set
     * @param initialCol to set
     */
    public void setInitialLevel(int initialRow, int initialCol)
    {
        this.initialRow = initialRow;
        this.initialCol = initialCol;
        currentRow = initialRow;
        currentCol = initialCol;
        setChanged();
        notifyObservers();
    }

    /**
     * Set the player in level Manager
     * 
     * @param player
     */
    public void setPlayer(Player player)
    {
        this.player = player;
    }

    /**
     * Returns the current level containing all its gameObjects
     * 
     * @return level
     */
    public Level<GameObject> getCurrentLevel()
    {
        return levels.get(getNameOfCurrentLevel());
    }

    /**
     * Method to move to upper floor
     * 
     * @param gapBetweenFloor
     */
    public void goUp(int gapBetweenFloor)
    {
        direction = Direction.UP;
        nextLevel(-1, 0);
        elevator.updateVerticalPosition(elevator.getY() + gapBetweenFloor);
        elevator.updateFloor(-1);

    }

    /**
     * Method to move to lower floor
     * 
     * @param gapBetweenFloor
     */
    public void goDown(int gapBetweenFloor)
    {
        direction = Direction.DOWN;
        nextLevel(1, 0);
        elevator.updateVerticalPosition(elevator.getY() - gapBetweenFloor);
        elevator.updateFloor(1);
    }

    /**
     * Method to change room
     * 
     */
    public void goLeft()
    {
        direction = Direction.LEFT;
        nextLevel(0, -1);
        AudioManager.getInstance().stopRunningClips();
    }

    /**
     * Method to change room
     * 
     */
    public void goRight()
    {
        direction = Direction.RIGHT;
        nextLevel(0, 1);
        AudioManager.getInstance().stopRunningClips();
    }

    // Method to set and prepare the next level
    private void nextLevel(int rowDelta, int colDelta)
    {
        currentRow += rowDelta;
        currentCol += colDelta;
        if (!levels.containsKey(PREFIX_LEVEL + currentRow + currentCol))
        {
            currentRow = initialRow;
            currentCol = initialCol;
            elevator.resetAllStatus();
        }
        // reset lifts when enter in a new room
        resetLifts();
        updateFollowers();
        notifyObservers();

    }

    /**
     * Return the PlayerPoint.
     * Used to reset the position of the player.
     * 
     * @return new player point
     */
    public PlayerPoint getNewPlayerPosition()
    {
        setChanged();
        notifyObservers();
        return (PlayerPoint) levels.get(PREFIX_LEVEL + currentRow + currentCol).getPlayerPoint(direction);
    }

    /**
     * Reset the position of the lifts in the current level
     */
    public void resetLiftPositions()
    {
        if (resetLiftKey > 0)
        {
            resetLiftKey--;
            resetLifts();
        }
    }

    // Reset lifts
    private void resetLifts()
    {
        getCurrentLevel().resetLiftPositions();
        setChanged();
        notifyObservers();
    }

    // Method to update the target position to enemy that use a follower
    private void updateFollowers()
    {
        getCurrentLevel().updateFollowers(player);
    }

    /**
     * Block enemies for FRAMES_ENEMY_BLOCKING seconds.
     */
    public void blockEnemies()
    {
        if (blockEnemyKey > 0)
        {
            blockEnemyKey--;
            framesEnemyBlocked = FRAMES_ENEMY_BLOCKING;
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Return how many levels have been completed
     * 
     * @return
     */
    public int getLevelCompleted()
    {
        int levelCompleted = 0;
        for (var levelMap : levels.entrySet())
        {
            levelCompleted += levelMap.getValue().isLevelCompleted() ? 1 : 0;
        }
        return levelCompleted;
    }

    /**
     * method called when an observed object notifies a change of state
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if (framesEnemyBlocked < 0)
        {
            levels.get(getNameOfCurrentLevel()).moveEnemies();
            levels.get(getNameOfCurrentLevel()).attackEnemies();
        }
        levels.get(getNameOfCurrentLevel()).updateDynamicObjects();
        framesEnemyBlocked--;
        if (o instanceof SearchableObject && arg instanceof Badge)
        {
            Badge badge = (Badge) arg;
            if (badge.getBadgeType() == Badge.BadgeType.BLOCK_ENEMY)
                blockEnemyKey++;
            if (badge.getBadgeType() == Badge.BadgeType.LIFT_RESET)
                resetLiftKey++;
            if (badge.getBadgeType() == Badge.BadgeType.HACKER_KEY)
                hackerKey++;
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Adds Observer to gameobjects
     * 
     * @param observer 
     * 
     */
    public void addObserverToGameObjects(Observer observer)
    {
        levels.values().stream()
                .forEach(level -> level.getGameObjects().stream()
                        .filter(go -> go instanceof DynamicObject)
                        .forEach(dynobj -> dynobj.addObserver(observer)));

        elevator.addObserver(observer);

    }

}
