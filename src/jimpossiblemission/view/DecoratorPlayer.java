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
    // SCREEN SETTINGS
    public final static int originalTileSize = 32;
    public final static int scale = 4;

    public final static int tileSize = originalTileSize * scale;
    public final static int spriteSize = originalTileSize * scale;

    String hitBoxCsv;
    List<SpriteAnimation> spriteAnimation = new ArrayList<SpriteAnimation>();
    int spriteSizeX, spriteSizeY;
    int numberOSprite;
    int offsetX, offsetY;
    int currentSpriteNumber = -1;
    int delayFrames = 4;
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
        InputStream inputStream = getClass().getResourceAsStream(path + hitBoxCsv);
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
        int w = spriteSize;
        int h = spriteSize;
        if (gameObject.isMoving())
        {
            if (((Player) gameObject).getDirection() == Direction.LEFT)
            {
                x = x + w;
                w = -w;
            }
            if (tempDelayFrame >= delayFrames)
            {
                g2.drawImage(getNext(), x, y, w, h, null);
                tempDelayFrame = 0;
            } else
            {
                tempDelayFrame++;
            }
            g2.drawImage(getCurrent(), x, y, w, h, null);
        } else
            g2.drawImage(getHold(), x, y, w, h, null);
    }

    public Image getCurrent()
    {
        return currentSprite;
    }

    public Image getNext()
    {
        currentSpriteNumber++;
        if (currentSpriteNumber >= spriteAnimation.size())
            currentSpriteNumber = 0;
        currentSprite = spriteAnimation.get(currentSpriteNumber).getImage();
        return currentSprite;
    }

    public Image getHold()
    {
        currentSpriteNumber = 0;
        currentSprite = spriteAnimation.get(currentSpriteNumber).getImage();
        return currentSprite;
    }

}
