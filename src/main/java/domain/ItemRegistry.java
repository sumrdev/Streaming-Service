package domain;

import java.util.*;

public interface ItemRegistry {
    public void initialize();

    public void addMovie(String title, String[] genre, double rating, int release);

    public void addSeries(String title, String[] genre, double rating, int startYear, int endYear, ArrayList<Integer> seasons);

    public ArrayList<String> getMovieKeyList();

    public ArrayList<String> getSeriesKeyList();

    public HashSet<String> getGenreSet();

    public String getToString(String itemKey);

    public String getItemTitle(String itemKey);

    public String[] getItemGenre(String itemKey);

    public double getItemRating(String itemKey);

    public int getMovieRelease(String itemKey);

    public int getSeriesStartYear(String itemKey);

    public int getSeriesEndYear(String itemKey);

    public ArrayList<Integer> getSeriesSeasons(String itemKey);


}
