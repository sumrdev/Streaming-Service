package presentation;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class PlayMenuController {
    @FXML
    private BorderPane root;
    @FXML
    private Text title;
    @FXML
    private Text episode;

    /**
     * Navigates back to the home screen.
     */
    public void back() {
        MainWindowController mwc = (MainWindowController) root.getScene().getUserData();
        mwc.navigateHome();
    }

    /**
     * Sets the given itemTitle and episodeTitle onto the play menu.
     * @param itemTitle String title of the item to play.
     * @param episodeTitle String of episode identifier of the item to play.
     */
    public void load(String itemTitle, String episodeTitle) {
        title.setText("Watching: " + itemTitle);
        if (!episodeTitle.equals("")) {
            episode.setText("Episode: "+episodeTitle);
        } else {
            episode.setText("");
        }
    }
    public void load(String itemTitle) {
        title.setText(itemTitle);
    }
}
