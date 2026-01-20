package jimpossiblemission;

import javax.swing.SwingUtilities;

import jimpossiblemission.controller.Controller;
import jimpossiblemission.model.GameModel;
import jimpossiblemission.view.MainView;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crea il Model
            GameModel model = new GameModel();
            
            // Crea il Controller
            Controller controller = new Controller(model);
            
            // Crea la View
            MainView view = new MainView(model, controller);
            view.setVisible(true);
        });
    }
}
