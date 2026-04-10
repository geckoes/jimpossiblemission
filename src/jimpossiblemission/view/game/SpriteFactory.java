/**
 * 
 */
package jimpossiblemission.view.game;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import jimpossiblemission.model.entity.HitBox;

/**
 * Sprite Factory used to cache images of spriteAnimation
 * to avoid to load same image similar GameObjectView in game. 
 * 
 * @author Filippo Taiuti
 *
 */
public class SpriteFactory
{
    private static final Map<String, List<SpriteAnimation>> cache = new HashMap<>();

    /**
     * Return a sprite for staticGameObjects that have only one sprite
     * 
     * @param basePath
     * @return
     */
    public static SpriteAnimation getSprite(String basePath, String resourceHitBoxCsv)
    {
        return getSprites(basePath, resourceHitBoxCsv).getFirst();
    }

    /**
     * Return a list of sprites for dynamicGameObjects
     * 
     * @param basePath
     * @return
     */
    public static List<SpriteAnimation> getSprites(String basePath, String resourceHitBoxCsv)
    {
        String fullPath = basePath + resourceHitBoxCsv;
        if (!cache.containsKey(basePath))
        {
            try
            {
                cache.put(basePath, loadResources(basePath, resourceHitBoxCsv));
            } catch (IOException e)
            {
                System.err.println("Errore caricamento asset: " + fullPath);
                e.printStackTrace();
                return null;
            }
        }
        return cache.get(basePath);
    }

    /**
     * Load images from resource directory.
     * 
     * 
     * @param basePath fullPath with starting and ending '/'
     * @param resourceHitBoxCsv name of file with information about images and
     * collider
     * @return list of spriteAnimations
     * @throws IOException if path or filename are not right
     */
    private static List<SpriteAnimation> loadResources(String basePath, String resourceHitBoxCsv) throws IOException
    {
        String fullPath = basePath + resourceHitBoxCsv;

        InputStream is = SpriteFactory.class.getResourceAsStream(fullPath);
        List<SpriteAnimation> list = new ArrayList<SpriteAnimation>();
        HitBox hb;
        BufferedImage image;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is)))
        {
            String line;
            List<String> fileEntries = new ArrayList<>();
            while ((line = br.readLine()) != null)
            {
                fileEntries.add(line);
            }
            fileEntries.remove(0);

            for (var entry : fileEntries)
            {
                String[] fields = entry.split(",");
                image = ImageIO.read(SpriteFactory.class.getResourceAsStream(basePath + fields[0]));
                hb = new HitBox(Integer.valueOf(fields[1]), Integer.valueOf(fields[2]), Integer.valueOf(fields[3]),
                        Integer.valueOf(fields[4]));
                SpriteAnimation sa = new SpriteAnimation(image, hb);
                list.add(sa);
            }
            return list;
        } catch (IOException e)
        {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Fill the spriteManager with animations
     * 
     * @param manager that leads the animations of dynamicObject
     * @param basePath where the entity are stored
     * @param states of the dynamicObject
     */
    public static void fillSpriteManager(SpriteManager manager, String basePath, String resourceHitBoxCsv,
            Enum<?>[] states)
    {
        for (Enum<?> state : states)
        {
            // Es: /Sprites/Player/ + Running + /hitboxes.csv
            String path = basePath + state.name() + "/";
            getSprites(path, resourceHitBoxCsv).forEach(anim ->
            {
                manager.addSpriteAnimation(state.name(), anim);
            });
        }
    }

}
