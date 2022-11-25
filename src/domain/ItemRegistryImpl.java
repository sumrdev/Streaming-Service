package domain;

import java.util.*;

public class ItemRegistryImpl implements ItemRegistry {
    ArrayList<String> movieKeyList;
    ArrayList<String> seriesKeyList;
    HashMap<String, Item> itemMap;
    HashSet<String> genreSet;

    public ItemRegistryImpl (){
        movieKeyList = new ArrayList<>();
        seriesKeyList = new ArrayList<>();
        itemMap = new HashMap<>();
        genreSet= new HashSet<>();
    }
    public void initialize(){

    }
    public void addMovie(String title, String[] genre, double rating, int release){
        Movie movieToBeAdded = new Movie(title, genre, rating, release);
        itemMap.put(title, movieToBeAdded);
        movieKeyList.add(title);
        for (String genreString : genre){
            genreSet.add(genreString);
        }
    }
    public void addSeries(String title, String[] genre, double rating, int startYear, int endYear, ArrayList<Integer> seasons){
        Series seriesToBeAdded = new Series(title, genre, rating, startYear, endYear, seasons);
        itemMap.put(title,seriesToBeAdded);
        seriesKeyList.add(title);
        for (String genreString : genre){
            genreSet.add(genreString);
        }
    }
    public ArrayList<String> getMovieKeyList(){
        return movieKeyList;
    }
    public ArrayList<String> getSeriesKeyList(){
        return seriesKeyList;
    }
    public HashSet<String> getGenreSet(){
        return genreSet;
    }
    public String getToString(String itemKey){
        return itemMap.get(itemKey).toString();
    }
    public String getItemTitle(String itemKey){
        return itemMap.get(itemKey).getTitle();
    }
    public String[] getItemGenre(String itemKey){
        return itemMap.get(itemKey).getGenre();
    }
    public double getItemRating(String itemKey){
        return itemMap.get(itemKey).getRating();
    }
    public int getMovieRelease(String itemKey){
        return itemMap.get(itemKey).getRelease();
    }
    public int getSeriesStartYear(String itemKey){
        return itemMap.get(itemKey).getStartYear();
    }
    public int getSeriesEndYear(String itemKey){
        return itemMap.get(itemKey).getEndYear();
    }
    public ArrayList<Integer> getSeriesSeasons(String itemKey){
        return itemMap.get(itemKey).getSeasons();
    }



}
