package jimpossiblemission.view.gameobject;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import jimpossiblemission.model.entity.GameObject;
import jimpossiblemission.view.game.SpriteAnimation;
import jimpossiblemission.view.game.SpriteFactory;

/**
 * StatiObject view
 * 
 * @author Filippo Taiuti
 */
public abstract class StaticObjectView extends GameObjectView
{
    private SpriteAnimation spriteAnimation;
    private Image currentSprite;

    /**
     * Comstructor of StaticObjectView
     * 
     * @param gameObject to show in View
     * @param hitBoxcsv 
     * @param pathResource
     */
    public StaticObjectView(GameObject gameObject, String hitBoxcsv, String pathResource)
    {
        super(gameObject);
        try
        {
            setSpriteAnimation(hitBoxcsv, pathResource);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Set sprite animation
     * 
     * @param hitBoxCsv
     * @param path where hitBoxCsv is
     * @throws IOException
     */
    private void setSpriteAnimation(String hitBoxCsv, String path) throws IOException
    {
        spriteAnimation = SpriteFactory.getSprite(path, hitBoxCsv);
    }

    public SpriteAnimation getSpriteAnimation()
    {
        return spriteAnimation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics2D g2)
    {
        int x = gameObject.getX();
        int y = gameObject.getY();

        width = spriteAnimation.getImage().getWidth();
        height = spriteAnimation.getImage().getHeight();
        g2.drawImage(spriteAnimation.getImage(), x, y, width, height, null);// ,
                                                                            // Color:YELLOW,
                                                                            // null);
    }

    public Image getCurrent()
    {
        return currentSprite;
    }

}
