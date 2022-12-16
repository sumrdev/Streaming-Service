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
     * Gets the title of an item
     * @return a String representing the title of an item 
     */
    public String getTitle(){
        return title;
    }

    /**
     * Gets the genre of an item
     * @return a String[] containing the genres of an item
     */
    public String[] getGenre(){
        return genre;
    }

    /**
     * Gets the rating of an item
     * @return a double representing the rating of an item
     */
    public double getRating(){
        return rating;  
    }
    
    /**
     * Gets the release year of an item
     * @return an int representing the release year of an item
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
 * @return a String representation of genre
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
 * @return a String representation of item
 */
    public String toString(){
        return title + "; " + releaseYear + ";" + genreToString() + ";" + rating + ";";
    }
}
