package presentation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {
    @FXML
    Button button;

    public void click() {
        button.setText("Clicked!");
    }
}
