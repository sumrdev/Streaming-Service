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
    Button navButtonUser;

    Parent userMenu = null;
    UserMenuController userMenuController = null;
    Parent homeMenu = null;
    MainMenuController homeMenuController = null;
    BorderPane scene;
    FrontEndHelper feh = null;

    public void navigateHome() {
        if (this.feh != null && this.feh.ur.getSelectedUser() == null)
            return;
        scene.setCenter(homeMenu);
        //homeMenuController.update();
    }

    public void navigateUser() {
        scene.setCenter(userMenu);
    }

    public void setup() {
        try {

            this.scene = (BorderPane) navButtonHome.getScene().getRoot();
            FXMLLoader homeMenu = new FXMLLoader(getClass().getClassLoader().getResource("mainMenu.fxml"));
            FXMLLoader userMenu = new FXMLLoader(getClass().getClassLoader().getResource("userMenu.fxml"));
            this.homeMenu = homeMenu.load();
            this.homeMenuController = homeMenu.getController();
            this.userMenu = userMenu.load();
            this.userMenuController = userMenu.getController();

            FrontEndHelper feh = new FrontEndHelper();
            this.feh = feh;
            this.homeMenuController.initialize(feh);
            this.userMenuController.initialize(feh);
            this.scene.setCenter(this.userMenu);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
