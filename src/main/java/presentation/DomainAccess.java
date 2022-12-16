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

    public ObservableList<String> getFavoriteItems() {
        return favoriteItems;
    }

    public void selectUser(String user) {
        ur.selectUser(user);
        favoriteItems.clear();
        favoriteItems.addAll(ur.getFavoriteItems(user));
    }

    public void changeUser() {
        ur.selectUser(null);
        favoriteItems.clear();
    }

    public String[] getGenreStrings() {
        return ir.getGenreSet().stream().toArray(String[]::new);
    }

    public ArrayList<String> getMovieKeyList() {
        return ir.getMovieKeyList();
    }

    public ArrayList<String> getSeriesKeyList() {
        return ir.getSeriesKeyList();
    }

    public String getItemTitle(String itemKey) {
        return ir.getItemTitle(itemKey);
    }

    public int getItemReleaseYear(String itemKey) {
        return ir.getItemReleaseYear(itemKey);
    }

    public String[] getItemGenre(String itemKey) {
        return ir.getItemGenre(itemKey);
    }
    
    public double getItemRating(String itemKey) {
        return ir.getItemRating(itemKey);
    }
    
    public int[] getSeriesSeasons(String itemKey) {
        return ir.getSeriesSeasons(itemKey);
    }
    public int getSeriesEndYear(String itemKey) {
        return ir.getSeriesEndYear(itemKey);
    }
    
    public ArrayList<String> getUsernameList() {
        return ur.getUsernameList();
    }

    public void removeUser(String user) {
        ur.removeUser(user);
    }

    public void addUser(String user) throws IllegalArgumentException{
        ur.addUser(user);
    }
    
    public String getSelectedUser() {
        return ur.getSelectedUser();
    }

    public void save() {
        ur.save();
    }

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
