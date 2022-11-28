package domain;

import java.util.ArrayList;

public class User {
    String username;
    String password;
    ArrayList<Item> favoriteItems;

    public User(String username, String password, String[] favoriteGenre){
        this.username=username;
        this.password=password;
        this.favoriteItems=new ArrayList<Item>();
    }

    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public ArrayList<Item> getFavoriteItems(){
        return favoriteItems;
    }
    public void addFavoriteItem(Item item){
        favoriteItems.add(item);
    }
    public void removeFavoriteItem(Item item){
        favoriteItems.remove(item);
    }
    public String favoriteItemsToString(){
        String favoriteItemsString = "";
        for (Item item : favoriteItems) {
            favoriteItemsString += item.getTitle() + ",";
        }
        return favoriteItemsString;
    }

    public String toString(){
        return username + ";" + password + ";"+ favoriteItemsToString() + ";";
    }


}
