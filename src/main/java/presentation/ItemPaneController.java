package presentation;

import java.io.IOException;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private StackPane itemPane;

    @FXML
    private ImageView imageContainer;

    @FXML
    private Text itemNameText;

    @FXML
    private Button favoriteButton;

    /**
     * Initializes the item pane with the given parameters
     * @param itemkey the key of the item
     * @param itemName the name of the item
     * @param imgPath the path to the image of the item
     * @param favoriteList the list of favorite items to add or remove the item from
     * @param popup the popup controller to show on click
     * @param type the type of the item, either movie or series
     * @throws IOException if the image path is invalid
     */
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

    /**
     * Shows the corresponding popup for the item 
     */
    public void showPopup() {
        if (type.equals("movie")) {
            popup.setMovie(itemKey, img);
        } else {
            popup.setSeries(itemKey, img);
        }
        popup.show();
    }

    /**
     * Toggles the item between being on the favoriteList and not being on the favoriteList
     */
    public void toggleFavorites() {
        if (favoriteList.contains(itemKey)) {
            favoriteList.remove(itemKey);
        } else {
            favoriteList.add(itemKey);
        }
    }
}
