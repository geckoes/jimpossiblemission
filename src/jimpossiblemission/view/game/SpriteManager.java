package jimpossiblemission.view.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SpriteManager manages the sprite to show in animation
 * 
 * @author Filippo Taiuti
 *
 */
public class SpriteManager
{
    private Map<String, List<SpriteAnimation>> mapSpriteAnimation;
    private int currentIndex;

    private SpriteAnimation currentSprite;

    /**
     * Construction of SpriteManager.
     * Initialize an Map of animationName and list of sprite animation
     */
    public SpriteManager()
    {
        mapSpriteAnimation = new HashMap<String, List<SpriteAnimation>>();
    }

    /**
     * Add a sprite animation in map 
     * 
     * @param animationName the name of sprite animation
     * @param anim the sprite animation
     */
    public void addSpriteAnimation(String animationName, SpriteAnimation anim)
    {
        if (!mapSpriteAnimation.containsKey(animationName))
            mapSpriteAnimation.put(animationName, new ArrayList<SpriteAnimation>());
        mapSpriteAnimation.get(animationName).add(anim);
    }

    /**
     * set the next sprite to show
     * 
     * @param animationName
     */
    public void setNextSprite(String animationName)
    {
        currentIndex++;
        if (currentIndex >= mapSpriteAnimation.get(animationName).size())
            currentIndex = 0;
        currentSprite = mapSpriteAnimation.get(animationName).get(currentIndex);
    }

    /**
     * Set a new sprite to show
     *  
     * @param animationName
     */
    public void setNewSprite(String animationName)
    {
        currentIndex = 0;
        currentSprite = mapSpriteAnimation.get(animationName).get(currentIndex);
    }

    /**
     * Returns a list of spriteAnimation of animationName
     * @param animationName the name of animation
     * @return list of spriteAnimation
     */
    public List<SpriteAnimation> getListSpriteAnimation(String animationName)
    {
        return mapSpriteAnimation.get(animationName);
    }

    /**
     * Get the current spriteAnimation
     * 
     * @return sprite animation
     */
    public SpriteAnimation getCurrentSpriteAnimation()
    {
        return currentSprite;
    }

    /**
     * Set the end of the current animation 
     * @return
     */
    public boolean isEnded()
    {
        return currentIndex == 0;
    }

}
