package domain;

public interface ItemRegistry {
    public void initialize();

    public void addMovie(Item item);

    public void addSeries(String title);
}
