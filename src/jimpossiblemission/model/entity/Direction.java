
package jimpossiblemission.model.entity;

/**
 * Represents the possible directions used in game
 * 
 * @author Filippo Taiuti
 *
 */
public enum Direction
{
    LEFT, RIGHT, UP, DOWN, NONE;
    
    public static Direction getDirection(String direction) {
    	
		return Direction.valueOf(direction.toUpperCase());
    }
}