import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import domain.ItemRegistryImpl;
import domain.Movie;
import domain.Item;
class ItemRegistryTest {
    static ItemRegistryImpl itemRegistry;

     @BeforeAll
    static void beforeAll() {
        itemRegistry = new ItemRegistryImpl();
        itemRegistry.initialize();
        System.out.println("Before all test methods");
    }

    @Test
    void justAnExample() {
        ArrayList<Item> movies = itemRegistry.getMovieList();
        Item movie = movies.get(0);
        assertTrue(movie.getTitle().equals("The Godfather"));
        assertTrue(movie.getGenre()[0].equals("Crime"));
        assertTrue(movie.getRating() == 9.2);
    }
}