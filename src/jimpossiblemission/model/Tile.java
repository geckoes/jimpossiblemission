/**
 * 
 */
package jimpossiblemission.model;

import java.util.Observable;
import java.util.Optional;

/**
 * @author Filippo Taiuti
 *
 */
@SuppressWarnings("deprecation")
public class Tile extends Observable
{
    /**
     * The Visibility enum represents the visibility of a tile.
     *
     * @author Cicio Ionut
     * @version 1.0
     */
    public enum Visibility
    {
        Hidden, Flagged, Revealed
    }

    public final int x, y;
    public final boolean isMine;
    private Visibility visibility = Visibility.Hidden;
    public Optional<Integer> adjacentMines = Optional.empty();

    /**
     * Class constructor specifying the coordiantes and wether the tile is a mine.
     *
     * @param x      the x coordinate in the grid
     * @param y      the y coordinate in the grid
     * @param isMine whether the tile is a mine
     */
    public Tile(int x, int y, boolean isMine)
    {
        this.x = x;
        this.y = y;
        this.isMine = isMine;
    }

    /**
     * Returns the visibility of the tile.
     *
     * @return the visibility of the tile
     */
    public Visibility visibility()
    {
        return visibility;
    }

    /**
     * Returns the number of adjacent mines.
     *
     * @return the number of adjacent mines
     */
    public Optional<Integer> adjacentMines()
    {
        return adjacentMines;
    }

    /**
     * Reveals the tile if it's hidden.
     */
    public void reveal()
    {
        if (visibility != Visibility.Hidden)
            return;

        setChanged();
        notifyObservers(visibility = Visibility.Revealed);
    }

    /**
     * Flags the tile if it's hidden or unflags it if it's flagged.
     */
    public void flag()
    {
        setChanged();
        notifyObservers(visibility = switch (visibility) {
            case Hidden -> Visibility.Flagged;
            case Flagged -> Visibility.Hidden;
            case Revealed -> {
                clearChanged();
                yield Visibility.Revealed;
            }
        });
    }
}
