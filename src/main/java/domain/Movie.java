package domain;

public class Movie extends Item {
    int release;
    public Movie(String title, String[] genre, double rating, int release){
        super(title, genre, rating, release);
        this.release=release;
    }
    public int getRelease(){
        return release;
    }
    public String toString(){
        return title + ", " + release;
    }
}
