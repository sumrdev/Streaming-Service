package domain;

import java.util.*;

public abstract class Item {
    String title;
    String[] genre;
    double rating;
    
    public Item(String title, String[] genre, double rating){
        this.title=title;
        this.genre=genre;
        this.rating=rating;
    }
    public String getTitle(){
        return title;
    }
    public String[] getGenre(){
        return genre;
    }
    public double getRating(){
        return rating;  
    }
    public int getRelease(){
        //should not happen
        return 1;
    }
    public int getStartYear(){
        //should not happen
        return 1;
    }
    public int getEndYear(){
        //should not happen
        return 1;
    }
    public ArrayList<Integer> getSeasons(){
        //should not happen
        return null;
    }

}
