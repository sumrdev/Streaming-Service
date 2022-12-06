package presentation;
import javafx.fxml.FXML;
import domain.UserRegistry;
import domain.ItemRegistry;
import domain.UserRegistryImpl;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.util.HashSet;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
public class LoginMenuController {
    //main Panes
    @FXML
    HBox userPane;
    @FXML
    HBox userSelect;
    @FXML
    HBox userCreation;

    //when logged in (userPane)
    @FXML
    FlowPane favoritePane;

    //when choosing user (userSelect)
    @FXML
    FlowPane currentUsers;

    //when creating user (userCreation)
    @FXML
    TextField usernameInput;

    public UserRegistry ur;
    public ItemRegistry ir;

    public void initialize(ItemRegistry ir) {
        this.ir = ir;
        UserRegistry ur = new UserRegistryImpl();
        ur.initialize();
    }

    public void loadCurrentUsers() {
        currentUsers.getChildren().clear();
        for (String name : ur.getUsernameList()) {
            VBox v = new VBox();
            v.setAlignment(javafx.geometry.Pos.CENTER);
            ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/avatar.png")));
            v.getChildren().add(img);

            Text username = new Text(name);
            username.setText(name);
            v.getChildren().add(username);
            v.setOnMouseClicked(e -> selectUser(name));
            currentUsers.getChildren().add(v);
        }
    }

    public void loadFavorites(HashSet<String> keyList) {
        favoritePane.getChildren().clear();
        keyList.forEach(item -> {
            try {
                StackPane itemPane = (StackPane) FXMLLoader.load(getClass().getClassLoader().getResource("item.fxml"));
                ImageView image = (ImageView) itemPane.getChildren().get(0);
                Image img;
                // System.out.println(item);
                if (ir.getSeriesSeasons(item) == null) {
                    img = new Image(getClass().getResourceAsStream("/movie_img/" + item + ".jpg"));
                } else {
                    img = new Image(getClass().getResourceAsStream("/show_img/" + item + ".jpg"));
                }
                image.setImage(img);

                Text text = (Text) itemPane.getChildren().get(2);
                text.setText(item);
                favoritePane.getChildren().add(itemPane);


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    public void showUserCreation() {
        userCreation.setVisible(true);
    }

    public void createNewUser() {
        ur.addUser(usernameInput.getText());
        loadCurrentUsers();
        userCreation.setVisible(false);
    }

    public void selectUser(String username) {
        ur.selectUser(username);
        userSelect.setVisible(false);
        loadFavorites(ur.getFavoriteItems(username));
    }

}

