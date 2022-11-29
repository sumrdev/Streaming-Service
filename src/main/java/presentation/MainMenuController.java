package presentation;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import domain.ItemRegistry;
import javafx.collections.ObservableList;
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
    ChoiceBox<String> choiceGenre;

    @FXML
    ChoiceBox<String> choiceCategory;

    ItemRegistry ir = null;

    public void loadItems(ItemRegistry ir, List<String> keyList) {
        itemGrid.getChildren().clear();
        keyList.forEach(item -> {
            try {
                StackPane itemPane = (StackPane) FXMLLoader.load(getClass().getClassLoader().getResource("item.fxml"));
                ImageView image = (ImageView) itemPane.getChildren().get(0);
                Image img;
                // System.out.println(item);
                if (ir.getSeriesSeasons(item) == null) {
                    img = new Image(getClass().getResourceAsStream("/movie_img/" + item + ".jpg"));
                } else {
                    img = new Image(getClass().getResourceAsStream("/show_img/" + item + ".jpg"));
                }
                image.setImage(img);

                Text text = (Text) itemPane.getChildren().get(1);
                text.setText(item);
                itemGrid.getChildren().add(itemPane);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    public void loadFilters(ItemRegistry ir) {
        this.ir = ir;
        String[] genres = ir.getGenreSet().stream().toArray(String[]::new);
        choiceGenre.getItems().addAll("All");
        choiceGenre.getItems().addAll(genres);
        choiceGenre.getSelectionModel().selectFirst();

        choiceCategory.getItems().addAll("Movies", "Series", "All");
        choiceCategory.getSelectionModel().selectFirst();
    }

    public void updateItems() {
        if (ir == null)
            return;
        String genre = choiceGenre.getValue();
        String category = choiceCategory.getValue();

        Stream<String> items = Stream.concat(ir.getMovieKeyList().stream(), ir.getSeriesKeyList().stream());
        if (genre != null) {
            if (!genre.equals("All")) {
                items = items.filter(item -> Arrays.asList(ir.getItemGenre(item)).contains(genre));
            }
        }

        if (category != null) {
            if (category.equals("Movies")) {
                items = items.filter(item -> ir.getSeriesSeasons(item) == null);
            } else if (category.equals("Series")) {
                items = items.filter(item -> ir.getSeriesSeasons(item) != null);
            }
        }
        loadItems(ir, items.toList());
    }
}
