package jimpossiblemission.controller;

import java.io.IOException;
import java.util.Observer;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import jimpossiblemission.model.GameModel;
import jimpossiblemission.model.game.Player;
import jimpossiblemission.model.ImpossibleMission;
import jimpossiblemission.view.MainView;
import jimpossiblemission.view.View;

/**
 * The Impossible Mission view controller.
 *
 * @author Filippo Taiuti
 * 
 */
@SuppressWarnings("deprecation")
public class Controller {
	private GameModel model;
    private MainView view;
    
    public Controller(GameModel model) {
        this.model = model;
    }
    
    public void setView(MainView view) {
        this.view = view;
    }
    
    public void moveObject(int dx, int dy) {
        model.moveObject(dx, dy);
    }
    
    public void switchPanel(String panelName) {
        model.setCurrentPanel(panelName);
        view.showPanel(panelName);
    }
    
    public void resetPosition() {
        model.resetPosition();
    }
    
    public void setRandomPosition() {
        model.setRandomPosition(400, 400);
    }
}
