package presentation;

import java.io.IOException;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public abstract class ItemPane extends StackPane  {
    private double maxScaleSize = 1.1;
    protected Image img;

    public ItemPane(String itemkey, String itemName, String imgPath, ObservableList<String> favoriteList) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("item.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        fxmlLoader.load();
        ImageView image = (ImageView) this.getChildren().get(0);
        img = new Image(getClass().getResourceAsStream(imgPath));
        image.setImage(img);
        StackPane overlay = (StackPane) this.getChildren().get(1);
        Text text = (Text) overlay.getChildren().get(1);
        text.setText(itemName);

        Button favorite = (Button) ((HBox) overlay.getChildren().get(2)).getChildren().get(0);
        favoriteList.addListener((ListChangeListener<String>) c -> {
            if (favoriteList.contains(itemkey)) {
                favorite.setTextFill(Color.web("#FFD700"));
            } else {
                favorite.setTextFill(Color.web("#FFFFFF"));
            }
        });

        favorite.setOnAction(e -> {
            toggleFavorites(itemkey, favoriteList);

        });
        Transitions.addScaleFadeTransition(this, maxScaleSize);

    }




    private void toggleFavorites(String key, ObservableList<String> favoriteItems) {
        if (favoriteItems.contains(key)) {
            favoriteItems.remove(key);
        } else {
            favoriteItems.add(key);
        }
    }


}
