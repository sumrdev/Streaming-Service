package presentation;

import domain.ItemRegistry;
import domain.ItemRegistryImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindow extends Application {
    public ItemRegistry ir;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Streaming Service");

        BorderPane root = (BorderPane) FXMLLoader.load(getClass().getClassLoader().getResource("mainWindow.fxml"));
        FXMLLoader menu = new FXMLLoader(getClass().getClassLoader().getResource("mainMenu.fxml"));
        root.setCenter(menu.load());

        this.ir = new ItemRegistryImpl();
        this.ir.initialize();
        MainMenuController menuController = menu.getController();
        System.out.println(menuController);
        try {
            menuController.loadItems(ir);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}