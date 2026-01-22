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

import jimpossiblemission.model.game.Player;
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
    public DecoratorPlayer(Player gameObject, String hitBoxCsv, String path) throws IOException
    {
        super(gameObject);

        var fileEntries = new ArrayList<String>();
        InputStream inputStream = DecoratorPlayer.class.getResourceAsStream(path + hitBoxCsv);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream)))
        {

            String line;
            while ((line = br.readLine()) != null)
            {
                fileEntries.add(line);
            }
            fileEntries.remove(0);

            for (var entry : fileEntries)
            {
                String[] fields = entry.split(",");
                SpriteAnimation sa = new SpriteAnimation();
                BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path + fields[0]));
                sa.setImage(image, image.getWidth(null) * scale, image.getHeight(null) * scale);

                spriteRunningAnimation.add(sa);
            }

        } catch (IOException e)
        {
            throw new IOException(e.getMessage());
        }
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
