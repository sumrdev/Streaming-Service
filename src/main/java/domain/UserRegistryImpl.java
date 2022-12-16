package domain;

import java.util.*;
import data.DataAccessImpl;

public class UserRegistryImpl implements UserRegistry {
    /**
     * @param userMap is a HashMap that maps usernames to User objects
     * @param userNameList is an ArrayList that contains all usernames
     * @param currentUser is the username of the currently selected user
     */
    private HashMap<String, User> userMap;
    private ArrayList<String> userNameList;
    private String currentUser = null;

    /**
     * Constructor for UserRegistryImpl class
     */
    public UserRegistryImpl(){
        userMap = new HashMap<>();
        userNameList = new ArrayList<>();
        this.initialize();
    }

    /**
     * Initializes the userMap and userNameList
     * by loading the data from the database
     * and adding the users to the userMap and userNameList
     */
    private void initialize() {
        DataAccessImpl da = new DataAccessImpl("database");
        List<String> userData = da.load("users");
        for (String s : userData){
            String[] data = s.split(";");
            if(data.length == 1) addUser(data[0]);
            else if (data.length == 2) addUser(data[0], data[1].split(","));
        }
    }

    /**
     * Adds a user to the userMap and userNameList
     * @param username is the username of the user to be added
     * @throws IllegalArgumentException if the user already exists
     * Usually called when adding a new user
     */
    @Override
    public void addUser(String username) throws IllegalArgumentException {
        if (userNameList.contains(username)){
            throw new IllegalArgumentException("User already exists");
        } else {
            userMap.put(username, new User(username));
            userNameList.add(username);
        }
    }

    /**
     * Adds a user to the userMap and userNameList
     * @param username is the username of the user to be added
     * @param favorites is an array of favorite items of the user to be added
     * @throws IllegalArgumentException if the user already exists
     * Usually called when initializing the userMap and userNameList
     */
    private void addUser(String username, String[] favorites) throws IllegalArgumentException {
        if (userNameList.contains(username)){
            throw new IllegalArgumentException("User already exists");
        } else{
            User user = new User(username);
            for (String favorite : favorites){
                user.addFavoriteItem(favorite);
            }
            userMap.put(username, user);
            userNameList.add(username);
        }
    }

    /**
     * Removes a user from the userMap and userNameList
     * @param username is the username of the user to be removed
     */
    @Override
    public void removeUser(String username) {
        if (userMap.containsKey(username)){
            userMap.remove(username);
            userNameList.remove(username);
        }
        
    }

    /**
     * Saves the userMap and userNameList to the database
     */
    @Override
    public void save() {
        DataAccessImpl da = new DataAccessImpl("database");
        ArrayList<String> listToBeSaved = new ArrayList<>();
        for (User user : userMap.values()){
            listToBeSaved.add(user.toString());
        }
        da.save("users", listToBeSaved);
    }
    
    /**
     * Gets the username of a user
     * @param userKey is the username of the user
     * @return the username of the user
     */
    public String getUsername(String userKey){
        return userMap.get(userKey).getUsername();
    }

    /**
     * Gets the favorite items of a user
     * @param userKey is the username of the user
     * @return a HashSet of the favorite items of the user
     */
    public HashSet<String> getFavoriteItems (String userKey){
        return userMap.get(userKey).getFavoriteItems();
    }

    /**
     * Adds a favorite item to a user
     * @param userKey is the username of the user
     */
    public void addFavoriteItem (String userKey, String itemKey){
        userMap.get(userKey).addFavoriteItem(itemKey);
    }
    /**
     * Removes a favorite item from a user
     * @param userKey is the username of the user
     */
    public void removeFavoriteItem (String userKey, String itemKey){
        userMap.get(userKey).removeFavoriteItem(itemKey);
    }

    /**
     * Gets the list of usernames
     * @return an ArrayList of usernames
     */
    public ArrayList<String> getUsernameList(){
        return userNameList;
    }

    /**
     * Selects a user
     * @param userKey is the username of the user to be selected
     */
    public void selectUser(String userKey){
        currentUser = userKey;
    }

    /**
     * Gets the username of the currently selected user
     * @return the username of the currently selected user
     */
    public String getSelectedUser(){
        return currentUser;
    }
}
