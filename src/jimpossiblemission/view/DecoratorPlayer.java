package jimpossiblemission.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.imageio.ImageIO;

import jimpossiblemission.model.game.BoxCollider;
import jimpossiblemission.model.game.Player;
import jimpossiblemission.model.game.ShapeCollider;
import jimpossiblemission.model.game.SpriteAnimation;
import jimpossiblemission.model.game.controllers.Direction;

public class DecoratorPlayer extends DecoratorObject
{
    List<SpriteAnimation> spriteRunningAnimation = new ArrayList<SpriteAnimation>();
    List<SpriteAnimation> spriteSearchingAnimation = new ArrayList<SpriteAnimation>();
    List<SpriteAnimation> spriteStandingAnimation = new ArrayList<SpriteAnimation>();
    List<SpriteAnimation> spriteJumpingAnimation = new ArrayList<SpriteAnimation>();

    int numberOSprite;
    int currentSpriteNumber = 0;
    int delayFrames = 3;
    int tempDelayFrame = 0;

    /**
     * @param gameObject
     * @throws IOException
     */
    public DecoratorPlayer(Player gameObject) throws IOException
    {
        super(gameObject);

        spriteRunningAnimation = super.createSpriteAnimation("hitboxes.csv", "/Sprites/Player/Running/");
        spriteSearchingAnimation = super.createSpriteAnimation("hitboxes.csv", "/Sprites/Player/Searching/");
        spriteStandingAnimation = super.createSpriteAnimation("hitboxes.csv", "/Sprites/Player/Standing/");
        spriteJumpingAnimation = super.createSpriteAnimation("hitboxes.csv", "/Sprites/Player/Jumping/");
    }

    public void draw(Graphics2D g2)
    {
        int x = gameObject.getX();
        int y = gameObject.getY();
        SpriteAnimation sa;
        if (gameObject.isMoving())
        {
            if (tempDelayFrame >= delayFrames)
            {
                sa = getNext();
                tempDelayFrame = 0;
            } else
            {
                sa = getCurrent();
                tempDelayFrame++;
            }
            width = sa.getWidth();
            if (((Player) gameObject).getDirection() == Direction.LEFT)
            {
                x = (int) gameObject.getX() + width;
                width = -width;
            }

        } else if (((Player) gameObject).isSearching())
        {
            sa = spriteRunningAnimation.get(0);
            width = sa.getWidth();

        } else
        {
            sa = spriteRunningAnimation.get(0);
            width = sa.getWidth();
        }
        g2.drawImage(sa.getImage(), x, y, width, sa.getHeight(), Color.WHITE, null);
        setChanged();
        notifyObservers(this);
        System.out.println("Player " + gameObject.getX() + " " + gameObject.getY());
    }

    public SpriteAnimation getCurrent()
    {
        return spriteRunningAnimation.get(currentSpriteNumber);
    }

    public SpriteAnimation getNext()
    {
        currentSpriteNumber++;
        if (currentSpriteNumber >= spriteRunningAnimation.size())
            currentSpriteNumber = 0;
        return spriteRunningAnimation.get(currentSpriteNumber);
    }

    @Override
    public void update(Observable o, Object arg)
    {
    }


}
