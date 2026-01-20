package jimpossiblemission.view;
import javax.swing.*;

import jimpossiblemission.model.GameModel;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class Panel2 extends JPanel implements Observer {
	private GameModel model;
    private JLabel statusLabel;
    
    public Panel2(GameModel model) {
        this.model = model;
        model.addObserver(this);
        setLayout(new BorderLayout());
        setBackground(new Color(255, 240, 230));
        
        JLabel titleLabel = new JLabel("Pannello Informazioni", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);
        
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(statusLabel, BorderLayout.CENTER);
        
        updateInfo();
    }
    
    private void updateInfo() {
        String info = "<html><div style='text-align: center;'>" +
                     "Coordinata X: " + model.getObjectX() + "<br>" +
                     "Coordinata Y: " + model.getObjectY() + "<br>" +
                     "Pannello attivo: " + model.getCurrentPanel() +
                     "</div></html>";
        statusLabel.setText(info);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        updateInfo();
    }
}
