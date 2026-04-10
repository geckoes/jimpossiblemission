package jimpossiblemission.view;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Computer panel is used to send command as reset lifts and block enemies.
 * 
 * @author Filippo Taiuti
 *
 */
public class ComputerPanel extends AbstractComputerPanel
{
    private static final long serialVersionUID = 1L;

    // Opzioni del menu
    private static final String[] MENU_OPTIONS = { "Reset Lift Position", "Block Robots", "Exit" };

    // Listener to computer panel
    private ComputerMenuListener computerMenuListener;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void moveSelection(int direction)
    {
        selectedIndex += direction;

        // Wrap around
        if (selectedIndex < 0)
        {
            selectedIndex = MENU_OPTIONS.length - 1;
        } else if (selectedIndex >= MENU_OPTIONS.length)
        {
            selectedIndex = 0;
        }

        repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void confirmSelection()
    {
        switch (selectedIndex) {
            case 0: // Reset Elevator
                computerMenuListener.onResetLiftPosition();
                closeMenu();
                break;
            case 1: // Block Robots
                computerMenuListener.onBlockEnemies();
                closeMenu();
                break;
            case 2: // Exit
                closeMenu();
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void closeMenu()
    {
        setVisible(false);
        computerMenuListener.onMenuClosed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int startY = 100;
        for (int i = 0; i < MENU_OPTIONS.length; i++)
        {
            int y = startY + i * OPTION_HEIGHT;

            // Colore testo
            if (i == selectedIndex)
            {
                g2d.setColor(SELECTED_COLOR);

                // Disegna mano (freccia)
                drawArrow(g2d, 40, y - 15);
            } else
            {
                g2d.setColor(TEXT_COLOR);
            }

            // Disegna testo opzione
            g2d.drawString(MENU_OPTIONS[i], 80, y);
        }
    }

    /**
     * Add listener to JPanel
     * 
     * @param listener ComputerMenuListener
     */
    void addComputerMenuListener(ComputerMenuListener listener)
    {
        computerMenuListener = listener;
    }
}
