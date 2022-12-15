package domain;

import java.util.HashSet;

public class User {
    private String username;
    private HashSet<String> favoriteItems;

    public User(String username){
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
    private String favoriteItemsToString(){
        String favoriteItemsString = "";
        int len = favoriteItems.size();
        int i = 0;
        for (String itemKey : favoriteItems) {
            i++;
            if (i == len) {
                favoriteItemsString += itemKey;
            } else
            favoriteItemsString += itemKey + ",";
        }
        return favoriteItemsString;
    }

    public String toString(){
        if(favoriteItems.isEmpty()) return username + ";";
        return username + ";" + favoriteItemsToString() + ";";
    }


}
