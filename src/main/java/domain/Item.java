package domain;


public abstract class Item {
    protected String title;
    protected String[] genre;
    protected double rating;
    protected int releaseYear;
    
    public Item(String title, String[] genre, double rating, int releaseYear) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.releaseYear = releaseYear;
    }

    public String getTitle(){
        return title;
    }
    public String[] getGenre(){
        return genre;
    }
    public double getRating(){
        return rating;  
    }
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

    public String genreToString(){
        String genreString = "";
        for (int i = 0; i < genre.length; i++) {
            if(i==genre.length-1) genreString += genre[i];
            else genreString += genre[i] + ",";
        }
        return genreString;
    }

    public String toString(){
        return title + "; " + releaseYear + ";" + genreToString() + ";" + rating + ";";
    }
}
