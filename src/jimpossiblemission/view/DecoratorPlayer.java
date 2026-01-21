package jimpossiblemission.view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import jimpossiblemission.model.game.Direction;
import jimpossiblemission.model.game.Player;
import jimpossiblemission.model.game.SpriteAnimation;

public class DecoratorPlayer extends DecoratorObject
{

    String hitBoxCsv;
    List<SpriteAnimation> spriteAnimation = new ArrayList<SpriteAnimation>();
    int spriteSizeX, spriteSizeY;
    int numberOSprite;
    int offsetX, offsetY;
    int currentSpriteNumber = -1;
    int delayFrames = 3;
    int tempDelayFrame = 0;
    Image currentSprite;

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
                sa.setImage(ImageIO.read(getClass().getResourceAsStream(path + fields[0])));
                sa.setX(Integer.valueOf(fields[1]));
                sa.setY(Integer.valueOf(fields[2]));
                sa.setW(Integer.valueOf(fields[3]));
                sa.setH(Integer.valueOf(fields[4]));
                spriteAnimation.add(sa);
            }
        } catch (IOException e)
        {
            throw new IOException(e.getMessage());
        }
    }

    public void draw(Graphics2D g2)
    {
        int x = (int) gameObject.getX();
        int y = (int) gameObject.getY();
        int w = tileSize;
        int h = tileSize;
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
            if (((Player) gameObject).getDirection() == Direction.LEFT)
            {
                x = (int) gameObject.getX() + w;
                w = -w;
            }
            g2.drawImage(sa.getImage(), x, y, w, h, null);
        } else
            g2.drawImage(getHold(), x, y, w, h, null);
    }

    public SpriteAnimation getCurrent()
    {
        return spriteAnimation.get(currentSpriteNumber);
    }

    public SpriteAnimation getNext()
    {
        currentSpriteNumber++;
        if (currentSpriteNumber >= spriteAnimation.size())
            currentSpriteNumber = 0;
        return spriteAnimation.get(currentSpriteNumber);
    }

    public Image getHold()
    {
        currentSpriteNumber = 0;
        currentSprite = spriteAnimation.get(currentSpriteNumber).getImage();
        return currentSprite;
    }

}
