package domain;

import java.util.*;
import data.DataAccessImpl;

public class userRegistryImpl implements userRegistry {
    HashMap<String, User> userMap;
    ArrayList<String> userNameList;
    public userRegistryImpl(){
        userMap = new HashMap<>();
        userNameList = new ArrayList<>();
    }

    @Override
    public void initialize() {
        DataAccessImpl da = new DataAccessImpl("database");
        List<String> userData = da.load("users");
        for (String s : userData){
            String[] data = s.split(";");
            String[] favorites = data[1].split(",");
            addUser(data[0], favorites);
        }
    }

    @Override
    public void addUser(String username) {
        if (userNameList.contains(username)){

        } else{
        userMap.put(username, new User(username));
        userNameList.add(username);
            }
    }

    @Override
    public void addUser(String username, String[] favorites) {
        if (userNameList.contains(username)){

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
        }
        
    }

    

    @Override
    public void save() {
        DataAccessImpl da = new DataAccessImpl("database");
        ArrayList<String> listToBeSaved = new ArrayList<>();
        for (User user : userMap.values()){
            String saveString = user.getUsername()+"; ";
            for (String favoriteKey : user.getFavoriteItems()){
                saveString = saveString + favoriteKey + ", ";
            }
            listToBeSaved.add(saveString);
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



}
