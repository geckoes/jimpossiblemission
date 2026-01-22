package jimpossiblemission.view;

/**
 * @author Filippo Taiuti
 *
 */
public class BoxCollider extends ShapeCollider
{
    protected int localX, localY, localW, localH;
    protected int x, y, w, h;

    public BoxCollider(int x, int y, int w, int h) {
    	this.x=x;
    	localX=x;
    	this.y=y;
    	localY=y;
    	this.w=w;
    	localW=w;
    	this.h=h;
    	localH=h;
    }
	@Override
	public boolean isColliding(ShapeCollider otherObj) {
		if (otherObj instanceof BoxCollider) {
			BoxCollider bc = (BoxCollider) otherObj;
			// controllo che la base dell'otherObj sia all'interno della parte superiore
			// e che la base sia all'interno dell'altezza della piattaforma
			
		}
		return false;
	}
}
