package domain;
public class Series extends Item {
    private int endYear;
    private int[] seasons;
    
    public Series(String title, String[] genre, double rating, int release, int endYear, int[] seasons){
        super(title, genre, rating, release);
        this.endYear=endYear;
        this.seasons=seasons;
    }

    public int getEndYear(){
        return endYear;
    }

    public int[] getSeasons(){
        return seasons;
    }

    public String seasonsToString(){
        String seasonsString = "";
        for (int i = 0; i < genre.length-1; i++) {
            if(i==genre.length-2) seasonsString += (i+1) + "-" +seasons[i];
            else seasonsString += (i+1) + "-" +  seasons[i] + ",";
        }
        return seasonsString;
    }

    @Override
    public String toString(){
        return title + ";" + releaseYear + "-" + endYear + ";" + genreToString() + ";"+ String.valueOf(rating).replace(".", ",") + ";" + seasonsToString() + ";";
    }
}

