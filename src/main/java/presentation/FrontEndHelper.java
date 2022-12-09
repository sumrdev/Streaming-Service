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

    public FrontEndHelper(MainWindowController mmc) {
        this.ir = new ItemRegistryImpl();
        this.ur = new UserRegistryImpl();
        this.ir.initialize();
        this.ur.initialize();
        favoriteItems.addListener((ListChangeListener<String>) c -> {
            String user = ur.getSelectedUser();
            if (user == null) {
                mmc.navigateLogin();
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

    public void logout() {
        ur.selectUser(null);
        favoriteItems.clear();
    }
    


    public HashMap<String, UserPane> createItemPanes() {
        System.out.println("Creating item panes");
        HashMap<String, UserPane> itemNodes = new HashMap<>();
        ArrayList<String> movies = ir.getMovieKeyList();
        ArrayList<String> series = ir.getSeriesKeyList();
        for (String movieKey : movies) {
            try {
                String movie = ir.getItemTitle(movieKey);
                itemNodes.put(movieKey, new UserPane(movieKey, movie, "/movie_img/" + movie + ".jpg", favoriteItems));
            } catch (Exception e) {
                System.out.println("Error loading movie: " + ir.getItemTitle(movieKey) + " - " + e.getMessage());
            }
        }
        for (String serieKey : series) {
            try {
                String serie = ir.getItemTitle(serieKey);
                itemNodes.put(serieKey, new UserPane(serieKey, serie, "/show_img/" + serie + ".jpg", favoriteItems));
            } catch (Exception e) {
                System.out.println("Error loading movie: " + ir.getItemTitle(serieKey) + " - " + e.getMessage());
            }
        } 
        return itemNodes;
    }

    
}
