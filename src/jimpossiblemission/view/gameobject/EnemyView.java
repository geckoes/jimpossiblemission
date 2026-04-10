package jimpossiblemission.view.gameobject;

import jimpossiblemission.exception.GameObjectViewCreationException;
import jimpossiblemission.model.entity.Enemy;
import jimpossiblemission.view.gameobject.strategy.AttackStrategyView;

/**
 * Enemy View
 * 
 * @author Filippo Taiuti
 *
 */
public class EnemyView extends StaticObjectView
{

    protected AttackStrategyView attackStrategyView;

    /**
     * Constructor of Enemy View
     * 
     * @param gameObject as Enemy
     */
    public EnemyView(Enemy gameObject, String hitbox, String pathResource)
    {
        super(gameObject, hitbox, pathResource);
        try
        {
            attackStrategyView = GameObjectViewFactory.createAttackStrategyView(gameObject.getAttackStrategy());
        } catch (GameObjectViewCreationException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
