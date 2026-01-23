package jimpossiblemission.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
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
import jimpossiblemission.model.game.Platform;
import jimpossiblemission.model.game.ShapeCollider;
import jimpossiblemission.model.game.SpriteAnimation;

public class DecoratorPlatform extends DecoratorObject
{
    List<SpriteAnimation> spriteAnimation = new ArrayList<SpriteAnimation>();
    int numberOSprite;
    int currentSpriteNumber = 0;
    int delayFrames = 4;
    int tempDelayFrame = 0;
    Image currentSprite;

    /**
     * @param gameObject
     * @throws IOException
     */
    public DecoratorPlatform(Platform gameObject, String hitBoxCsv, String path) throws IOException
    {
        super(gameObject);

        var fileEntries = new ArrayList<String>();
        InputStream inputStream = DecoratorPlatform.class.getResourceAsStream(path + hitBoxCsv);
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

                sa.setImage(image, image.getWidth(null) * scale * 5, image.getHeight(null) * scale);
                BoxCollider bc = new BoxCollider(Integer.valueOf(fields[1]),  Integer.valueOf(fields[2]),  Integer.valueOf(fields[3]),  Integer.valueOf(fields[4]));
                sa.setShapeCollider(bc);
                spriteAnimation.add(sa);
            }
        } catch (IOException e)
        {
            throw new IOException(e.getMessage());
        }
    }
    
    public void move() {
    	gameObject.move();
    	
    	
    }

    public void draw(Graphics2D g2)
    {
        int x = gameObject.getX();
        int y = gameObject.getY();
        SpriteAnimation sa = spriteAnimation.get(0);

        width = sa.getWidth();
        height = sa.getHeight();
        g2.drawImage(sa.getImage(), x, y, sa.getWidth(), sa.getHeight(), Color.GREEN, null);

        System.out.println("Platform " + gameObject.getX() + " " + gameObject.getY());

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

    @Override
    public void update(Observable o, Object arg)
    {
        if (o == null || arg == null)
            return;
        if (arg instanceof DecoratorObject)
        {
            DecoratorObject decObj = (DecoratorObject) arg;
            decObj.gameObject.setOnGround(false);
            // controllo la collisione con un oggetto
            if (intersects(decObj))
            {
                decObj.gameObject.setY(gameObject.getY() - decObj.height + 1);
                decObj.gameObject.setOnGround(true);
            }
			ShapeCollider sc = decObj.getShapeCollider();
			// controllo la collisione con un oggetto
			if (!decObj.gameObject.isOnGround()) {
				
				if (decObj)
					((DecoratorObject) arg).getGameObject().setOnGround(true);
			}
        }
    }

	private void updateGeneralPosition(BoxCollider sa) {
		sa.x = (int) (sa.localX + this.getGameObject().getX());
		sa.y = (int) (sa.localY + this.getGameObject().getY());
		sa.w = (int) (sa.localW + this.getGameObject().getX());
		sa.h = (int) (sa.localH + this.getGameObject().getY());
	}
	@Override
	public ShapeCollider getShapeCollider() {
		BoxCollider sa =  (BoxCollider) spriteAnimation.get(currentSpriteNumber).getShapeCollider();
		updateGeneralPosition(sa);
		return sa;
	}
}
