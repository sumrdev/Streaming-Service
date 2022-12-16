import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import domain.Movie;

class MovieTest {

    @Test
    void movieGettersTest() {
        String[] genres = new String[2];
        genres[0] = "Crime";
        genres[1] = "Drama";
        Movie movie = new Movie("The Godfather", genres, 9.2, 1972 );
        assertEquals(movie.getTitle(), "The Godfather");
        assertEquals(movie.getReleaseYear(), 1972);
        assertEquals(movie.getGenre()[0], "Crime");
        assertEquals(movie.getGenre()[1], "Drama");
        assertEquals(movie.getRating(), 9.2);
    }
}