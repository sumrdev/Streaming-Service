package presentation;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class PlayMenuController {


    MainWindow mainWindow = null;

    @FXML
    VBox Player;
    

    public void initialize(MainWindow mw) {
        this.mainWindow = mw;
/*         ImageView img = (ImageView) Player.getChildren().get(0);
        img.preserveRatioProperty().set(true);
        img.fitHeightProperty().bind(Player.heightProperty());
        img.fitWidthProperty().bind(Player.widthProperty());
        img.pre */

    } 

    public void back() {
        if (mainWindow != null)
            mainWindow.navigateHome();
    }
}
