package jimpossiblemission.view;

/**
 * Interface used to get actions in game view
 * 
 * @author Filippo Taiuti
 *
 */
public interface ComputerMenuListener
{
	/**
	 * Command to reset lift positions
	 */
    void onResetLiftPosition();

    /**
     * Command to block enemies
     */
    void onBlockEnemies();
    
    /**
     * Command to close the current menu
     */
    void onMenuClosed();
}
