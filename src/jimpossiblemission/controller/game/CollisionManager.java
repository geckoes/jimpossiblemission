package jimpossiblemission.controller.game;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

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
import jimpossiblemission.model.entity.SearchableObject;
import jimpossiblemission.model.entity.Wall;
import jimpossiblemission.model.game.Level;

/**
 * Class used to manage Collisions between GameObjects
 * 
 * @author Filippo Taiuti
 *
 */
@SuppressWarnings("deprecation")
public class CollisionManager implements Observer
{
    private static CollisionManager instance;
    private static Level<GameObject> currentLevel;

    public static CollisionManager getInstance()
    {
        if (instance == null)
            instance = new CollisionManager();
        return instance;
    }

    private CollisionManager()
    {
    }

    /**
     * Method called when an observed object notifies a change of state
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if (o == null && arg == null)
            return;
        if (o instanceof LevelManager)
        {
            currentLevel = ((LevelManager) o).getCurrentLevel();
        }
        if (o instanceof DynamicObject)
        {
            checkCollision((DynamicObject) o);
        }
    }

    /**
     * Method used to check if dynamicObject collides against another object loaded
     * in the current level
     * 
     * @param dynamicObject
     */
    public void checkCollision(DynamicObject dynamicObject)
    {
        checkCollisionWithPlatforms(dynamicObject, currentLevel.getGameObjects());
        checkCollisionWithLifts(dynamicObject, currentLevel.getLifts());
        checkCollisionWithWalls(dynamicObject, currentLevel.getGameObjects());
        checkCollisionWithSearchable(dynamicObject, currentLevel.getGameObjects());
        checkCollisionWithComputers(dynamicObject, currentLevel.getGameObjects());
        checkCollisionWithElevator(dynamicObject, currentLevel.getGameObjects());
        if (dynamicObject instanceof Player)
            checkCollisionWithEnemies((Player) dynamicObject, currentLevel.getGameObjects());

    }

    /**
     * check when the GameObject collides with one platform
     *
     * @param goView
     * @return
     */
    private void checkCollisionWithPlatforms(DynamicObject dynObj, List<GameObject> gameObjects)
    {
        dynObj.notOnGround();

        Optional<GameObject> platform = gameObjects.stream() // Stream<List<GameObject>>
                .filter(go -> go instanceof Platform && go.isActive()) // Stream<List<Platfrom>>
                .filter(p -> dynObj.getCollider().getRightBound() > p.getCollider().getLeftBound()
                        && dynObj.getCollider().getLeftBound() < p.getCollider().getRightBound()
                        && dynObj.getCollider().getBottomBound() >= p.getCollider().getTopBound()
                        && dynObj.getCollider().getBottomBound() < p.getCollider().getBottomBound() - 4) // Stream<List<Platform>>
                .findFirst(); // Platform
        if (platform.isPresent())
            dynObj.grounded(platform.get());
    }

    /**
     * check when the GameObject collides with one platform
     *
     * @param goView
     * @return
     */
    private void checkCollisionWithWalls(DynamicObject dynObj, List<GameObject> gameObjects)
    {
        Optional<GameObject> wall = gameObjects.stream() // Stream<List<GameObject>>
                .filter(go -> go instanceof Wall && go.isActive()) // Stream<List<Wall>>
                .filter(p -> dynObj.getCollider().getRightBound() > p.getCollider().getLeftBound()
                        && dynObj.getCollider().getLeftBound() < p.getCollider().getRightBound()
                        && dynObj.getCollider().getBottomBound() > p.getCollider().getTopBound()
                        && dynObj.getCollider().getTopBound() < p.getCollider().getBottomBound()) // Stream<List<Wall>>
                .findFirst(); // Wall
        if (wall.isPresent())
            dynObj.touchedSolidObstacle(wall.get());
    }

    /**
     * check when the GameObject collides with one lift
     *
     * @param goView
     * @return
     */
    private void checkCollisionWithLifts(DynamicObject dynObj, List<Lift> lifts)
    {
        for (Lift lift : lifts)
        {
            Optional<LiftFloor> liftTouched = lift.getLifts().stream() // Stream<List<LiftFloor>>
                    .filter(go -> go instanceof LiftFloor && go.isActive()) // Stream<List<LiftFloor>>
                    .filter(p -> dynObj.getCollider().getLeftBound() > p.getCollider().getLeftBound()
                            && dynObj.getCollider().getRightBound() < p.getCollider().getRightBound()
                            && dynObj.getCollider().getBottomBound() >= p.getCollider().getTopBound()
                            && dynObj.getCollider().getBottomBound() < p.getCollider().getBottomBound()) // Stream<List<LiftFloor>>
                    .findFirst(); // LiftFloor
            if (liftTouched.isPresent())
            {
                dynObj.grounded(liftTouched.get());
                lift.move(dynObj.getDirection());
            }
        }
    }

    /**
     * check when the GameObject collides with one Enemy
     *
     * @param goView
     * @return
     */
    private void checkCollisionWithEnemies(Player player, List<GameObject> gameObjects)
    {
        Optional<GameObject> enemy = gameObjects.stream() // Stream<List<GameObject>>
                .filter(go -> go instanceof Enemy && go.isActive()) // Stream<List<Enemy>>
                .filter(e -> player.getCollider().intersects(e.getCollider())
                        && e.getCollider().intersects(player.getCollider())
                        || ((Enemy) e).getAttackStrategy().isColliding(player.getCollider())) // Stream<List<Enemy>>
                .findFirst(); // Enemy
        if (enemy.isPresent())
            player.takeDamage(Player.PlayerState.Electrified);
    }

    /**
     * check when the GameObject collides with one object
     *
     * @param goView
     * @return
     */
    private void checkCollisionWithSearchable(DynamicObject dynObj, List<GameObject> gameObjects)
    {
        Optional<GameObject> searchable = gameObjects.stream() // Stream<List<GameObject>>
                .filter(go -> go instanceof SearchableObject && go.isActive()) // Stream<List<Searchable>>
                .filter(p -> dynObj.getCollider().getRightBound() > p.getCollider().getLeftBound()
                        && dynObj.getCollider().getLeftBound() < p.getCollider().getRightBound()
                        && dynObj.getCollider().getBottomBound() > p.getCollider().getTopBound()
                        && dynObj.getCollider().getTopBound() < p.getCollider().getBottomBound()) // Stream<List<Searchable>>
                .findFirst(); // Searchable
        if (searchable.isPresent())
            if (dynObj instanceof Player && dynObj.getDirection() == Direction.UP)
            {
                ((Player) dynObj).search();
                ((SearchableObject) searchable.get()).searchBadge();
                if (!searchable.get().isActive())
                {
                    long remaining_searchable = gameObjects.stream() // Stream<List<GameObject>>
                            .filter(go -> go instanceof SearchableObject && go.isActive()) // Stream<List<Searchable>>
                            .count();
                    if (remaining_searchable == 0)
                        currentLevel.setLevelCompleted();
                }
            }
    }

    /**
     * check when the GameObject collides with one computer
     *
     * @param goView
     * @return
     */
    private void checkCollisionWithComputers(DynamicObject dynObj, List<GameObject> gameObjects)
    {
        Optional<GameObject> computer = gameObjects.stream() // Stream<List<GameObject>>
                .filter(go -> go instanceof Computer && go.isActive()) // Stream<List<Computer>>
                .filter(p -> dynObj.getCollider().getRightBound() > p.getCollider().getLeftBound()
                        && dynObj.getCollider().getLeftBound() < p.getCollider().getRightBound()
                        && dynObj.getCollider().getBottomBound() > p.getCollider().getTopBound()
                        && dynObj.getCollider().getTopBound() < p.getCollider().getBottomBound()) // Stream<List<Computer>>
                .findFirst(); // Computer
        if (computer.isPresent())
            if (dynObj instanceof Player && dynObj.getDirection() == Direction.UP)
                ((Player) dynObj).hacking(((Computer) computer.get()).getTypeOfComputer());
    }

    /**
     * check when the GameObject collides with the elevator
     *
     * @param goView
     * @return
     */
    private void checkCollisionWithElevator(DynamicObject dynObj, List<GameObject> gameObjects)
    {
        Optional<GameObject> elevator = gameObjects.stream() // Stream<List<GameObject>>
                .filter(go -> go instanceof Elevator && go.isActive()) // Stream<List<Elevator>>
                .filter(p -> dynObj.getCollider().getLeftBound() > p.getCollider().getLeftBound()
                        && dynObj.getCollider().getRightBound() < p.getCollider().getRightBound()
                        && dynObj.getCollider().getBottomBound() >= p.getCollider().getTopBound()
                        && dynObj.getCollider().getBottomBound() < p.getCollider().getBottomBound()) // Stream<List<Elevator>>
                .findFirst(); // Elevator
        if (elevator.isPresent() && dynObj instanceof Player)
        {
            ((Elevator) elevator.get()).setPlayerInElevator((Player) dynObj);
        }

    }

}
