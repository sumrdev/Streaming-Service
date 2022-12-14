package presentation;


import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainWindowController {

    @FXML
    Button navButtonHome;
    @FXML
    Button navButtonUser;

    MainWindow mainWindow = null;

    public void initialize(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }


    public void navigateHome() {
        if (mainWindow != null)
            mainWindow.navigateHome();
    }

    public void navigateUser() {
        if (mainWindow != null)
            mainWindow.navigateUser();
    }

    

}
