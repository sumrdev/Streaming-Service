package presentation;

import java.io.IOException;
import java.security.AllPermission;
import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PopupController  {
    @FXML
    HBox root;
    @FXML
    ImageView image;
    @FXML
    VBox infoBox;
    @FXML
    VBox episodeListBox;
    @FXML
    VBox bottom;
    @FXML
    Button playButton;
    @FXML
    ChoiceBox<String> seasonChoiceBox;

    private DomainAccess da;
    private MainWindow mw;

    public void initialize(MainWindow mw, DomainAccess da) throws IOException, ClassCastException {
        this.da = da;
        this.mw = mw;
        hide();
    }


    public void setMovie(String itemKey, Image itemImg) {
        image.setImage(itemImg);
        infoBox.getChildren().clear();
        infoBox.getChildren().add(new Text(da.getItemTitle(itemKey)));
        infoBox.getChildren().add(new Text("Rating: " + da.getItemRating(itemKey)));
        infoBox.getChildren().add(new Text("Genre: " + (""+Arrays.asList(da.getItemGenre(itemKey))).replace("[", "").replace("]", "") ));
        infoBox.getChildren().add(new Text("Release: " + da.getItemRelease(itemKey)));
        infoBox.getChildren().add(playButton);

        bottom.setVisible(false);

    }
    public void setSeries(String itemKey, Image itemImg ) {
        image.setImage(itemImg);
        infoBox.getChildren().clear();
        infoBox.getChildren().add(new Text(da.getItemTitle(itemKey)));
        infoBox.getChildren().add(new Text("Rating: " + da.getItemRating(itemKey)));
        infoBox.getChildren().add(new Text("Genre: " + (""+Arrays.asList(da.getItemGenre(itemKey))).replace("[", "").replace("]", "") ));
        infoBox.getChildren().add(new Text("Release: " + da.getItemRelease(itemKey)));
        int endYear = da.getSeriesEndYear(itemKey);
        if(endYear != 0) infoBox.getChildren().add(new Text("End: " + endYear));
        else infoBox.getChildren().add(new Text("End: Still running"));
        infoBox.getChildren().add(playButton);

        seasonChoiceBox.setOnAction (null); // remove old event handler
        seasonChoiceBox.getItems().clear();
        int[] seasons = da.getSeriesSeasons(itemKey);
        for (int i = 0; i < seasons.length; i++) {
            seasonChoiceBox.getItems().add("Season " + (i+1));
        }
        seasonChoiceBox.getSelectionModel().select(0);
        setEpisodes(seasons[0]);
        seasonChoiceBox.setOnAction(e -> {
            setEpisodes(seasons[seasonChoiceBox.getSelectionModel().getSelectedIndex()]);
        });
        bottom.setVisible(true);
        //set alert
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("You can select a season and episode to play");
        alert.show();

    }

    public void show() {
        root.setVisible(true);
    }

    public void hide() {
        root.setVisible(false);
    }

    public void play() {
        mw.playMovie();
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
