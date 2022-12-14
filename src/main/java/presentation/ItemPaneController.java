package presentation;

import java.io.IOException;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ItemPaneController  {
    private double maxScaleSize = 1.1;
    private Image img;
    private String itemKey;
    private ObservableList<String> favoriteList;
    private PopupController popup;
    private String type;

    @FXML
    StackPane itemPane;

    @FXML
    ImageView imageContainer;

    @FXML
    Text itemNameText;

    @FXML
    Button favoriteButton;

    public void initialize(String itemkey, String itemName, String imgPath, ObservableList<String> favoriteList, PopupController popup, String type) throws IOException {
        this.itemKey = itemkey;
        this.favoriteList = favoriteList;
        this.popup = popup;
        if (!type.equals("movie") && !type.equals("series")) {
            throw new IllegalArgumentException("Type must be either movie or series");
        }
        this.type = type;
        img = new Image(getClass().getResourceAsStream(imgPath));
        imageContainer.setImage(img);
        itemNameText.setText(itemName);
        favoriteList.addListener((ListChangeListener<String>) c -> {
            if (favoriteList.contains(itemkey)) {
                favoriteButton.setTextFill(Color.web("#FFD700"));
            } else {
                favoriteButton.setTextFill(Color.web("#FFFFFF"));
            }
        });
        Transitions.addScaleFadeTransition(itemPane, maxScaleSize);

    }

    public void showPopup() {
        if (type.equals("movie")) {
            popup.setMovie(itemKey, img);
        } else {
            popup.setSeries(itemKey, img);
        }
        popup.show();
    }

    public void toggleFavorites() {
        if (favoriteList.contains(itemKey)) {
            favoriteList.remove(itemKey);
        } else {
            favoriteList.add(itemKey);
        }
    }
}
