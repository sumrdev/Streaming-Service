package presentation;

import java.util.ArrayList;
import java.util.HashMap;

import domain.ItemRegistry;
import domain.ItemRegistryImpl;
import domain.UserRegistry;
import domain.UserRegistryImpl;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;


public class FrontEndHelper {
    ItemRegistry ir = null;
    UserRegistry ur = null;
    ObservableList<String> favoriteItems = FXCollections.observableArrayList();

    public FrontEndHelper() {
        this.ir = new ItemRegistryImpl();
        this.ur = new UserRegistryImpl();
        this.ir.initialize();
        this.ur.initialize();
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

    public void selectUser(String user) {
        ur.selectUser(user);
        favoriteItems.clear();
        favoriteItems.addAll(ur.getFavoriteItems(user));
    }

    public void changeUser() {
        ur.selectUser(null);
        favoriteItems.clear();
    }
    
    public HashMap<String, UserPane> createItemPanes(Popup popup) {
        System.out.println("Creating item panes");
        HashMap<String, UserPane> itemNodes = new HashMap<>();
        ArrayList<String> movies = ir.getMovieKeyList();
        ArrayList<String> series = ir.getSeriesKeyList();
        for (String movieKey : movies) {
            try {
                String movie = ir.getItemTitle(movieKey);
                itemNodes.put(movieKey, new UserPaneMovie(movieKey, movie, ir.getItemGenre(movieKey), ir.getItemRating(movieKey), ir.getItemRelease(movieKey), "/movie_img/" + movie + ".jpg", favoriteItems, popup));
            } catch (Exception e) {
                System.out.println("Error loading movie: " + ir.getItemTitle(movieKey) + " - " + e.getMessage());
            }
        }
        for (String serieKey : series) {
            try {
                String serie = ir.getItemTitle(serieKey);
                itemNodes.put(serieKey, new UserPaneSeries(serieKey, serie,  ir.getItemGenre(serieKey), ir.getItemRating(serieKey), ir.getItemRelease(serieKey), ir.getSeriesEndYear(serieKey), ir.getSeriesSeasons(serieKey), "/show_img/" + serie + ".jpg", favoriteItems, popup));
            } catch (Exception e) {
                System.out.println("Error loading movie: " + ir.getItemTitle(serieKey) + " - " + e.getMessage());
            }
        } 
        return itemNodes;
    }

    
}
