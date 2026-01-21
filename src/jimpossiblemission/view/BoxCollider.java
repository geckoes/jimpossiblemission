package jimpossiblemission.view;

/**
 * @author Filippo Taiuti
 *
 */
public class BoxCollider extends ShapeCollider
{
    protected int x, y, w, h;

	@Override
	public boolean isColliding(DecoratorObject otherObj) {
		return false;
	}
}
