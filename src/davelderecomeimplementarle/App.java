package impossiblemission;

import impossiblemission.controller.Controller;
import impossiblemission.model.ImpossibleMission;
import impossiblemission.view.View;

public class App
{
    public static void main(String[] args)
    {
        new Controller(new ImpossibleMission(), new View());
    }

}
