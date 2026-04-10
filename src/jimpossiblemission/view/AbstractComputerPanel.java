package jimpossiblemission.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * Abstract computer panel.
 * 
 * @author Filippo Taiuti
 *
 */
public abstract class AbstractComputerPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    // Configuration
    private static final Color BG_COLOR = new Color(40, 35, 30);
    protected static final Color TEXT_COLOR = new Color(200, 160, 120);
    protected static final Color SELECTED_COLOR = new Color(255, 200, 100);
    private static final Color BORDER_COLOR = new Color(150, 120, 80);

    // Dimensions
    private static final int PANEL_WIDTH = 400;
    private static final int PANEL_HEIGHT = 300;
    protected static final int OPTION_HEIGHT = 50;
    private static final int ARROW_SIZE = 30;

    // Stato
    protected int selectedIndex;

    /**
     * Construction set state and dimensions of the JPanel
     * 
     */
    public AbstractComputerPanel()
    {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(BG_COLOR);
        setOpaque(true);
        setFocusable(true);

        // Input handling
        setupKeyBindings();

        // Inizialmente invisibile
        setVisible(false);
    }

    /**
     * Setup key bindings per navigazione
     */
    private void setupKeyBindings()
    {
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        // Draw UP
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUp");
        actionMap.put("moveUp", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                moveSelection(-1);
            }
        });

        // Draw DOWN
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown");
        actionMap.put("moveDown", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                moveSelection(1);
            }
        });

        // ENTER - Confirm selection
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "select");
        actionMap.put("select", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                confirmSelection();
            }
        });

        // ESC - close menu
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "close");
        actionMap.put("close", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                closeMenu();
            }
        });
    }

    /**
     * Move Arrow
     */
    protected abstract void moveSelection(int direction);

    /**
     * Confirm selection
     */
    protected abstract void confirmSelection();

    /**
     * Close menu
     */
    protected abstract void closeMenu();

    /**
     * Show the menu
     * 
     */
    public void showMenu()
    {
        selectedIndex = 0; // Reset selection
        setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Borders
        g2d.setColor(BORDER_COLOR);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawRect(5, 5, getWidth() - 10, getHeight() - 10);

        // Draw instructions
        g2d.setFont(new Font("Monospaced", Font.BOLD, 20));
        g2d.setColor(SELECTED_COLOR);
        String title = "Use your key to:";
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (getWidth() - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 50);

        // Draw options
        g2d.setFont(new Font("Monospaced", Font.BOLD, 20));

        // implements menu options in children
    }

    /**
     * Draw Arrow
     */
    protected void drawArrow(Graphics2D g, int x, int y)
    {
        g.setColor(SELECTED_COLOR);

        int[] xPoints = { x, x + ARROW_SIZE, x };
        int[] yPoints = { y, y + ARROW_SIZE / 2, y + ARROW_SIZE };
        g.fillPolygon(xPoints, yPoints, 3);

        // Border
        g.setColor(new Color(255, 255, 200));
        g.setStroke(new BasicStroke(2));
        g.drawPolygon(xPoints, yPoints, 3);
    }

}
