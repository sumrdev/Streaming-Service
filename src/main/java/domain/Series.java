package domain;
public class Series extends Item {
    private int endYear;
    private int[] seasons;
    /**
     * Constructor for Series class
     * @param title
     * @param genre
     * @param rating
     * @param release
     * @param endYear
     * @param seasons
     */
    public Series(String title, String[] genre, double rating, int releaseYear, int endYear, int[] seasons){
        super(title, genre, rating, releaseYear);
        this.endYear=endYear;
        this.seasons=seasons;
    }

    /**
     * Gets the end year of a series
     * @return an int representing the end year of the series
     */
    public int getEndYear(){
        return endYear;
    }

    /**
     * Gets the seasons of a series
     * @return returns an int[] representing seasons with episodes
     */
    public int[] getSeasons(){
        return seasons;
    }

    /**
     * Converts seasons to a string
     * @return String representation of seasons
     */
    public String seasonsToString(){
        String seasonsString = "";
        for (int i = 0; i < genre.length-1; i++) {
            if(i==genre.length-2) seasonsString += (i+1) + "-" +seasons[i];
            else seasonsString += (i+1) + "-" +  seasons[i] + ",";
        }
        return seasonsString;
    }
    /**
     * Converts series to a string
     * @return String representation of series
     */
    @Override
    public String toString(){
        return title + ";" + releaseYear + "-" + endYear + ";" + genreToString() + ";"+ String.valueOf(rating).replace(".", ",") + ";" + seasonsToString() + ";";
    }
}

