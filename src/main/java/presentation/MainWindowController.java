package presentation;


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
    LoginMenuController loginMenuController = null;
    Parent homeMenu = null;
    MainMenuController homeMenuController = null;
    BorderPane scene;

    public void navigateHome() {
        scene.setCenter(homeMenu);
        //homeMenuController.update();
    }

    public void navigateLogin() {
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

            FrontEndHelper feh = new FrontEndHelper(this);
            this.homeMenuController.initialize(feh);
            this.loginMenuController.initialize(feh);
            this.scene.setCenter(this.homeMenu);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
