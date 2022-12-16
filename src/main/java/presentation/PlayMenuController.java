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

    public void back() {
        MainWindowController mwc = (MainWindowController) root.getScene().getUserData();
        mwc.navigateHome();
    }

    public void load(String itemTitle, String episodeTitle) {
        System.out.println("Loading " + itemTitle + " " + episodeTitle);
        title.setText("Wathcing: " + itemTitle);
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
