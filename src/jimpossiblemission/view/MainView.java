package jimpossiblemission.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;

import jimpossiblemission.controller.Controller;
import jimpossiblemission.model.GameModel;

/**
 * Vista principale per il progetto Impossible Mission
 * 
 * @author Filippo Taiuti
 */

@SuppressWarnings("deprecation")
public class MainView extends JFrame
{
	private GameModel model;
    private Controller controller;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Panel1 panel1;
    
    public MainView(GameModel model, Controller controller) {
        this.model = model;
        this.controller = controller;
        controller.setView(this);
        
        setTitle("Applicazione MVC con Observer");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        createMenuBar();
        createMainPanel();
        
        add(mainPanel);
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Pannelli");
        
        JMenuItem item1 = new JMenuItem("Pannello Gioco");
        item1.addActionListener(e -> controller.switchPanel("Panel1"));
        
        JMenuItem item2 = new JMenuItem("Pannello Info");
        item2.addActionListener(e -> controller.switchPanel("Panel2"));
        
        JMenuItem item3 = new JMenuItem("Pannello Controllo");
        item3.addActionListener(e -> controller.switchPanel("Panel3"));
        
        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }
    
    private void createMainPanel() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        panel1 = new Panel1(model, controller);
        Panel2 panel2 = new Panel2(model);
        Panel3 panel3 = new Panel3(model, controller);
        
        mainPanel.add(panel2, "Panel2");
        mainPanel.add(panel1, "Panel1");
        mainPanel.add(panel3, "Panel3");
    }
    
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
        if (panelName.equals("Panel1")) {
            panel1.requestFocusInWindow();
        }
    }

}
