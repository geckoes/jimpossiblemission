package jimpossiblemission;

import jimpossiblemission.controller.MainController;
import jimpossiblemission.model.ImpossibleMission;
import jimpossiblemission.view.MainView;

/**
 * @author Filippo Taiuti
 *
 */
public class JImpossibleMission
{
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        new MainController(new ImpossibleMission(), new MainView());
    }

}
