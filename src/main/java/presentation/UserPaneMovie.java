package presentation;

import java.io.IOException;

import javafx.collections.ObservableList;

public class UserPaneMovie extends UserPane {
    public UserPaneMovie(String itemkey, String itemName, String[] genres, double rating, int release, String imgPath, ObservableList<String> favoriteList, Popup popup) throws IOException {
        super(itemkey, itemName, imgPath, favoriteList);
        this.setOnMouseClicked(e -> {
            System.out.println("Clicked on " + itemName);
            popup.setMovie(itemName, genres, rating, this.img, release);
            popup.show();
        });
    }

}
