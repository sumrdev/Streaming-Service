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
     * Returns endYear
     * @return endYear
     */
    public int getEndYear(){
        return endYear;
    }

    /**
     * Returns seasons
     * @return seasons
     */
    public int[] getSeasons(){
        return seasons;
    }

    /**
     * Converts seasons to a string
     * @return seasonsString
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
     * @return seriesString
     */
    @Override
    public String toString(){
        return title + ";" + releaseYear + "-" + endYear + ";" + genreToString() + ";"+ String.valueOf(rating).replace(".", ",") + ";" + seasonsToString() + ";";
    }
}

