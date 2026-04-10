package jimpossiblemission.model.entity;

/**
 * HitBox class: this is the box used to check collisions. The arguments are
 * relative to the object's coordinates (x,y)
 * 
 * @author Filippo Taiuti
 *
 */
public class HitBox
{
    private int x, y, width, height;

    /**
     * Constructor without arguments
     */
    public HitBox()
    {
    }

    /**
     * Constructor with arguments
     * 
     * @param x is the left bound of the box
     * @param y is the top bound of the box
     * @param width is the right bound of the box
     * @param height is the bottom bound of the box
     */
    public HitBox(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Modifies the bounds of the box, the new coordinates will update the older
     * ones
     * 
     * @param newX the new value of left bound
     * @param newY the new value of top bound
     * @param newWidth the new value of right bound
     * @param newHeight the new value of bottom bound
     */
    public void updateBox(int newX, int newY, int newWidth, int newHeight)
    {
        x = newX;
        y = newY;
        width = newWidth;
        height = newHeight;
    }

    /**
     * Modifies the bounds of the box, the new coordinates will update the older
     * ones
     * 
     * @param otherHitBox the new hitBox
     */
    public void updateBox(HitBox otherHitBox)
    {
        updateBox(otherHitBox.x, otherHitBox.y, otherHitBox.width, otherHitBox.height);
    }

    /**
     * Returns the left bound of the box
     * 
     * @return x coordinate
     */
    public int getX()
    {
        return x;
    }

    /**
     * Returns the top bound of the box
     * 
     * @return y coordinate
     */
    public int getY()
    {
        return y;
    }

    /**
     * Returns the right bound of the box
     * 
     * @return width coordinate
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Returns the bottom bound of the box
     * 
     * @return height coordinate
     */
    public int getHeight()
    {
        return height;
    }
}
