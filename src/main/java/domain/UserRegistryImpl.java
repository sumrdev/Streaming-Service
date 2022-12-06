package domain;

import java.util.*;
import data.DataAccessImpl;

public class UserRegistryImpl implements UserRegistry {
    public HashMap<String, User> userMap;
    ArrayList<String> userNameList;
    String currentUser = null;
    public UserRegistryImpl(){
        userMap = new HashMap<>();
        userNameList = new ArrayList<>();
    }

    @Override
    public void initialize() {
        DataAccessImpl da = new DataAccessImpl("database");
        List<String> userData = da.load("users");
        for (String s : userData){
            String[] data = s.split(";");
            if(data.length == 1) addUser(data[0]);
            else if (data.length == 2)
            addUser(data[0], data[1].split(","));
        }
    }

    @Override
    public void addUser(String username) {
        if (userNameList.contains(username)){
            throw new IllegalArgumentException("User already exists");
        } else {
            userMap.put(username, new User(username));
            userNameList.add(username);
        }
    }

    @Override
    public void addUser(String username, String[] favorites) {
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

    @Override
    public void removeUser(String username) {
        if (userMap.containsKey(username)){
            userMap.remove(username);
            userNameList.remove(username);
        }
        
    }

    @Override
    public void save() {
        DataAccessImpl da = new DataAccessImpl("database");
        ArrayList<String> listToBeSaved = new ArrayList<>();
        for (User user : userMap.values()){
            listToBeSaved.add(user.toString());
        }
        da.save("users", listToBeSaved);
    }

    public String getUsername(String userKey){
        return userMap.get(userKey).getUsername();
    }

    public HashSet<String> getFavoriteItems (String userKey){
        return userMap.get(userKey).getFavoriteItems();
    }

    public void addFavoriteItem (String userKey, String itemKey){
        userMap.get(userKey).addFavoriteItem(itemKey);
    }

    public void removeFavoriteItem (String userKey, String itemKey){
        userMap.get(userKey).removeFavoriteItem(itemKey);
    }

    public ArrayList<String> getUsernameList(){
        return userNameList;
    }

    public void selectUser(String userKey){
        currentUser = userKey;
    }

}
