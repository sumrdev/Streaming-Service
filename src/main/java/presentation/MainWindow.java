package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;



public class MainWindow extends Application {
    
    // root of main window, set center to one of the roots below
    BorderPane root;
    // roots to show in center of main window
    Parent userMenu = null;
    Parent homeMenu = null;
    Parent playMenu = null;
    
    DomainAccess da = null;
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Streaming Service");

        FXMLLoader rootLoader = new FXMLLoader(getClass().getClassLoader().getResource("mainWindow.fxml"));
        root = (BorderPane) rootLoader.load();
        MainWindowController rootController = rootLoader.getController();
        rootController.initialize(this);
        Scene mainScene = new Scene(root);
        mainScene.getStylesheets().add(getClass().getClassLoader().getResource("style.css").toExternalForm());

        primaryStage.setScene(mainScene);
        primaryStage.show();
        primaryStage.getIcons().add(new Image("icon.png"));
        setup();
    }

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
            HomeMenuController homeMenuController = homeMenu.getController();
            UserMenuController userMenuController = userMenu.getController();
            PlayMenuController playMenuController = playMenu.getController();
            homeMenuController.initialize(da, this);
            userMenuController.initialize(da, this);
            playMenuController.initialize(this);
            this.root.setCenter(this.userMenu);
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    public void navigateHome() {
        if (this.da != null && this.da.getSelectedUser() == null)
            return;
        root.setCenter(homeMenu);
        //homeMenuController.update();
    }

    public void navigateUser() {
        root.setCenter(userMenu);
    }

    public void playMovie() {
        root.setCenter(playMenu);
    }

    public static void main(String[] args) {
        launch(args);
    }

}