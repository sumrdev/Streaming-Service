package presentation;

import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PopupController  {
    @FXML
    private HBox root;
    @FXML
    private ImageView image;
    @FXML
    private VBox infoBox;
    @FXML
    private VBox episodeListBox;
    @FXML
    private VBox bottom;
    @FXML
    private Button playButton;
    @FXML
    private ChoiceBox<String> seasonChoiceBox;
    
    private DomainAccess da;
    private String itemTitle = "";

    /**
     * Initializes and hides the popup
     * @param da the domain access to use
     */
    public void initialize(DomainAccess da) {
        this.da = da;
        hide();
    }

    /**
     * Gets relevant item information and displays it in the popup
     * @param itemKey the key of the item
     * @param itemImg the image object of the item
     */
    public void setMovie(String itemKey, Image itemImg) {
        image.setImage(itemImg);
        itemTitle = da.getItemTitle(itemKey);
        infoBox.getChildren().clear();
        infoBox.getChildren().add(new Text(itemTitle));
        infoBox.getChildren().add(new Text("Rating: " + da.getItemRating(itemKey)));
        infoBox.getChildren().add(new Text("Genre: " + (""+Arrays.asList(da.getItemGenre(itemKey))).replace("[", "").replace("]", "") ));
        infoBox.getChildren().add(new Text("Release: " + da.getItemReleaseYear(itemKey)));
        infoBox.getChildren().add(playButton);

        bottom.setVisible(false);
    }

    /**
     * Gets relevant item information and displays it in the popup
     * Genereates a list of episodes for the selected season
     * @param itemKey the key of the item
     * @param itemImg the image object of the item
     */
    public void setSeries(String itemKey, Image itemImg ) {
        image.setImage(itemImg);
        itemTitle = da.getItemTitle(itemKey);
        infoBox.getChildren().clear();
        infoBox.getChildren().add(new Text(itemTitle));
        infoBox.getChildren().add(new Text("Rating: " + da.getItemRating(itemKey)));
        infoBox.getChildren().add(new Text("Genre: " + (""+Arrays.asList(da.getItemGenre(itemKey))).replace("[", "").replace("]", "") ));
        infoBox.getChildren().add(new Text("Release: " + da.getItemReleaseYear(itemKey)));
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
    }

    /**
     * Shows the popup
     */
    public void show() {
        root.setVisible(true);
    }

    /**
     * Hides the popup
     */
    public void hide() {
        root.setVisible(false);
    }

    /**
     * Navigate to the play menu and play the selected item
     */
    public void play() {
        MainWindowController mwc = (MainWindowController) root.getScene().getUserData();
        mwc.playMovie(this.itemTitle, "");
    }

    /**
     * Navigate to the play menu and play the selected episode
     * @param episode the episode identifier
     */
    private void play(String episode) {
        MainWindowController mwc = (MainWindowController) root.getScene().getUserData();
        mwc.playMovie(this.itemTitle, episode);
    }
    
    /**
     * Consumes the mouse event to prevent the popup from closing, when clicking on the popup
     */
    public void consumeClick(MouseEvent me) {
        me.consume();
    }

    /**
     * Generates a list of episodes for the selected season
     * @param amount the amount of episodes in the season
     */
    private void setEpisodes(int amount) {
        episodeListBox.getChildren().clear();
        for (int i = 0; i < amount; i++) {
            episodeListBox.getChildren().add(createEpisodePane(i+1));
        }
    }

    /**
     * Creates a pane for an episode
     * @param n the episode number
     * @return the pane in the form of an HBox
     */
    private HBox createEpisodePane(int n) {
        HBox episode = new HBox();
        episode.setOnMouseClicked(e -> {
            play(""+n);
        });
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
