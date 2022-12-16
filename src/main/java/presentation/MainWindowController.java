package presentation;

public class MainWindowController {
    MainWindow mainWindow;

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

    public void playMovie(String itemTitle) {
        String itemEpisode = "";
        if (mainWindow != null)
            mainWindow.playMovie(itemTitle, itemEpisode);
    }
    public void playMovie(String itemTitle, String itemEpisode) {
        if (mainWindow != null)
            mainWindow.playMovie(itemTitle, itemEpisode);
    }

}
