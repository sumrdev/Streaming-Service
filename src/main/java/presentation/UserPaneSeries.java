

package presentation;

import java.io.IOException;

import javafx.collections.ObservableList;

public class UserPaneSeries extends UserPane {
    public UserPaneSeries(String itemkey, String itemName, String[] genres, double rating, int startYear, int endYear, int[] seasons, String imgPath, ObservableList<String> favoriteList, Popup popup) throws IOException {
        super(itemkey, itemName, imgPath, favoriteList);
        this.setOnMouseClicked(e -> {
            popup.setSeries(itemName, genres, rating, img, startYear, endYear, seasons);
            popup.show();
        });
    }
}