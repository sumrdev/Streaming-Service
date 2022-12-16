package presentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import domain.ItemRegistry;
import domain.ItemRegistryImpl;
import domain.UserRegistry;
import domain.UserRegistryImpl;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;


public class DomainAccess {
    private ItemRegistry ir = null;
    private UserRegistry ur = null;
    /**
     * List used to keep track of the favorite items of the currently selected user.
     * Automatically saves changes to the user registry.
     */
    private ObservableList<String> favoriteItems = FXCollections.observableArrayList();

    private HashMap<String, StackPane> itemPanes = new HashMap<>(); 
    private Parent popup;

    /**
     * Creates a new DomainAccess object and sets up popup, itemPanes and favoritelist.
     * @throws IOException if the itemPopup.fxml file cannot be found.
     */
    public DomainAccess() throws IOException {
        this.ir = new ItemRegistryImpl();
        this.ur = new UserRegistryImpl();

        FXMLLoader popupLoader = new FXMLLoader(getClass().getClassLoader().getResource("itemPopup.fxml"));
        popup = popupLoader.load();
        PopupController puc = popupLoader.getController();
        puc.initialize(this);
        itemPanes = createItemPanes(puc);

        favoriteItems.addListener((ListChangeListener<String>) c -> {
            String user = ur.getSelectedUser();
            if (user == null) {
                return;
            };
            while (c.next()) {
                if (c.wasAdded()) {
                    for (String item : c.getAddedSubList()) {
                        ur.addFavoriteItem(user, item);
                    }
                }
                if (c.wasRemoved()) {
                    for (String item : c.getRemoved()) {
                        ur.removeFavoriteItem(user, item);
                    }
                }
            }
            ur.save();
        });
    }
    /**
     * Creates a List of StackPanes from a list of item keys.
     * @param keyList the list of item keys.
     * @return a list of StackPanes.
     */
    public List<StackPane> getItemPanes(List<String> keyList) {
        return keyList.stream().map(key -> itemPanes.get(key)).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * Gets the getFavoriteItems of the currently selected user
     * @return ObservableList<String> of favorite items.
     */
    public ObservableList<String> getFavoriteItems() {
        return favoriteItems;
    }

    /**
     * Selects a user and updates the favoriteItems list.
     * @param userKey
     */
    public void selectUser(String userKey) {
        ur.selectUser(userKey);
        favoriteItems.clear();
        favoriteItems.addAll(ur.getFavoriteItems(userKey));
    }

    /**
     * Changes the currently selected user to null.
     */
    public void changeUser() {
        ur.selectUser(null);
        favoriteItems.clear();
    }

    /**
     * Gets all genres as an array of strings.
     * @return String[] of genres.
     */
    public String[] getGenreStrings() {
        return ir.getGenreSet().stream().toArray(String[]::new);
    }

    /**
     * Gets Movie key list.
     * @return ArrayList<String>
     */
    public ArrayList<String> getMovieKeyList() {
        return ir.getMovieKeyList();
    }

    /**
     * Gets Series key list.
     * @return ArrayList<String>
     */
    public ArrayList<String> getSeriesKeyList() {
        return ir.getSeriesKeyList();
    }

    /**
     * Gets item title.
     * @param itemKey the key of the item.
     * @return String of the item title.
     */
    public String getItemTitle(String itemKey) {
        return ir.getItemTitle(itemKey);
    }

    /**
     * Gets item Release Year.
     * @param itemKey the key of the item.
     * @return int of the ReleaseYear.
     */
    public int getItemReleaseYear(String itemKey) {
        return ir.getItemReleaseYear(itemKey);
    }

    /**
     * Gets item Genres.
     * @param itemKey the key of the item.
     * @return String[] of the Genres.
     */
    public String[] getItemGenre(String itemKey) {
        return ir.getItemGenre(itemKey);
    }

    /**
     * Gets item Rating.
     * @param itemKey the key of the item.
     * @return double of the Rating.
     */
    public double getItemRating(String itemKey) {
        return ir.getItemRating(itemKey);
    }

    /**
     * Gets Series Seasons.
     * @param itemKey the key of the item. (a series)
     * @return int[] of the Seasons.
     */
    public int[] getSeriesSeasons(String itemKey) {
        return ir.getSeriesSeasons(itemKey);
    }

    /**
     * Gets Series EndYear.
     * @param itemKey the key of the item. (a series)
     * @return int of the Seasons.
     */
    public int getSeriesEndYear(String itemKey) {
        return ir.getSeriesEndYear(itemKey);
    }

    /**
     * Gets Username List.
     * @return ArrayList<String> of User.
     */
    public ArrayList<String> getUsernameList() {
        return ur.getUsernameList();
    }

    /**
     * Removes a User.
     * @param userKey the key of the user.
     */
    public void removeUser(String userKey) {
        ur.removeUser(userKey);
    }

    /**
     * Adds a User.
     * @param userKey the key of the user.
     * @throws IllegalArgumentException if the userKey is already in use.
     */
    public void addUser(String userKey) throws IllegalArgumentException{
        ur.addUser(userKey);
    }

    /**
     * Gets the currently selected user.
     * @return String of the selected user.
     */
    public String getSelectedUser() {
        return ur.getSelectedUser();
    }

    /**
     * Saves the user registry.
     */
    public void save() {
        ur.save();
    }

    /**
     * Gets the popup controller.
     * @return Popup node as Parent.
     */
    public Parent getPopup() {
        return popup;
    }

    /**
     * Creates a HashMap of StackPanes from the item registry.
     * @param popup the popup controller used to initialize the item panes.
     * @return a HashMap of StackPanes.
     */
    public HashMap<String, StackPane> createItemPanes(PopupController popup) {
        HashMap<String, StackPane> itemNodes = new HashMap<>();
        ArrayList<String> movies = ir.getMovieKeyList();
        ArrayList<String> series = ir.getSeriesKeyList();
        
        for (String movieKey : movies) {
            try {
                FXMLLoader ItemLoader = new FXMLLoader(getClass().getClassLoader().getResource("item.fxml"));
                itemNodes.put(movieKey, ItemLoader.load());
                ItemPaneController controller = ItemLoader.getController();
                String movie = ir.getItemTitle(movieKey);
                controller.initialize(movieKey, movie, "/movie_img/" + movie + ".jpg", favoriteItems, popup, "movie");
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error loading movie");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
        for (String serieKey : series) {
            try {
                FXMLLoader ItemLoader = new FXMLLoader(getClass().getClassLoader().getResource("item.fxml"));
                itemNodes.put(serieKey, ItemLoader.load());
                ItemPaneController controller = ItemLoader.getController();
                String serie = ir.getItemTitle(serieKey);
                controller.initialize(serieKey, serie, "/show_img/" + serie + ".jpg", favoriteItems, popup, "series");
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error loading Series");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        } 
        return itemNodes;
    }
}
