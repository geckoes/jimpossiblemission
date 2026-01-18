package jimpossiblemission.view;

import java.awt.Image;
import java.awt.image.BufferedImage;

public interface GraphicEntity
{
    /**
     * Set sprite from a SpriteImage:
     * @param bufferedImage
     * @param offsetX offset in x axis
     * @param offsetY offset in y axis
     * @param spriteSizeX size of the sprite on x axis
     * @param spriteSizeY size of the sprite on y axis
     * @param numberOfSprite number of Sprite that can be rotate in animation
     */
    void setSprites(BufferedImage buffImg, int offsetX, int offsetY, int spriteSizeX, int spriteSizeY, int numberOfSprite);
    /**
     * Set sprite from a SpriteImage:
     * @param bufferedImage
     * @param offsetX offset in x axis
     * @param offsetY offset in y axis
     * @param spriteSizeX size of the sprite on x axis
     * @param spriteSizeY size of the sprite on y axis
     * @param numberOfSprite number of Sprite that can be rotate in animation
     */
    void setSprites(BufferedImage buffImg, int offsetX, int offsetY, int spriteSizeX, int spriteSizeY, int numberOfSprite, int delayFrames);

    Image getNext();
    Image getCurrent();
    Image getHold();
}
