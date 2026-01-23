package jimpossiblemission.view;

import java.awt.image.BufferedImage;
import java.util.Optional;

import jimpossiblemission.model.game.ShapeCollider;
import jimpossiblemission.model.game.Sprite;

public class SpriteAnimation extends Sprite {
	
	private Optional<ShapeCollider> shapeCollider;
	
	public SpriteAnimation(BufferedImage image, ShapeCollider shapeCollider) {
		super(image);
		this.shapeCollider = Optional.ofNullable(shapeCollider);
	}
	
	public Optional<ShapeCollider> getShapeCollider() {
		return shapeCollider;
	}


}
