package domain;

import java.util.*;

public interface ItemRegistry {
    public void initialize();

    public void addMovie(String title, String[] genre, double rating, int release);

    public void addSeries(String title, String[] genre, double rating, int startYear, int endYear, ArrayList<Integer> seasons);
    
    public HashSet<String> getGenreSet();

    public ArrayList<Item> getSeriesList();

    public ArrayList<Item> getMovieList();


}
