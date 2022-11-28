package presentation;

import domain.ItemRegistry;
import domain.ItemRegistryImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindow extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Streaming Service");

        FXMLLoader rootLoader = new FXMLLoader(getClass().getClassLoader().getResource("mainWindow.fxml"));
        BorderPane root = (BorderPane) rootLoader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
        MainWindowController rootController = rootLoader.getController();
        rootController.setup();
    }

    public static void main(String[] args) {
        launch(args);
    }

}