package presentation;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class PlayMenuController {
    @FXML
    BorderPane root;

    public void back() {
        MainWindowController mwc = (MainWindowController) root.getScene().getUserData();
        mwc.navigateHome();
    }
}
