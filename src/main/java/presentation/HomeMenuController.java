package presentation;

import java.util.*;
import java.util.stream.*;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class HomeMenuController {
    @FXML
    FlowPane itemGrid;

    @FXML
    ChoiceBox<String> choiceGenre;

    @FXML
    ChoiceBox<String> choiceCategory;

    @FXML
    TextField searchBox;
    
    @FXML
    StackPane MainStackpane;

    private DomainAccess da = null;
    private List<String> currentItems = null;

    public void initialize(DomainAccess da) {
        try {
            this.da = da;
            String[] genres = da.getGenreStrings();
            choiceGenre.getItems().addAll("All");
            choiceGenre.getItems().addAll(genres);
            choiceGenre.getSelectionModel().selectFirst();
            choiceCategory.getItems().addAll("All", "Series", "Movies");
            choiceCategory.getSelectionModel().selectFirst();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unload() {
        itemGrid.getChildren().clear();
        MainStackpane.getChildren().remove(da.getPopup());
    }

    public void load() {
        unload();
        MainStackpane.getChildren().add(da.getPopup());
        updateItemFilters();
    }

    public void updateItemFilters() {
        String genre = choiceGenre.getValue();
        String category = choiceCategory.getValue();

        Stream<String> items = Stream.concat(da.getMovieKeyList().stream(), da.getSeriesKeyList().stream());
        if (genre != null) {
            if (!genre.equals("All")) {
                items = items.filter(item -> Arrays.asList(da.getItemGenre(item)).contains(genre));
            }
        }

        if (category != null) {
            if (category.equals("Movies")) {
                items = items.filter(item -> da.getSeriesSeasons(item) == null);
            } else if (category.equals("Series")) {
                items = items.filter(item -> da.getSeriesSeasons(item) != null);
            }
        }
        currentItems = items.toList();
        if (searchBox.getText().length() > 0) {
            search(null);
        } else {
            itemGrid.getChildren().clear();
            itemGrid.getChildren().addAll(da.getItemPanes(currentItems));
        }
    }

    public void search(Event e) {
        String search = searchBox.getText();
        HashMap<String, Integer> searchResults = new HashMap<>();
        for (String item : currentItems) {
            String title =  da.getItemTitle(item);
            searchResults.put(item, calculate(title, search) * 10);
        }

        // add search string that cantains the search string to the list
        for (String item : currentItems) {
            String title =  da.getItemTitle(item);
            if (title.toLowerCase().equals(search.toLowerCase())) {
                searchResults.put(item, 0);
            }
            else if (title.toLowerCase().contains(search.toLowerCase())) {
                searchResults.put(item, calculate(item, search));
            }
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(searchResults.entrySet());
        list.sort((o1, o2) -> o1.getValue() - o2.getValue());
        itemGrid.getChildren().clear();
        itemGrid.getChildren().addAll(da.getItemPanes(list.stream().map(entry -> entry.getKey()).toList()));
    }

    // https://www.baeldung.com/java-levenshtein-distance

    //returns the levenshtein distance between two strings
    public static int calculate(String str1, String str2) {
        int[][] distance = new int[str1.length() + 1][str2.length() + 1];
    
        for (int i = 0; i <= str1.length(); i++)
            distance[i][0] = i;
        for (int j = 1; j <= str2.length(); j++)
            distance[0][j] = j;

        for (int i = 1; i <= str1.length(); i++)
            for (int j = 1; j <= str2.length(); j++)
                distance[i][j] = min(
                        distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1,
                        distance[i - 1][j - 1] + costOfSubstitution(str1.charAt(i - 1), str2.charAt(j - 1)));

        return distance[str1.length()][str2.length()];
    }

    private static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    private static int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }
}
