package domain;

import java.util.*;
import data.DataAccessImpl;

public class userRegistryImpl implements userRegistry {
    ArrayList<User> userList;
    ArrayList<String> userNameList;
    public userRegistryImpl(){
        userList = new ArrayList<>();
        userNameList = new ArrayList<>();
    }

    @Override
    public void initialize() {
        DataAccessImpl da = new DataAccessImpl("database");
        List<String> userData = da.load("users");
        for (String s : userData){
            String[] data = s.split(";");
            ArrayList<String> favorites = new ArrayList<>();
            for (String dataString : data){
                favorites.add(dataString);
            }
            favorites.remove(0);
            addUser(data[0], favorites);
        }
    }

    @Override
    public void addUser(String username) {
        if (userNameList.contains(username)){

        } else{
        userList.add(new User(username));
        userNameList.add(username);
            }
    }

    @Override
    public void addUser(String username, ArrayList<String> favorites) {
        if (userNameList.contains(username)){

        } else{
            User user = new User(username);
            for (String favorite : favorites){
                user.addFavoriteItem(favorite);
            }
            userList.add(user);
            userNameList.add(username);
        }
    }
        

    @Override
    public void removeUser(String username) {
        for (User user : userList){
            if (user.getUsername() == username){
                userList.remove(user);
                userNameList.remove(username);
            }
        }
        
    }

    

    @Override
    public void save() {
        DataAccessImpl da = new DataAccessImpl("database");
        ArrayList<String> listToBeSaved = new ArrayList<>();
        for (User user : userList){
            String saveString = user.getUsername()+"; ";
            for (String favoriteKey : user.getFavoriteItems()){
                saveString = saveString + favoriteKey + "; ";
            }
            listToBeSaved.add(saveString);
        }
        da.save("users", listToBeSaved);

    }

}
