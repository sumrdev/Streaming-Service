import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import domain.ItemRegistryImpl;

class MovieTest {
    static ItemRegistryImpl itemRegistry;

    @BeforeAll
    static void beforeAll() {
        itemRegistry = new ItemRegistryImpl();
        itemRegistry.initialize();
    }

    @Test
    void toStringTest() {
        ArrayList<String> movies = itemRegistry.getMovieKeyList();
        String movieKey = movies.get(0);
        assertEquals(itemRegistry.getItemString(movieKey).toString(), "The Godfather; 1972;Crime,Drama;9.2;");
    }

    @Test
    void getMovieTitleTest() {
        ArrayList<String> movies = itemRegistry.getMovieKeyList();
        String movieKey = movies.get(0);
        assertEquals(itemRegistry.getItemTitle(movieKey), "The Godfather");
    }

    @Test
    void getMovieGenreTest() {
        ArrayList<String> movies = itemRegistry.getMovieKeyList();
        String movieKey = movies.get(0);
        assertEquals(itemRegistry.getItemGenre(movieKey)[0], "Crime");
    }

    @Test
    void getMovieRatingTest() {
        ArrayList<String> movies = itemRegistry.getMovieKeyList();
        String movieKey = movies.get(0);
        assertEquals(itemRegistry.getItemRating(movieKey), 9.2);
    }

    @Test
    void getMovieReleaseTest() {
        ArrayList<String> movies = itemRegistry.getMovieKeyList();
        String movieKey = movies.get(0);
        assertEquals(itemRegistry.getItemRelease(movieKey), 1972);
    }
}