package jimpossiblemission.model.entity;

/**
 * Represent the objects that cannot move. They can contain badges to use with
 * computers.
 * 
 * @author Filippo Taiuti
 *
 */
public class SearchableObject extends GameObject
{
    private Badge badge;
    private int searchPercentage;
    private String type;

    private final static int MULTIPLY = 4;

    /**
     * Constructor of a searchableObject
     *  
     * @param x initial x
     * @param y initial y
     * @param type of searchableObject
     * @param badgeType
     */
    public SearchableObject(int x, int y, String type, String badgeType)
    {
        super(x, y);
        this.type = type;
        collider = new BoxCollider(this);
        addBadge(badgeType);
    }

    // Add badge
    private void addBadge(String badgeString)
    {
        if (badgeString.equalsIgnoreCase(Badge.BadgeType.LIFT_RESET.name()))
            badge = new Badge(Badge.BadgeType.LIFT_RESET);
        else if (badgeString.equalsIgnoreCase(Badge.BadgeType.BLOCK_ENEMY.name()))
            badge = new Badge(Badge.BadgeType.BLOCK_ENEMY);
        else if (badgeString.equalsIgnoreCase(Badge.BadgeType.HACKER_KEY.name()))
            badge = new Badge(Badge.BadgeType.HACKER_KEY);
        else
            badge = new Badge(Badge.BadgeType.EMPTY);
    }

    /**
     * Search the badge in the searchableObject
     */
    public void searchBadge()
    {
        searchPercentage++;
        if (searchPercentage >= 100 * MULTIPLY)
        {
            active = false;
            setChanged();
            notifyObservers(badge);
        }
    }

    /**
     * Get type
     * @return string type
     */
    public String getType()
    {
        return type;
    }

    /**
     * Get badge found in searchableObject
     * 
     * @return badge
     */
    public Badge getBadge()
    {
        return badge;
    }

    /**
     * Get current percentage of research
     * @return
     */
    public int getSearchPercentage()
    {
        return searchPercentage / MULTIPLY;
    }

    /**
     * Inner Class Badge
     * 
     * @author Filippo Taiuti
     *
     */
    public class Badge
    {
        /**
         * Type of searchable object
         * 
         */
        public enum BadgeType
        {
            LIFT_RESET, BLOCK_ENEMY, HACKER_KEY, EMPTY;
        }

        private final BadgeType badgeType;

        /**
         * Constructor of Searchable object
         * 
         * @param searchableType
         */
        public Badge(BadgeType searchableType)
        {
            this.badgeType = searchableType;
        }

        /**
         * Return the type of searchable object
         * 
         * @return type of searchable object
         */
        public BadgeType getBadgeType()
        {
            return badgeType;
        }
    }
}
