/**
 * 
 */
package jimpossiblemission.model.game;

/**
 * @author Filippo Taiuti
 *
 */
public abstract class GameObject
{
    protected double x, y, width, height;
    protected boolean active;
    protected boolean isMoving = false;
    
    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.active = true;
    }

    public abstract void update();
    
    public boolean collision(GameObject otherObj) {
        return x < otherObj.x + otherObj.width 
                && x + width > otherObj.x 
                && y < otherObj.y + otherObj.height 
                && y + height > otherObj.y;
    }

    // Getters and setters
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public boolean isActive() { return active; }
    public boolean isMoving() { return isMoving; }
}
