package presentation;

import java.net.URL;
import java.util.ResourceBundle;

import domain.ItemRegistry;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class MainMenuController {
    @FXML
    FlowPane itemGrid;

    @FXML
    ChoiceBox choiceGenre;

    @FXML
    ChoiceBox choiceCategory;

    public void loadItems(ItemRegistry ir) {
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

    public void loadFilters(ItemRegistry ir) {
        String[] genres = ir.getGenreSet().stream().toArray(String[]::new);
        choiceGenre.getItems().addAll(genres);
    }
}
