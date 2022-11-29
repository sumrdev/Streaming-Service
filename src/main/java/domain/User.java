package domain;

import java.util.HashSet;

public class User {
    String username;
    HashSet<String> favoriteItems;

    public User(String username, String password){
        this.username=username;
        this.favoriteItems=new HashSet<String>();
    }

    public String getUsername(){
        return username;
    }
    public HashSet<String> getFavoriteItems(){
        return favoriteItems;
    }
    public void addFavoriteItem(String itemKey){
        favoriteItems.add(itemKey);
    }
    public void removeFavoriteItem(String itemKey){
        favoriteItems.remove(itemKey);
    }
    public String favoriteItemsToString(){
        String favoriteItemsString = "";
        for (String itemKey : favoriteItems) {
            favoriteItemsString += itemKey + ",";
        }
        return favoriteItemsString;
    }

    public String toString(){
        return username + ";" + favoriteItemsToString() + ";";
    }


}
