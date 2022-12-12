import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import data.DataAccessImpl;

class DataAccessTest {

    @BeforeAll
    static void beforeAll() {
    }
    
    @Test
    void loadSizeTest() {
        DataAccessImpl dataAccess = new DataAccessImpl("database");
        ArrayList<String> data = (ArrayList<String>) dataAccess.load("movies");
        assertEquals(100, data.size());
    }

    @Test
    void loadFirstTest() {
        DataAccessImpl dataAccess = new DataAccessImpl("database");
        ArrayList<String> data = (ArrayList<String>) dataAccess.load("movies");
        assertEquals("The Godfather; 1972; Crime, Drama; 9,2; ", data.get(0));
    }
}