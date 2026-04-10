package jimpossiblemission;

import javax.swing.JFrame;

import jimpossiblemission.controller.ImpossibleMissionController;
import jimpossiblemission.model.ImpossibleMission;

/**
 * Main class of the JImpossible Mission project
 * 
 * @author Filippo Taiuti
 *
 */
public class JImpossibleMission
{
    /**
     * Main class of the project
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        JFrame gameMenu = new JFrame("Impossible Mission");

        gameMenu.setTitle("Impossible Mission");
        gameMenu.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        gameMenu.setLocationRelativeTo(null);

        gameMenu.setSize(1024, 800);
        gameMenu.setVisible(true);

        new ImpossibleMissionController(new ImpossibleMission(), gameMenu);
    }

}
