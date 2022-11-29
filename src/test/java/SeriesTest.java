import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import domain.ItemRegistryImpl;

class SeriesTest {
    static ItemRegistryImpl itemRegistry;

    @BeforeAll
    static void beforeAll() {
        itemRegistry = new ItemRegistryImpl();
        itemRegistry.initialize();
    }

    @Test
    void toStringTest() {
        ArrayList<String> series = itemRegistry.getSeriesKeyList();
        String seriesKey = series.get(0);
        assertEquals(itemRegistry.getItemString(seriesKey), "Twin Peaks;1990-1991;Crime,Drama,Mystery;8,8;1-8,2-22;");
    }

    @Test
    void getSeriesTitleTest() {
        ArrayList<String> series = itemRegistry.getSeriesKeyList();
        String seriesKey = series.get(0);
        assertEquals(itemRegistry.getItemTitle(seriesKey), "Twin Peaks");
    }

    @Test
    void getSeriesGenreTest() {
        ArrayList<String> series = itemRegistry.getSeriesKeyList();
        String seriesKey = series.get(0);
        assertEquals(itemRegistry.getItemGenre(seriesKey)[0], "Crime");
    }

    @Test
    void getSeriesRatingTest() {
        ArrayList<String> series = itemRegistry.getSeriesKeyList();
        String seriesKey = series.get(0);
        assertEquals(itemRegistry.getItemRating(seriesKey), 8.8);
    }

    @Test
    void getSeriesStartYearTest() {
        ArrayList<String> series = itemRegistry.getSeriesKeyList();
        String seriesKey = series.get(0);
        assertEquals(itemRegistry.getItemRelease(seriesKey), 1990);
    }

    @Test
    void getSeriesEndYearTest() {
        ArrayList<String> series = itemRegistry.getSeriesKeyList();
        String seriesKey = series.get(0);
        assertEquals(itemRegistry.getSeriesEndYear(seriesKey), 1991);
    }

    @Test
    void getSeriesSeasonsTest() {
        ArrayList<String> series = itemRegistry.getSeriesKeyList();
        String seriesKey = series.get(0);
        assertEquals(itemRegistry.getSeriesSeasons(seriesKey).get(0), 8);
    }

}