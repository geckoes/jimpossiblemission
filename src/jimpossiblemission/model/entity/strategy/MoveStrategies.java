package jimpossiblemission.model.entity.strategy;

/**
 * class for movement strategies.
 * 
 * @author Filippo Taiuti
 *
 */
public class MoveStrategies
{
    /**
     * Transforms string in movement strategy
     * 
     * @param moveStrategy as string
     * @return moveStrategy as MoveStrategy
     */
    public static MoveStrategy getStrategy(String moveStrategy)
    {
        return getStrategy(moveStrategy, 0, 0);
    }

    /**
     * Transforms string in movement strategy
     * 
     * @param moveStrategy as string
     * @param startX left point
     * @param endX right point
     * @return moveStrategy as MoveStrategy
     */
    public static MoveStrategy getStrategy(String moveStrategy, int startX, int endX)
    {
        switch (moveStrategy) {
            case "patrol":
                return new PatrolStrategy(startX, endX);
            case "follow":
                return new FollowStrategy();
            default:
                return new WatchStrategy();
        }
    }
}
