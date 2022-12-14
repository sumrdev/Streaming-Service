package presentation;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Popup extends HBox {
    private ImageView image;
    private VBox infoBox;
    private VBox episodeListBox;
    private VBox bottom;
    private Button playButton;
    private ChoiceBox<String> seasonChoiceBox;
    @SuppressWarnings("unchecked") //ChoiceBox generic cast not supported 
    public Popup(MainWindow mw) throws IOException, ClassCastException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("itemPopup.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        fxmlLoader.load();
        this.setOnMouseClicked(e -> this.setVisible(false));
        VBox main = (VBox) this.getChildren().get(0);
        main.setOnMouseClicked(e -> e.consume());
        HBox Top = (HBox) main.getChildren().get(0);
        image = (ImageView) Top.getChildren().get(0);
        infoBox = (VBox) Top.getChildren().get(2);

        bottom = (VBox) main.getChildren().get(1);
        HBox BottomInfo = (HBox) bottom.getChildren().get(0);
        seasonChoiceBox = (ChoiceBox<String>) BottomInfo.getChildren().get(2);

        ScrollPane BottomPane = (ScrollPane) bottom.getChildren().get(1);
        episodeListBox = (VBox) BottomPane.getContent();

        playButton = new Button("Play");
        playButton.setOnAction(e -> {
            mw.playMovie();
        });
        playButton.getStyleClass().add("normal-button");

        this.setVisible(false);
    }


    public void setMovie(String title, String[] genre, double rating,  Image itemImg, int release) {
        image.setImage(itemImg);
        infoBox.getChildren().clear();
        infoBox.getChildren().add(new Text(title));
        infoBox.getChildren().add(new Text("Rating: " + rating));
        infoBox.getChildren().add(new Text("Genre: " + genre[0]));
        infoBox.getChildren().add(new Text("Release: " + release));
        infoBox.getChildren().add(playButton);

        bottom.setVisible(false);

    }
    public void setSeries(String title, String[] genre, double rating, Image itemImg, int startYear, int endYear, int[] seasons ) {
        image.setImage(itemImg);
        infoBox.getChildren().clear();
        infoBox.getChildren().add(new Text(title));
        infoBox.getChildren().add(new Text("Rating: " + rating));
        infoBox.getChildren().add(new Text("Genre: " + genre[0]));
        infoBox.getChildren().add(new Text("Release: " + startYear));
        if(endYear != 0) infoBox.getChildren().add(new Text("End: " + endYear));
        else infoBox.getChildren().add(new Text("End: Still running"));
        infoBox.getChildren().add(playButton);

        seasonChoiceBox.setOnAction (null); // remove old event handler
        seasonChoiceBox.getItems().clear();
        for (int i = 0; i < seasons.length; i++) {
            seasonChoiceBox.getItems().add("Season " + (i+1));
        }
        seasonChoiceBox.getSelectionModel().select(0);
        setEpisodes(seasons[0]);
        seasonChoiceBox.setOnAction(e -> {
            setEpisodes(seasons[seasonChoiceBox.getSelectionModel().getSelectedIndex()]);
        });
        bottom.setVisible(true);
    }

    public void show() {
        this.setVisible(true);
    }


    private void setEpisodes(int amount) {
        episodeListBox.getChildren().clear();
        for (int i = 0; i < amount; i++) {
            episodeListBox.getChildren().add(createEpisodePane(i+1));
        }
    }

    private HBox createEpisodePane(int n) {
        HBox episode = new HBox();
        Text t = new Text("Episode " + n);
        t.setFill(Color.WHITE);
        episode.getChildren().add(t);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        episode.getChildren().add(spacer);
        
        Image img = new Image(getClass().getResourceAsStream("/play.png"));
        ImageView play = new ImageView(img);
        play.setFitHeight(20);
        play.setFitWidth(20);
        episode.getChildren().add(play);
        
        episode.setPrefHeight(25);
        episode.setPadding(new Insets(5, 5, 5, 5));
        episode.setStyle("-fx-background-color: #253046");

        return episode;
    }

}
