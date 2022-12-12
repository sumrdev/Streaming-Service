package domain;

import java.security.MessageDigest;
import java.util.*;
import data.DataAccessImpl;

public class ItemRegistryImpl implements ItemRegistry {
    HashSet<String> genreSet;
    ArrayList<String> movieKeyList;
    ArrayList<String> seriesKeyList;
    HashMap<String, Item> itemMap;
    MessageDigest md;
    public ItemRegistryImpl (){
        movieKeyList = new ArrayList<>();
        seriesKeyList = new ArrayList<>();
        itemMap = new HashMap<>();
        genreSet= new HashSet<>();
        try {
            this.md = MessageDigest.getInstance("SHA-1");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
    public void initialize(){
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
            int startYear = Integer.parseInt(data[1].split("-")[0].strip());
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
           
            addSeries(title, genre, rating, startYear,endYear, seasons);
        }
    }
    public void addMovie(String title, String[] genre, double rating, int release){
        Movie movieToBeAdded = new Movie(title, genre, rating, release);
        String key = HexFormat.of().formatHex(this.md.digest((title + genre + rating + release).getBytes()));
        itemMap.put(key, movieToBeAdded);
        movieKeyList.add(key);
        for (String genreString : genre){
            genreSet.add(genreString);
        }
    }
    public void addSeries(String title, String[] genre, double rating, int startYear, int endYear, int[] seasons){
        Series seriesToBeAdded = new Series(title, genre, rating, startYear, endYear, seasons);
        String key = HexFormat.of().formatHex( (this.md.digest((title + genre + rating + startYear + endYear + seasons).getBytes())));
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
    public int getItemRelease(String itemKey){
        return itemMap.get(itemKey).getRelease();
    }
    public int getSeriesStartYear(String itemKey){
        return itemMap.get(itemKey).getStartYear();
    }
    public int getSeriesEndYear(String itemKey){
        return itemMap.get(itemKey).getEndYear();
    }
    public int[] getSeriesSeasons(String itemKey){
        return itemMap.get(itemKey).getSeasons();
    }

    public String getItemString(String itemKey){
        return itemMap.get(itemKey).toString();
    }

}
