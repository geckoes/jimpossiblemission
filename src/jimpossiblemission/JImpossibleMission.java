/**
 * 
 */
package jimpossiblemission;

import jimpossiblemission.controller.Controller;
import jimpossiblemission.model.ImpossibleMission;
import jimpossiblemission.view.View;

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
//        new MainController(new ImpossibleMission(), new MainView());
        new Controller(new ImpossibleMission(), new View());
    }

}
