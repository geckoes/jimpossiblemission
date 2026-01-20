package jimpossiblemission.view;

import javax.swing.*;

import jimpossiblemission.controller.Controller;
import jimpossiblemission.model.GameModel;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class Panel1 extends JPanel implements Observer {
    private GameModel model;
    private Controller controller;
    
    public Panel1(GameModel model, Controller controller) {
        this.model = model;
        this.controller = controller;
        model.addObserver(this);
        setBackground(new Color(230, 240, 255));
        setFocusable(true);
        
        // Listener tastiera per spostare l'oggetto
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int step = 10;
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        controller.moveObject(0, -step);
                        break;
                    case KeyEvent.VK_DOWN:
                        controller.moveObject(0, step);
                        break;
                    case KeyEvent.VK_LEFT:
                        controller.moveObject(-step, 0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        controller.moveObject(step, 0);
                        break;
                }
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Disegna l'oggetto
        g2d.setColor(Color.BLUE);
        g2d.fillOval(model.getObjectX(), model.getObjectY(), 50, 50);
        
        // Istruzioni
        g2d.setColor(Color.BLACK);
        g2d.drawString("Usa le frecce per muovere il cerchio", 10, 20);
        g2d.drawString("Posizione: (" + model.getObjectX() + ", " + model.getObjectY() + ")", 10, 40);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
