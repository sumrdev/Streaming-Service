import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import domain.ItemRegistryImpl;

class ItemRegistryTest {
    static ItemRegistryImpl itemRegistry;

    @BeforeAll
    static void beforeAll() {
        itemRegistry = new ItemRegistryImpl();
    }

    @Test
    void ItemRegistryMovieTest() {
        ArrayList<String> movies = itemRegistry.getMovieKeyList();
        String movieKey = movies.get(0);
        assertEquals(itemRegistry.getItemTitle(movieKey), "The Godfather");
        assertEquals(itemRegistry.getItemGenre(movieKey)[0], "Crime");
        assertEquals(itemRegistry.getItemRating(movieKey), 9.2);
    }

    @Test
    void ItemRegistryStillRunningSeriesTest() {
        ArrayList<String> series = itemRegistry.getSeriesKeyList();
        String serieList = series.get(0);
        assertEquals(itemRegistry.getItemTitle(serieList), "Twin Peaks");
        assertEquals(itemRegistry.getItemReleaseYear(serieList), 1990);
        assertEquals(itemRegistry.getSeriesEndYear(serieList), 1991);
        assertEquals(itemRegistry.getItemGenre(serieList)[0], "Crime");
        assertEquals(itemRegistry.getItemGenre(serieList)[1], "Drama");
        assertEquals(itemRegistry.getItemGenre(serieList)[2], "Mystery");
        assertEquals(itemRegistry.getItemRating(serieList), 8.8);
        assertEquals(itemRegistry.getSeriesSeasons(serieList)[0], 8);
    }

    @Test
    void ItemRegistryFinishedSeriesTest() {
        ArrayList<String> series = itemRegistry.getSeriesKeyList();
        String serieList = series.get(2);
        assertEquals(itemRegistry.getItemTitle(serieList), "Game Of Thrones");
        assertEquals(itemRegistry.getItemReleaseYear(serieList), 2011);
        assertEquals(itemRegistry.getSeriesEndYear(serieList), 0);
        assertEquals(itemRegistry.getItemGenre(serieList)[0], "Action");
        assertEquals(itemRegistry.getItemGenre(serieList)[1], "Adventure");
        assertEquals(itemRegistry.getItemGenre(serieList)[2], "Drama");
        assertEquals(itemRegistry.getItemRating(serieList), 9.5);
        assertEquals(itemRegistry.getSeriesSeasons(serieList)[6], 7);
    }
}