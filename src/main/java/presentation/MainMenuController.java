package presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import domain.ItemRegistry;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class MainMenuController implements Initializable {
    @FXML
    FlowPane itemGrid;

    public void loadItems(ItemRegistry ir) {
        System.out.println("Loading items...");
        ir.getMovieKeyList().forEach(movie -> {
            try {
                StackPane itemPane = (StackPane) FXMLLoader.load(getClass().getClassLoader().getResource("item.fxml"));
                ImageView image = (ImageView) itemPane.getChildren().get(0);
                Image img = new Image(getClass().getResourceAsStream("/movie_img/" + movie + ".jpg"));
                image.setImage(img);

                Text text = (Text) itemPane.getChildren().get(1);
                text.setText(movie);

                itemGrid.getChildren().add(itemPane);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
