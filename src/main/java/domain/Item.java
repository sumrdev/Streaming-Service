package domain;


public abstract class Item {
    protected String title;
    protected String[] genre;
    protected double rating;
    protected int releaseYear;
   /**
    * Constructor for the Item class.
    *@param title
    *@param genre
    *@param rating
    *@param releaseYear
    */
    public Item(String title, String[] genre, double rating, int releaseYear) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.releaseYear = releaseYear;
    }

    /**
     * Returns title
     * @return title
     */
    public String getTitle(){
        return title;
    }

    /**
     * Returns genre
     * @return genre
     */
    public String[] getGenre(){
        return genre;
    }

    /**
     * Returns rating
     * @return rating
     */
    public double getRating(){
        return rating;  
    }
    
    /**
     * Returns releaseYear
     * @return releaseYear
     */
    public int getReleaseYear(){
        return releaseYear;
    }

    public int getEndYear(){
        //should not happen
        return 1;
    }

    public int[] getSeasons(){
        //should not happen
        return null;
    }
/**
 * Converts genre to a String
 * @return String representation of genre
 */
    public String genreToString(){
        String genreString = "";
        for (int i = 0; i < genre.length; i++) {
            if(i==genre.length-1) genreString += genre[i];
            else genreString += genre[i] + ",";
        }
        return genreString;
    }
/**
 * Converts Item to a String
 * @return String representation of item
 */
    public String toString(){
        return title + "; " + releaseYear + ";" + genreToString() + ";" + rating + ";";
    }
}
