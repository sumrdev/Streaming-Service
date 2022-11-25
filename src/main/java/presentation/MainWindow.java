package presentation;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindow extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Streaming Service");

        BorderPane root = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("mainWindow.fxml"));
        root.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource(
                "mainMenu.fxml")));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}