package presentation;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class MainWindowController {

    @FXML
    Button navButtonHome;
    @FXML
    Button navButtonLogin;

    public void navigateHome(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainMenu.fxml"));
        BorderPane scene = (BorderPane) navButtonHome.getScene().getRoot();
        scene.setCenter(root);
    }

    public void navigateLogin(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("loginMenu.fxml"));
        BorderPane scene = (BorderPane) navButtonHome.getScene().getRoot();
        scene.setCenter(root);
    }

}
