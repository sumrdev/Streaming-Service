package presentation;

import java.util.*;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class UserMenuController {
    /**
     * The root pane of the user menu.
     */
    @FXML
    private StackPane MainStackpane;

    /**
     * Overlays for the user menu. Switches between userSelect, 
     *  userCreation, and userPane depending on the state.
     */
    @FXML
    private VBox userPane;
    @FXML
    private VBox userSelect;
    @FXML
    private VBox userCreation;

    /**
    * Used when logged in (userPane)
    */ 
    @FXML
    private FlowPane favoritePane;
    @FXML
    private Text usernameText;

    /**
    * Used when choosing user (userSelect)
    */ 
    @FXML
    private FlowPane currentUsers;

    /**
    * Used when creating user (userCreation)
    */ 
    @FXML
    private TextField usernameInput;

    private DomainAccess da;
    /**
     * ObservableList of users to be displayed in the userSelect pane. Automatically updates userRegistry.
     */
    private ObservableList<String> users = FXCollections.observableArrayList();
    private ListChangeListener<String> userFavoriteListener;

    /**
     * Initializes the user menu and users ObservableList. 
     * Sets up the favorite listener to listen for new favoriteItems.
     * Also sets up the usernameInput to only accept alphanumeric characters.
     * @param da the DomainAccess object to use
     */
    public void initialize(DomainAccess da) {
        this.da = da;
        userFavoriteListener = (ListChangeListener<String>) c -> {
            favoritePane.getChildren().clear();
            favoritePane.getChildren().addAll(da.getItemPanes(da.getFavoriteItems()));
        };

        usernameInput.textProperty().addListener((obs, oldText, newText) -> {
            boolean acceptable = newText.matches("^[a-zA-Z0-9 _]*$");
            if (!acceptable) {
                usernameInput.setText(oldText);
            }
        });
        
        users.addAll(da.getUsernameList());
        currentUsers.getChildren().clear();
        currentUsers.getChildren().addAll(getUserPanes());
        users.addListener((ListChangeListener<String>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (String user : c.getAddedSubList()) {
                        try {
                            da.addUser(user);
                        } catch (Exception e) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Username cannot be empty");
                            alert.setContentText("Please enter a username");
                            alert.showAndWait();
                            return;
                        }
                    }
                }
                if (c.wasRemoved()) {
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

    /** 
     * Unloads itemPanes and listeners
    */
    public void unload() {
        da.getFavoriteItems().removeListener(userFavoriteListener);
        favoritePane.getChildren().clear();
        MainStackpane.getChildren().remove(da.getPopup());
    }

    /**
     * Loads itemPanes and listeners.
     */
    public void load() {
        unload();
        MainStackpane.getChildren().add(da.getPopup());
        favoritePane.getChildren().addAll(da.getItemPanes(da.getFavoriteItems()));
        da.getFavoriteItems().addListener(userFavoriteListener);
    }

    /**
     * Creates a VBox with the userName and an image.
     * Sets onAction to select the user.
     * @param name the name of the user
     * @return the created VBox
     */
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

    /**
     * Creates and gets all userPanes for the currentUsers.
     * If there are no users, it displays a message.
     * @return an ArrayList of userPanes
     */
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

    /**
     * Shows the user creation menu.
     */
    public void showUserCreation() {
        selectView("userCreation");
    }

    /**
     * Creates a new user with the username in the usernameInput.
     */
    public void createNewUser() {
        //alert if username is empty
        if (usernameInput.getText().equals("")){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Username cannot be empty");
            alert.setContentText("Please enter a username");
            alert.showAndWait();
            return;
        }
        users.add(usernameInput.getText());
        selectView("userSelect");
    }

    /**
     * Selects the user with the given username.
     * @param username the username of the user
     */
    public void selectUser(String username) {
        da.selectUser(username);
        usernameText.setText(username);
        //select main view
        selectView("userPane");
        MainWindowController mwc = (MainWindowController) MainStackpane.getScene().getUserData();
        mwc.navigateHome();
    }

    /**
     * Logs out the current user.
     */
    public void logout() {
        da.changeUser();
        selectView("userSelect");
    }

    /**
     * The view specified by the parameter string.
     * @param s the view to be selected, either "userPane", "userSelect" or "userCreation"
     */
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
