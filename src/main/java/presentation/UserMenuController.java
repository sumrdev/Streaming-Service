package presentation;

import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
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

    private DomainAccess da;
    private ObservableList<String> users = FXCollections.observableArrayList();
    private HashMap<String, StackPane> itemNodes = new HashMap<>(); 

    public void initialize(DomainAccess da, MainWindow mw) {
        this.da = da;
        try {
            FXMLLoader popupLoader = new FXMLLoader(getClass().getClassLoader().getResource("itemPopup.fxml"));
            Parent popup = popupLoader.load();
            PopupController puc = popupLoader.getController();
            puc.initialize(mw, da);
            MainStackpane.getChildren().add(popup);
            this.itemNodes = da.createItemPanes(puc);
        } catch (ClassCastException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        da.favoriteItems.addListener((ListChangeListener<String>) c -> {
            favoritePane.getChildren().clear();
            favoritePane.getChildren().addAll(getItemPanes(da.favoriteItems));
        });
        
        users.addAll(da.getUsernameList());
        currentUsers.getChildren().clear();
        currentUsers.getChildren().addAll(getUserPanes());
        users.addListener((ListChangeListener<String>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    System.out.println("Added: " + c.getAddedSubList());
                    for (String user : c.getAddedSubList()) {
                        try {
                            da.addUser(user);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                if (c.wasRemoved()) {
                    System.out.println("Removed: " + c.getRemoved());
                    for (String user : c.getRemoved()) {
                        da.removeUser(user);
                    }
                }
            }
            currentUsers.getChildren().clear();
            currentUsers.getChildren().addAll(getUserPanes());
            da.save();
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
            delete.setTextOverrun(OverrunStyle.CLIP);
            delete.setFont(javafx.scene.text.Font.font("open-sans", 28));
            delete.setTextFill(javafx.scene.paint.Color.DARKRED);
            delete.setPadding(new javafx.geometry.Insets(-5, 0, -5, -5));
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
        if (da.getUsernameList().size() == 0) {
            Text noUsersText = new Text("There are no users yet!");
            noUsersText.setStyle("-fx-font: 40 open-sans;");
            noUsersText.setFill(javafx.scene.paint.Color.WHITE);
            currentUsers.getChildren().add(noUsersText);
            return new ArrayList<VBox>();
        }
        ArrayList<VBox> userPanes = new ArrayList<VBox>();
        for (String name : da.getUsernameList()) {
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
        da.selectUser(username);
        usernameText.setText(username);
        //select main view
        selectView("userPane");
    }

    public void logout() {
        da.changeUser();
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
