package presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import domain.ItemRegistry;
import domain.ItemRegistryImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class MainWindowController implements Initializable {

    @FXML
    Button navButtonHome;
    @FXML
    Button navButtonLogin;

    public void navigateHome() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainMenu.fxml"));
        BorderPane scene = (BorderPane) navButtonHome.getScene().getRoot();
        scene.setCenter(root);
    }

    public void navigateLogin() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("loginMenu.fxml"));
        BorderPane scene = (BorderPane) navButtonHome.getScene().getRoot();
        scene.setCenter(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
