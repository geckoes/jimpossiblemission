/**
 * 
 */
package jimpossiblemission.view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import jimpossiblemission.model.game.BoxCollider;
import jimpossiblemission.model.game.GameObject;
import jimpossiblemission.model.game.ShapeCollider;

/**
 * @author Filippo Taiuti
 *
 */
@SuppressWarnings({ "deprecation" })
public abstract class DecoratorObject extends GameObject implements Observer
{
    // SCREEN SETTINGS
    protected final static int tileSize = 32;
    protected final static int scale = 3;
    protected int width;
    protected int height;
    
    protected GameObject gameObject;

    public DecoratorObject(GameObject gameObject)
    {
        this.gameObject = gameObject;
        this.width = tileSize * scale;
        this.height = tileSize * scale;
    }

    public GameObject getGameObject()
    {
        return gameObject;
    }
    
    public abstract void draw(Graphics2D g);

    /**
     * Return a list of animations
     * @param hitBoxCsv
     * @param path
     * @return list<SpriteAnimation>
     * @throws IOException
     */
	public List<SpriteAnimation> createSpriteAnimation(String hitBoxCsv, String path) throws IOException {
		List<SpriteAnimation> list = new ArrayList<SpriteAnimation>();
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
                BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path + fields[0]));
                BoxCollider sc = new BoxCollider(Integer.valueOf(fields[1]),Integer.valueOf(fields[2]),Integer.valueOf(fields[3]),Integer.valueOf(fields[4]));
                SpriteAnimation sa = new SpriteAnimation(image, sc);
                list.add(sa);
            }

        } catch (IOException e)
        {
            throw new IOException(e.getMessage());
        }
		return list;
	}

}
