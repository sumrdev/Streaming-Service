package presentation;

import javafx.fxml.FXML;
import domain.UserRegistry;
import domain.ItemRegistry;
import domain.UserRegistryImpl;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Button;
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
    // main Panes
    @FXML
    VBox userPane;
    @FXML
    VBox userSelect;
    @FXML
    VBox userCreation;

    // when logged in (userPane)
    @FXML
    FlowPane favoritePane;

    // when choosing user (userSelect)
    @FXML
    FlowPane currentUsers;

    // when creating user (userCreation)
    @FXML
    TextField usernameInput;

    public UserRegistry ur;
    public ItemRegistry ir;

    public void initialize(ItemRegistry ir) {
        this.ir = ir;
        ur = new UserRegistryImpl();
        ur.initialize();
        loadCurrentUsers();
    }

    public void loadCurrentUsers() {
        currentUsers.getChildren().clear();
        if (ur.getUsernameList().size() == 0) {
            Text noUsersText = new Text("There are no users yet!");
            noUsersText.setStyle("-fx-font: 40 open-sans;");
            noUsersText.setFill(javafx.scene.paint.Color.WHITE);
            currentUsers.getChildren().add(noUsersText);
            return;
        }
        for (String name : ur.getUsernameList()) {
            VBox v = new VBox();
            v.setAlignment(javafx.geometry.Pos.CENTER);

            StackPane avatar = new StackPane();

            ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/avatar.png")));
            avatar.getChildren().add(img);
            Button delete = new Button("✖");
            delete.setOnAction(e -> deleteUser(name));
            avatar.getChildren().add(delete);
            StackPane.setAlignment(delete, javafx.geometry.Pos.TOP_RIGHT);

            v.getChildren().add(avatar);

            Text username = new Text(name);
            username.setStyle("-fx-font: 20 open-sans;");
            username.setFill(javafx.scene.paint.Color.WHITE);
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
        selectView("userCreation");
    }

    public void createNewUser() {
        System.out.println("he" + usernameInput.getText());
        if (usernameInput.getText().equals(""))
            return;
        try {
            ur.addUser(usernameInput.getText());
            ur.save();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        loadCurrentUsers();
        selectView("userSelect");
    }

    public void selectUser(String username) {
        ur.selectUser(username);
        loadFavorites(ur.getFavoriteItems(username));
        selectView("userPane");
    }

    public void deleteUser(String username) {
        ur.removeUser(username);
        loadCurrentUsers();
    }

    public void selectView(String s) {
        switch (s) {
            case "userPane":
                userPane.setVisible(true);
                userSelect.setVisible(false);
                userCreation.setVisible(false);
                break;
            case "userSelect":
                userPane.setVisible(false);
                userSelect.setVisible(true);
                userCreation.setVisible(false);
                break;
            case "userCreation":
                userPane.setVisible(false);
                userSelect.setVisible(false);
                userCreation.setVisible(true);
                break;
        }
    }

}
