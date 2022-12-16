import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import domain.User;

class UserTest {

    @Test
    void userGetUsername() {
        User user = new User("TestUser");
        assertEquals("TestUser", user.getUsername());
    }

    @Test
    void userToString() {
        User user = new User("TestUser");
        user.addFavoriteItem("TestItem");
        user.addFavoriteItem("TestItem2");
        assertEquals("TestUser;TestItem2,TestItem;", user.toString());
    }
}