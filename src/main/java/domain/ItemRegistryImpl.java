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
/*
 * Adds a Movie object to the registry
 * by adding it to itemMap, and adding its key to movieKeyList
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
     * Returns the List of movie keys
     * @return the movieKeyList
     */
    public ArrayList<String> getMovieKeyList(){
        return movieKeyList;
    }

    /**
     * Returns the List of series keys
     * @return the seriesKeyList
     */
    public ArrayList<String> getSeriesKeyList(){
        return seriesKeyList;
    }

    /**
     * Returns the HashMap of items
     * @return the set of genres
     */
    public HashSet<String> getGenreSet(){
        return genreSet;
    }

    /**
     * @param itemKey is the key of the item
     * @return the item as a String
     */
    public String getToString(String itemKey){
        return itemMap.get(itemKey).toString();
    }

    /**
     * @param itemKey is the key of the item
     * @return the title of the item
     */
    public String getItemTitle(String itemKey){
        return itemMap.get(itemKey).getTitle();
    }

    /**
     * @param itemKey is the key of the item
     * @return the genre of the item
     */
    public String[] getItemGenre(String itemKey){
        return itemMap.get(itemKey).getGenre();
    }

    /**
     * @param itemKey is the key of the item
     * @return the rating of the item
     */
    public double getItemRating(String itemKey){
        return itemMap.get(itemKey).getRating();
    }

    /**
     * @param itemKey is the key of the item
     * @return the release year of the item
     */
    public int getItemReleaseYear(String itemKey){
        return itemMap.get(itemKey).getReleaseYear();
    }

    /**
     * @param itemKey is the key of the item
     * @return the end year of the item 
     */
    public int getSeriesEndYear(String itemKey){
        return itemMap.get(itemKey).getEndYear();
    }

    /**
     * @param itemKey is the key of the item
     * @return the seasons of the item as an array
     */
    public int[] getSeriesSeasons(String itemKey){
        return itemMap.get(itemKey).getSeasons();
    }
}
