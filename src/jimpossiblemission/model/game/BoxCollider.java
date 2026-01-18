/**
 * 
 */
package jimpossiblemission.model.game;

/**
 * @author Filippo Taiuti
 *
 */
public class BoxCollider extends ShapeCollider
{
    protected int x, y, w, h;

    @Override
    public boolean isColliding(GameObject other)
    {
        
        return false;
    }
}
