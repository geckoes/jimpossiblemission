package jimpossiblemission.controller.game;

import java.util.Observable;
import java.util.Observer;

import jimpossiblemission.model.entity.DynamicObject;
import jimpossiblemission.view.game.SpriteManager;

/**
 * Class Animation Manager
 * 
 * @author Filippo Taiuti
 *
 */
@SuppressWarnings("deprecation")
public class AnimationManager implements Observer
{
    private DynamicObject dynamicObject;
    private SpriteManager spriteManager;

    private String lastState;
    private int counterNextSprite;
    private int nextSprite;

    /**
     * Class costructor requires DynamicObject and Sprite Manager
     * 
     * @param dynamicObject
     * @param spriteManager
     */
    public AnimationManager(DynamicObject dynamicObject, SpriteManager spriteManager)
    {
        this.dynamicObject = dynamicObject;
        this.spriteManager = spriteManager;
        nextSprite = 4;
    }

    /**
     * method called when an observed object notifies a change of state
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if (!dynamicObject.getState().toString().equals(lastState))
        {
            lastState = dynamicObject.getState();
            spriteManager.setNewSprite(dynamicObject.getState());
        } else
        {
            counterNextSprite++;
            if (counterNextSprite >= nextSprite)
            {
                counterNextSprite = 0;
                spriteManager.setNextSprite(dynamicObject.getState());
            }
        }
        dynamicObject.getCollider().updateCollider(spriteManager.getCurrentSpriteAnimation().getHitBox());
    }

    /**
     * Returns true if animation is completed
     * 
     * @return boolean
     */
    public boolean isAnimationEnded()
    {
        return spriteManager.isEnded();
    }
}
