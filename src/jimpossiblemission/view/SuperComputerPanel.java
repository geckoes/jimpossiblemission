package jimpossiblemission.view;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * SuperComputerPanel shows the menu of super computer
 * 
 * @author Filippo Taiuti
 *
 */
public class SuperComputerPanel extends AbstractComputerPanel
{
    private static final long serialVersionUID = 1L;

    // Opzioni del menu
	private static final String[] MENU_OPTIONS = { "Hacker System", "Exit" };

    // Listener to computer panel
    private SuperComputerMenuListener superComputerMenuListener;

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
				superComputerMenuListener.onHacker();
                closeMenu();
                break;
            case 1: // Exit
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
        superComputerMenuListener.onMenuClosed();
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
     * Add listener to panel
     * 
     * @param listener superComputerMenuListener
     */
    void addSuperComputerMenuListener(SuperComputerMenuListener listener)
    {
        superComputerMenuListener = listener;
    }
}
