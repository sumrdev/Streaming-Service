package domain;

import java.util.*;
import data.DataAccessImpl;

public class ItemRegistryImpl implements ItemRegistry {
    ArrayList<Item> movieList;
    ArrayList<Item> seriesList;
    HashSet<String> genreSet;

    public ItemRegistryImpl (){
        movieList = new ArrayList<>();
        seriesList = new ArrayList<>();
        genreSet = new HashSet<>();
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
            ArrayList<Integer> seasons = new ArrayList<>();
            for (String string : Arrays.asList(data[4].split(","))){
                seasons.add(Integer.parseInt(string.split("-")[1]));
            }                
            addSeries(title, genre, rating, startYear,endYear, seasons);
        }
    }
    public void addMovie(String title, String[] genres, double rating, int release){
        Movie movieToBeAdded = new Movie(title, genres, rating, release);
        movieList.add(movieToBeAdded);
        for (String genreString : genres){
            genreSet.add(genreString);
        }
    }
    public void addSeries(String title, String[] genres, double rating, int startYear, int endYear, ArrayList<Integer> seasons){
        Series seriesToBeAdded = new Series(title, genres, rating, startYear, endYear, seasons);
        seriesList.add(seriesToBeAdded);
        for (String genreString : genres){
            genreSet.add(genreString);
        }
    }
    public HashSet<String> getGenreSet(){
        return genreSet;
    }
    
    public ArrayList<Item> getSeriesList() {
        return seriesList;
    }

    public ArrayList<Item> getMovieList() {
        return movieList;
    }



}
