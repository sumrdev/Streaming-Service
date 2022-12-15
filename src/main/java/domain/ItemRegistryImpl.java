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
    private void initialize(){
        DataAccessImpl da = new DataAccessImpl("database");
        List<String> movieData = da.load("movies");
        List<String> seriesData = da.load("series");
 
        for (String s : movieData){
            String[] data = s.split(";");
            String title = data[0];
            int release = Integer.parseInt(data[1].strip());
            String[] genre = data[2].split(",");
            for (int i = 0; i < genre.length; i++) {
                genre[i] = genre[i].strip();
            }
            double rating = Double.parseDouble(data[3].replace(",", "."));
            addMovie(title, genre, rating, release);
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
    private void addMovie(String title, String[] genre, double rating, int release){
        Movie movieToBeAdded = new Movie(title, genre, rating, release);
        List<String> genreList = Arrays.asList(genre).stream().sorted().toList();
        String key = HexFormat.of().formatHex(this.md.digest((title + genreList + release).getBytes()));
        itemMap.put(key, movieToBeAdded);
        movieKeyList.add(key);
        for (String genreString : genre){
            genreSet.add(genreString);
        }
    }
    private void addSeries(String title, String[] genre, double rating, int release, int endYear, int[] seasons){
        Series seriesToBeAdded = new Series(title, genre, rating, release, endYear, seasons);
        List<String> genreList = Arrays.asList(genre).stream().sorted().toList();
        String key = HexFormat.of().formatHex( (this.md.digest((title + genreList + release).getBytes())));
        itemMap.put(key,seriesToBeAdded);
        seriesKeyList.add(key);
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
    public int getItemReleaseYear(String itemKey){
        return itemMap.get(itemKey).getReleaseYear();
    }
    public int getSeriesEndYear(String itemKey){
        return itemMap.get(itemKey).getEndYear();
    }
    public int[] getSeriesSeasons(String itemKey){
        return itemMap.get(itemKey).getSeasons();
    }
}
