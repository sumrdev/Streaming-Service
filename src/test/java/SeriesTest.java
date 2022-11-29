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
        System.out.println(itemRegistry.getItemString(seriesKey).toString());
    }

}