package jimpossiblemission.view.gameobject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import jimpossiblemission.exception.GameObjectViewCreationException;
import jimpossiblemission.model.entity.GameObject;
import jimpossiblemission.model.entity.strategy.AttackStrategy;
import jimpossiblemission.view.gameobject.strategy.AttackStrategyView;

/**
 * Creator of proper View starting from object or interface class.
 * It uses java reflection to create new instance of gameObjectView
 *  
 * @author Filippo Taiuti
 *
 */
public class GameObjectViewFactory
{
    private static final String VIEW_PACKAGE = "jimpossiblemission.view.gameobject.";
    private static final String VIEW_PACKAGE_STRATEGY = "jimpossiblemission.view.gameobject.strategy.";

    /**
     * Creates a new instance of gameObjectView starting from passed gameObject
     * 
     * @param go gameObject to see in game 
     * @return gameObjectView view of gameObject
     * @throws GameObjectViewCreationException
     */
    public static GameObjectView createGameObjectView(GameObject go) throws GameObjectViewCreationException
    {
        // get the name of the class of go
        String className = go.getClass().getSimpleName();

        // create the name of the View
        try
        {
            Class<?> viewClass = Class.forName(VIEW_PACKAGE + className + "View");
            Constructor<?> constructor = viewClass.getConstructor(go.getClass());
            return (GameObjectView) constructor.newInstance(go);
        } catch (ClassNotFoundException e)
        {
            throw new GameObjectViewCreationException("No View found for " + className);
        } catch (NoSuchMethodException | SecurityException e)
        {
            throw new GameObjectViewCreationException("Error found in constructor of " + className + "View");
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e)
        {
            throw new GameObjectViewCreationException("No View found for " + className);
        }
    }

    /**
     * Creates a new instance of gameObjectView starting from passed attackStrategy (interface)
    
     * @param attackStrategy
     * @return attackStrategy view of attack strategy
     * @throws GameObjectViewCreationException
     */
    public static AttackStrategyView createAttackStrategyView(AttackStrategy attackStrategy)
            throws GameObjectViewCreationException
    {
        // get the name of the class of go
        String className = attackStrategy.getClass().getSimpleName();

        // create the name of the View
        try
        {
            Class<?> viewClass = Class.forName(VIEW_PACKAGE_STRATEGY + className + "View");
            Constructor<?> constructor = viewClass.getConstructor(AttackStrategy.class);
            return (AttackStrategyView) constructor.newInstance(attackStrategy);
        } catch (ClassNotFoundException e)
        {
            throw new GameObjectViewCreationException("No View found for " + className);
        } catch (NoSuchMethodException | SecurityException e)
        {
            throw new GameObjectViewCreationException("Error found in constructor of " + className + "View");
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e)
        {
            throw new GameObjectViewCreationException("No View found for " + className);
        }
    }

}
