package jimpossiblemission.view.gameobject;

import java.awt.Graphics2D;
import java.util.Optional;

import jimpossiblemission.model.entity.Direction;
import jimpossiblemission.model.entity.Player;
import jimpossiblemission.model.entity.Player.PlayerState;
import jimpossiblemission.view.game.SpriteAnimation;
import jimpossiblemission.view.game.SpriteFactory;
import jimpossiblemission.view.game.SpriteManager;

/**
 * Player View
 * 
 * @author Filippo Taiuti
 */
public class PlayerView extends GameObjectView
{
    private Optional<SpriteAnimation> currentSpriteAnimation = Optional.empty();
    private SpriteManager animationManager;

    /**
     * Constructor of Player View
     * 
     * @param gameObject as Player
     * @param animationManager
     */
    public PlayerView(Player gameObject, SpriteManager animationManager)
    {
        super(gameObject);
        SpriteFactory.fillSpriteManager(animationManager, "/Sprites/Player/", "hitboxes.csv", PlayerState.values());
        this.animationManager = animationManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics2D g2)
    {
        int x = gameObject.getX();
        int y = gameObject.getY();

        currentSpriteAnimation = Optional.ofNullable(animationManager.getCurrentSpriteAnimation());
        if (currentSpriteAnimation.isPresent())
        {
            SpriteAnimation sa = currentSpriteAnimation.get();
            width = sa.getImage().getWidth();
            if (((Player) gameObject).getDirection() == Direction.LEFT
                    || ((Player) gameObject).getLastDirection() == Direction.LEFT)
            {
                x = (int) gameObject.getX() + width;
                width = -width;
            }
            g2.drawImage(sa.getImage(), x, y, width, sa.getImage().getHeight(), null, null);
        }
    }

    /**
     * set the current sprite to show
     * 
     * @param sprite animation
     */
    public void setCurrentSprite(SpriteAnimation sprite)
    {
        currentSpriteAnimation.orElse(sprite);
    }

}
