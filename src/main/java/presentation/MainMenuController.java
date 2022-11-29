package presentation;

import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import domain.ItemRegistry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
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

    @FXML
    TextField searchBox;

    ItemRegistry ir = null;
    List<String> currentItems = null;

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

    public void updateItemFilters() {
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
        currentItems = items.toList();
        loadItems(ir, currentItems);
    }

    public void search(ActionEvent e) {
        String search = searchBox.getText();
        HashMap<String, Integer> searchResults = new HashMap<>();
        for (String item : currentItems) {
            searchResults.put(item, calculate(item, search));
        }
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(searchResults.entrySet());
        list.sort((o1, o2) -> o1.getValue() - o2.getValue());

        loadItems(ir, list.stream().map(entry -> entry.getKey()).toList());
    }

    // by Deep Jain Levenshtein Distance
    // https://www.baeldung.com/java-levenshtein-distance
    static int calculate(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1]
                            + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);
                }
            }
        }

        return dp[x.length()][y.length()];
    }

    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    public static int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }
}
