package presentation;

public class PlayMenuController {

    MainWindow mainWindow = null;

    public void initialize(MainWindow mw) {
        this.mainWindow = mw;
    } 

    public void back() {
        if (mainWindow != null)
            mainWindow.navigateHome();
    }
}
