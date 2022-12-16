package domain;

import java.security.MessageDigest;
import java.util.*;
import data.DataAccessImpl;

public class ItemRegistryImpl implements ItemRegistry {
    private HashSet<String> genreSet;
    private ArrayList<String> movieKeyList;
    private ArrayList<String> seriesKeyList;
    private HashMap<String, Item> itemMap;
    private MessageDigest md;
/**
 * Constructor for ItemRegistryImpl
 * @param movieKeyList is an ArrayList containing String keys mapping to Movie objects
 * @param seriesKeyList is an ArrayList containing String keys mapping to Series objects
 * @param itemMap is a HashMap mapping String keys to Item objects
 * @param genreSet is a HashSet containing the genres of all Item objects in itemMap
 */
    public ItemRegistryImpl (){
        movieKeyList = new ArrayList<>();
        seriesKeyList = new ArrayList<>();
        itemMap = new HashMap<>();
        genreSet= new HashSet<>();
        try {
            this.md = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        this.initialize();
    }
/**
 * Initializes by loading data from the database
 * and calling addMovie and addSeries
 */
    private void initialize(){
        DataAccessImpl da = new DataAccessImpl("database");
        List<String> movieData = da.load("movies");
        List<String> seriesData = da.load("series");
 
        for (String s : movieData){
            String[] data = s.split(";");
            String title = data[0];
            int releaseYear = Integer.parseInt(data[1].strip());
            String[] genre = data[2].split(",");
            for (int i = 0; i < genre.length; i++) {
                genre[i] = genre[i].strip();
            }
            double rating = Double.parseDouble(data[3].replace(",", "."));
            addMovie(title, genre, rating, releaseYear);
        }
        for (String s : seriesData){
            String[] data = s.split(";");
            String title = data[0];
            int release = Integer.parseInt(data[1].split("-")[0].strip());
            int endYear = 0;
            if (data[1].split("-").length == 2 && !data[1].split("-")[1].equals(" ")) {
                endYear = Integer.parseInt(data[1].split("-")[1].strip());
            } 
            String[] genre = data[2].split(",");
            for (int i = 0; i < genre.length; i++) {
                genre[i] = genre[i].strip();
            }
            double rating = Double.parseDouble(data[3].replace(",", "."));
            int[] seasons = Arrays.asList(data[4].split(",")).stream().map(e -> e.split("-")[1]).mapToInt(Integer::parseInt).toArray();
           
            addSeries(title, genre, rating, release,endYear, seasons);
        }
    }
/**
 * Adds a Movie object to the registry
 * by adding it to itemMap, and adding its key to movieKeyList
 * @param title is the title of the movie
 * @param genre is an array of Strings representing the genres of the movie
 * @param rating is the rating of the movie as a double
 * @param releaseYear is the release year of the movie as an int
 */
    private void addMovie(String title, String[] genre, double rating, int releaseYear){
        Movie movieToBeAdded = new Movie(title, genre, rating, releaseYear);
        List<String> genreList = Arrays.asList(genre).stream().sorted().toList();
        String key = HexFormat.of().formatHex(this.md.digest((title + genreList + releaseYear).getBytes()));
        itemMap.put(key, movieToBeAdded);
        movieKeyList.add(key);
        for (String genreString : genre){
            genreSet.add(genreString);
        }
    }
/*
 * Adds a Series object to the registry
 * by adding it to itemMap, and adding its key to seriesKeyList
 */

    private void addSeries(String title, String[] genre, double rating, int releaseYear, int endYear, int[] seasons){
        Series seriesToBeAdded = new Series(title, genre, rating, releaseYear, endYear, seasons);
        List<String> genreList = Arrays.asList(genre).stream().sorted().toList();
        String key = HexFormat.of().formatHex( (this.md.digest((title + genreList + releaseYear).getBytes())));
        itemMap.put(key,seriesToBeAdded);
        seriesKeyList.add(key);
        for (String genreString : genre){
            genreSet.add(genreString);
        }
    }

    /**
     * Gets the List of movie keys
     * @return an arrayList of movie keys
     */
    public ArrayList<String> getMovieKeyList(){
        return movieKeyList;
    }

    /**
     * Gets the List of series keys
     * @return an arrayList of series keys
     */
    public ArrayList<String> getSeriesKeyList(){
        return seriesKeyList;
    }

    /**
     * Gets the HashSet of genres
     * @return a Hashset of genres
     */
    public HashSet<String> getGenreSet(){
        return genreSet;
    }

    /**
     * Gets String representation of an item
     * @param itemKey is the key of the item
     * @return the item as a String
     */
    public String getToString(String itemKey){
        return itemMap.get(itemKey).toString();
    }

    /**
     * Gets the title of an item
     * @param itemKey is the key of the item
     * @return A string representing the title of the item
     */
    public String getItemTitle(String itemKey){
        return itemMap.get(itemKey).getTitle();
    }

    /**
     * Gets genre of an item
     * @param itemKey is the key of the item
     * @return String[] containing the genres of the item
     */
    public String[] getItemGenre(String itemKey){
        return itemMap.get(itemKey).getGenre();
    }

    /**
     * Gets the rating of an item
     * @param itemKey is the key of the item
     * @return double representing the rating of the item
     */
    public double getItemRating(String itemKey){
        return itemMap.get(itemKey).getRating();
    }

    /**
     * Gets the release year of an item
     * @param itemKey is the key of the item
     * @return int representing the release year of the item
     */
    public int getItemReleaseYear(String itemKey){
        return itemMap.get(itemKey).getReleaseYear();
    }

    /**
     * Gets the end year of an item
     * @param itemKey is the key of the item
     * @return int representing the end year of the item 
     */
    public int getSeriesEndYear(String itemKey){
        return itemMap.get(itemKey).getEndYear();
    }

    /**
     * Gets the seasons of an item
     * @param itemKey is the key of the item
     * @return int[] representing the seasons of the item
     */
    public int[] getSeriesSeasons(String itemKey){
        return itemMap.get(itemKey).getSeasons();
    }
}
