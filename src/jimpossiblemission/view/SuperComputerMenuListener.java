package jimpossiblemission.view;

/**
 * Interface listener to send commands from superComputer to game.
 *  
 * @author Filippo Taiuti
 *
 */
public interface SuperComputerMenuListener
{
	/**
	 * Hacker the system
	 */
	void onHacker();

    /**
     * Close menu
     */
    void onMenuClosed();
}
