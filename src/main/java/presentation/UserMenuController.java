package presentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UserMenuController {
    // main Panes
    @FXML
    StackPane MainStackpane;

    @FXML
    VBox userPane;
    @FXML
    VBox userSelect;
    @FXML
    VBox userCreation;

    // when logged in (userPane)
    @FXML
    FlowPane favoritePane;
    @FXML
    Text usernameText;

    // when choosing user (userSelect)
    @FXML
    FlowPane currentUsers;

    // when creating user (userCreation)
    @FXML
    TextField usernameInput;

    private FrontEndHelper feh;
    private ObservableList<String> users = FXCollections.observableArrayList();
    private HashMap<String, UserPane> itemNodes = new HashMap<>(); 

    public void initialize(FrontEndHelper feh) {
        this.feh = feh;
        Popup popup;
        try {
            popup = new Popup();
            MainStackpane.getChildren().add(popup);
            this.itemNodes = feh.createItemPanes(popup);
        } catch (ClassCastException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        feh.favoriteItems.addListener((ListChangeListener<String>) c -> {
            favoritePane.getChildren().clear();
            favoritePane.getChildren().addAll(getItemPanes(feh.favoriteItems));
        });
        
        users.addAll(feh.ur.getUsernameList());
        currentUsers.getChildren().clear();
        currentUsers.getChildren().addAll(getUserPanes());
        users.addListener((ListChangeListener<String>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    System.out.println("Added: " + c.getAddedSubList());
                    for (String user : c.getAddedSubList()) {
                        try {
                            feh.ur.addUser(user);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                if (c.wasRemoved()) {
                    System.out.println("Removed: " + c.getRemoved());
                    for (String user : c.getRemoved()) {
                        feh.ur.removeUser(user);
                    }
                }
            }
            currentUsers.getChildren().clear();
            currentUsers.getChildren().addAll(getUserPanes());
            feh.ur.save();
        });
        selectView("userSelect");
    }

    private VBox createUserPane(String name) {
        VBox v = new VBox();
            v.setAlignment(javafx.geometry.Pos.CENTER);

            StackPane avatar = new StackPane();

            ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/avatar.png")));
            avatar.getChildren().add(img);
            Button delete = new Button("✖");
            delete.setOnAction(e -> users.remove(name));
            avatar.getChildren().add(delete);
            StackPane.setAlignment(delete, javafx.geometry.Pos.TOP_RIGHT);

            v.getChildren().add(avatar);

            Text username = new Text(name);
            username.setStyle("-fx-font: 20 open-sans;");
            username.setFill(javafx.scene.paint.Color.WHITE);
            v.getChildren().add(username);
            v.setOnMouseClicked(e -> selectUser(name));
            return v;
    }

    private ArrayList<VBox> getUserPanes() {
        if (feh.ur.getUsernameList().size() == 0) {
            Text noUsersText = new Text("There are no users yet!");
            noUsersText.setStyle("-fx-font: 40 open-sans;");
            noUsersText.setFill(javafx.scene.paint.Color.WHITE);
            currentUsers.getChildren().add(noUsersText);
            return new ArrayList<VBox>();
        }
        ArrayList<VBox> userPanes = new ArrayList<VBox>();
        for (String name : feh.ur.getUsernameList()) {
            userPanes.add(createUserPane(name));
        }
        return userPanes;
    }

    private List<StackPane> getItemPanes(List<String> keyList) {
        return keyList.stream().map(key -> itemNodes.get(key)).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public void showUserCreation() {
        selectView("userCreation");
    }

    public void createNewUser() {
        System.out.println("he" + usernameInput.getText());
        if (usernameInput.getText().equals(""))
            return;

        users.add(usernameInput.getText());
        selectView("userSelect");
    }

    public void selectUser(String username) {
        feh.selectUser(username);
        usernameText.setText(username);
        //select main view
        selectView("userPane");
    }

    public void logout() {
        feh.changeUser();
        selectView("userSelect");
    }

    private void selectView(String s) {
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
