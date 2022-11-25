package domain;
import java.util.*;
public class Series extends Item {
    int startYear;
    int endYear;
    ArrayList<Integer> seasons;
    public Series(String title, String[] genre, double rating, int startYear, int endYear, ArrayList<Integer> seasons){
        super(title, genre, rating);
        this.startYear=startYear;
        this.endYear=endYear;
        this.seasons=seasons;
    }
    public int getStartYear(){
        return startYear;
    }
    public int getEndYear(){
        return endYear;
    }
    public ArrayList<Integer> getSeasons(){
        return seasons;
    }
    public String toString(){
        return title + ", " + startYear + "-" + endYear + ", " + (seasons.size()+1) + " Seasons";
        }
    }

