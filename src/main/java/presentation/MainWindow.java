package presentation;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindow extends Application {
    /**
     * root of main window, set center to one of the roots below
     */
    private BorderPane root;
    /**
     * roots to show in center of main window
     */
    private Parent userMenu;
    private Parent homeMenu;
    private Parent playMenu;
    /**
     * controllers for the roots
     */
    private HomeMenuController homeMenuController;
    private UserMenuController userMenuController;
    private PlayMenuController playMenuController;
    
    private DomainAccess da;
    
    /**
     * Start method for javafx application, is called by javafx after launch.
     * Sets and loads the mainWindow. Calls setup() to load the other windows.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("Neostream");

        FXMLLoader rootLoader = new FXMLLoader(getClass().getClassLoader().getResource("mainWindow.fxml"));
        root = (BorderPane) rootLoader.load();
        MainWindowController rootController = rootLoader.getController();
        rootController.initialize(this);
        Scene mainScene = new Scene(root);
        mainScene.getStylesheets().add(getClass().getClassLoader().getResource("style.css").toExternalForm());
        mainScene.setUserData(rootController);

        primaryStage.setScene(mainScene);
        primaryStage.show();
        primaryStage.getIcons().add(new Image("icon.png"));
        setup();
    }

    /**
     * Setup method for the application, loads the roots to show and their controllers.
     * Navigates to the userMenu.
     */
    public void setup() {
        try {
            DomainAccess da = new DomainAccess();
            this.da = da;

            FXMLLoader homeMenu = new FXMLLoader(getClass().getClassLoader().getResource("homeMenu.fxml"));
            FXMLLoader userMenu = new FXMLLoader(getClass().getClassLoader().getResource("userMenu.fxml"));
            FXMLLoader playMenu = new FXMLLoader(getClass().getClassLoader().getResource("playMenu.fxml"));
            this.homeMenu = homeMenu.load();
            this.userMenu = userMenu.load();
            this.playMenu = playMenu.load();

            homeMenuController = homeMenu.getController();
            userMenuController = userMenu.getController();
            playMenuController = playMenu.getController();

            homeMenuController.initialize(da);
            userMenuController.initialize(da);
            navigateUser();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Reading FXML file failed");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error loading application");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Navigates to the homeMenu. Unloads the userMenu.
     */
    public void navigateHome() {
        if (this.da != null && this.da.getSelectedUser() == null)
            return;
        userMenuController.unload();
        homeMenuController.load();
        root.setCenter(homeMenu);
    }

    /**
     * Navigates to the userMenu. Unloads the homeMenu.
     */
    public void navigateUser() {
        homeMenuController.unload();
        userMenuController.load();
        root.setCenter(userMenu);

    }

    /**
     * Navigates to the playMenu. Loads the playMenu with the given itemTitle and itemEpisode.
     * @param itemTitle
     * @param itemEpisode
     */
    public void playMovie(String itemTitle, String itemEpisode) {
        playMenuController.load(itemTitle, itemEpisode);
        root.setCenter(playMenu);
    }

    /** 
     * main method for javafx calls launch.
    */
    public static void main(String[] args) {
        launch(args);
    }
}