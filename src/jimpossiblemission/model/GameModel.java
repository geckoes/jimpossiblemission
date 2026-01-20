package jimpossiblemission.model;

import java.util.Observable;

public class GameModel extends Observable {
	private int objectX = 100;
    private int objectY = 100;
    private String currentPanel = "Panel1";
    
    public void moveObject(int dx, int dy) {
        objectX += dx;
        objectY += dy;
        setChanged();
        notifyObservers();
    }
    
    public void setCurrentPanel(String panel) {
        currentPanel = panel;
        setChanged();
        notifyObservers();
    }
    
    public void resetPosition() {
        objectX = 100;
        objectY = 100;
        setChanged();
        notifyObservers();
    }
    
    public void setRandomPosition(int maxX, int maxY) {
        objectX = (int)(Math.random() * maxX);
        objectY = (int)(Math.random() * maxY);
        setChanged();
        notifyObservers();
    }
    
    public int getObjectX() { return objectX; }
    public int getObjectY() { return objectY; }
    public String getCurrentPanel() { return currentPanel; }
}
