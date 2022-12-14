package presentation;

public class MainWindowController {
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
