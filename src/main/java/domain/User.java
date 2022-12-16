package domain;

import java.util.HashSet;

public class User {
    private String username;
    private HashSet<String> favoriteItems;
    /**
     * Constructor for User class
     * @param username
     * @param favoriteItems
     */
    public User(String username){
        this.username=username;
        this.favoriteItems=new HashSet<String>();
    }

    /**
     * Gets the username of the user
     * @return the String representing the username
     */
    public String getUsername(){
        return username;
    }

    /**
     * Gets the favorite items of the user
     * @return a HashSet containing the favorites of the user represented by their String key
     */
    public HashSet<String> getFavoriteItems(){
        return favoriteItems;
    }

    /**
     * Adds an item to favoriteItems
     * @param itemKey
     */
    public void addFavoriteItem(String itemKey){
        favoriteItems.add(itemKey);
    }

    /**
     * Removes an item from favoriteItems
     * @param itemKey
     */
    public void removeFavoriteItem(String itemKey){
        favoriteItems.remove(itemKey);
    }

    /**
     * Converts favoriteItems to a string
     * @return a String representation of favoriteItems
     */
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

    /**
     * Converts User to a string
     * @return a String representation of User
     */
    public String toString(){
        if(favoriteItems.isEmpty()) return username + ";";
        return username + ";" + favoriteItemsToString() + ";";
    }
}
