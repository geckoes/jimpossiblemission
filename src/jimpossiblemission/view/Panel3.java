package jimpossiblemission.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import jimpossiblemission.controller.Controller;
import jimpossiblemission.model.GameModel;

public class Panel3 extends JPanel implements Observer {
	private GameModel model;
    private Controller controller;
    
    public Panel3(GameModel model, Controller controller) {
        this.model = model;
        this.controller = controller;
        model.addObserver(this);
        setLayout(new GridLayout(3, 1, 10, 10));
        setBackground(new Color(240, 255, 230));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Pannello di Controllo", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel);
        
        JButton resetBtn = new JButton("Reset Posizione");
        resetBtn.addActionListener(e -> controller.resetPosition());
        add(resetBtn);
        
        JButton randomBtn = new JButton("Posizione Casuale");
        randomBtn.addActionListener(e -> controller.setRandomPosition());
        add(randomBtn);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }



}
