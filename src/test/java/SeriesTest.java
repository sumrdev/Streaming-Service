import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import domain.Series;

class SeriesTest {
    @Test
    //test series not in a registry
    void seriesGettersTest() {
        String[] genres = new String[3];
        genres[0] = "Crime";
        genres[1] = "Drama";
        genres[2] = "Mystery";
        int[] seasons = new int[2];
        seasons[0] = 8;
        seasons[1] = 22;
        Series series = new Series("Twin Peaks",genres, 8.8, 1990, 1991, seasons);
        assertEquals(series.getTitle(), "Twin Peaks");
        assertEquals(series.getReleaseYear(), 1990);
        assertEquals(series.getEndYear(), 1991);
        assertEquals(series.getGenre()[0], "Crime");
        assertEquals(series.getGenre()[1], "Drama");
        assertEquals(series.getGenre()[2], "Mystery");
        assertEquals(series.getRating(), 8.8);
        assertEquals(series.getSeasons()[0], 8);
        assertEquals(series.getSeasons()[1], 22);
    }

}