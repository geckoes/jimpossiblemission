package jimpossiblemission.model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jimpossiblemission.model.entity.Direction;
import jimpossiblemission.model.entity.DynamicObject;
import jimpossiblemission.model.entity.Enemy;
import jimpossiblemission.model.entity.GameObject;
import jimpossiblemission.model.entity.Lift;
import jimpossiblemission.model.entity.LiftFloor;
import jimpossiblemission.model.entity.Player;
import jimpossiblemission.model.entity.PlayerPoint;
import jimpossiblemission.model.entity.strategy.FollowStrategy;

/**
 * Level model contains all GameObject that play in the level.
 * It use Generics T extends GameObject
 * 
 * @author Filippo Taiuti
 * @param <T> extends GameObject
 */
public class Level<T extends GameObject>
{
    private String name;
    private List<Lift> lifts;
    private boolean completed;
    private List<T> listGameObject;

    /**
     * Constructor Level
     * 
     * @param name
     */
    public Level(String name)
    {
        this.name = name;
        lifts = new ArrayList<>();
        listGameObject = new ArrayList<T>();
        completed = false;
    }

    /**
     * Returns the name of the level
     * 
     * @return level name
     */
    public String getName()
    {
        return name;
    }

    /** 
     * Set when the level is completed
     */
    public void setLevelCompleted()
    {
        completed = true;
    }

    /**
     * Tells if the level is completed
     * 
     * @return boolean level completed
     */
    public boolean isLevelCompleted()
    {
        return completed;
    }

    /**
     * Adds GameObject to Level
     * 
     * @param gameObject to add
     */
    public void addGameObject(T gameObject)
    {
        listGameObject.add(gameObject);
    }

    /**
     * Add lift to level
     * @param lift
     */
    @SuppressWarnings("unchecked")
    public void addLift(Lift lift)
    {
        for (LiftFloor liftFloor : lift.getLifts())
        {
            listGameObject.add((T) liftFloor);
        }
        lifts.add(lift);
    }

    /**
     *  Gets the list of gameObjects set in level
     *  
     *  @return list of gameObjects
     */
    public List<T> getGameObjects()
    {
        return listGameObject;
    }

    /**
     *  Gets the list of lifts set in level
     *  
     *  @return list of lifts
     */
    public List<Lift> getLifts()
    {
        return lifts;
    }

    /**
     * Gets the gameObject instance of playerPoint set in level
     * that checks with direction  
     *  
     *  @param direction of playerPoint
     *  @return list of gameObjects
     */
    public T getPlayerPoint(Direction direction)
    {
        List<T> gos = listGameObject.stream() // Stream<List<GameObject>>
                .filter(dynObjs -> dynObjs instanceof PlayerPoint && dynObjs.isActive()) // Stream<List<PlayerPoint>>
                .collect(Collectors.toList());
        if (direction == Direction.RIGHT)
            return gos.getFirst();
        return gos.getLast();

    }

    /**
     * send the input to update dynamicObjects.
     * Used to move dynamicObjects (except Enemies)
     */
    public void updateDynamicObjects()
    {
        listGameObject.stream() // Stream<List<GameObject>>
                .filter(dynObjs -> (dynObjs instanceof DynamicObject && !(dynObjs instanceof Enemy))
                        && dynObjs.isActive()) // Stream<List<DynamicObject>>
                .forEach(dynObj ->
                {
                    ((DynamicObject) dynObj).move();
                });
    }

    /**
     * Send the command to attack to all enemies
     */
    public void attackEnemies()
    {
        listGameObject.stream() // Stream<List<GameObject>>
                .filter(dynObjs -> dynObjs instanceof Enemy && dynObjs.isActive()) // Stream<List<DynamicObject>>
                .forEach(dynObj ->
                {
                    ((Enemy) dynObj).attack();
                });
    }

    /**
     * Send the command to move to all enemies
     */

    public void moveEnemies()
    {
        listGameObject.stream() // Stream<List<GameObject>>
                .filter(dynObjs -> dynObjs instanceof Enemy && dynObjs.isActive()) // Stream<List<DynamicObject>>
                .forEach(dynObj -> ((Enemy) dynObj).move());
    }

    /**
     * Send the command to reset the position to all dynamicObjects
     */
    public void resetPositions()
    {
        listGameObject.stream() // Stream<List<GameObject>>
                .filter(dynObjs -> dynObjs instanceof DynamicObject && dynObjs.isActive()) // Stream<List<DynamicObject>>
                .forEach(dynObj -> ((DynamicObject) dynObj).resetAllStatus());
    }

    /**
     * Send the command to reset the position to all liftfloor
     */
    public void resetLiftPositions()
    {
        listGameObject.stream() // Stream<List<GameObject>>
                .filter(dynObjs -> dynObjs instanceof LiftFloor && dynObjs.isActive()) // Stream<List<LiftFloor>>
                .forEach(dynObj -> ((DynamicObject) dynObj).resetAllStatus());
    }

    /**
     * updates target to followers
     * 
     * @param player as target to follow
     */
    public void updateFollowers(Player player)
    {
        listGameObject.stream() // Stream<List<GameObject>>
                .filter(dynObjs -> dynObjs instanceof Enemy && dynObjs.isActive()) // Stream<List<DynamicObject>>
                .filter(enemies -> ((Enemy) enemies).getMoveStrategy() instanceof FollowStrategy) // Stream<List<Enemy>>
                .forEach(enemyStrategy -> ((FollowStrategy) ((Enemy) enemyStrategy).getMoveStrategy())
                        .setTarget(player));
    }

}
