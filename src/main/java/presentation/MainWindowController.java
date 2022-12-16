package presentation;

public class MainWindowController {
    MainWindow mainWindow;

    /**
     * Initialize the controller with the main window.
     * @param mainWindow the main javafx window, used to navigate between the different views.
     */
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
    /**
     * Calls the playMovie method in the main window with the given itemTitle and itemEpisode.
     * @param itemTitle String title of the item to play.
     * @param itemEpisode String of episode identifier of the item to play.
     */
    public void playMovie(String itemTitle, String itemEpisode) {
        if (mainWindow != null)
        mainWindow.playMovie(itemTitle, itemEpisode);
    }
    /**
     * overload of playMovie, calls the playMovie method with no itemEpisode.
     * @param itemTitle String title of the item to play.
     */
    public void playMovie(String itemTitle) {
        String itemEpisode = "";
        if (mainWindow != null)
            mainWindow.playMovie(itemTitle, itemEpisode);
    }
}
