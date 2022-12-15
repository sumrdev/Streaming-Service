package domain;

import java.util.*;

public interface ItemRegistry {
    public void initialize();
    
    public HashSet<String> getGenreSet();

    public ArrayList<String> getMovieKeyList();

    public ArrayList<String> getSeriesKeyList();

    public String getToString(String itemKey);

    public String getItemTitle(String itemKey);

    public String[] getItemGenre(String itemKey);

    public double getItemRating(String itemKey);

    public int getItemReleaseYear(String itemKey);

    public int getSeriesEndYear(String itemKey);

    public int[] getSeriesSeasons(String itemKey);

}
