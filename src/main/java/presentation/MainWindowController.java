package presentation;

import java.io.IOException;

import domain.ItemRegistry;
import domain.ItemRegistryImpl;
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

    Parent loginMenu = null;
    Parent loginMenuController = null;
    Parent homeMenu = null;
    MainMenuController homeMenuController = null;
    BorderPane scene;
    public ItemRegistry ir;

    public void navigateHome() throws IOException {
        scene.setCenter(homeMenu);
    }

    public void navigateLogin() throws IOException {
        scene.setCenter(loginMenu);
    }

    public void setup() {
        try {
            this.scene = (BorderPane) navButtonHome.getScene().getRoot();
            FXMLLoader homeMenu = new FXMLLoader(getClass().getClassLoader().getResource("mainMenu.fxml"));
            FXMLLoader loginMenu = new FXMLLoader(getClass().getClassLoader().getResource("loginMenu.fxml"));
            this.homeMenu = homeMenu.load();
            this.homeMenuController = homeMenu.getController();
            this.loginMenu = loginMenu.load();
            this.loginMenuController = loginMenu.getController();
            this.ir = new ItemRegistryImpl();
            this.ir.initialize();
            this.homeMenuController.loadItems(this.ir);
            this.navigateHome();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
