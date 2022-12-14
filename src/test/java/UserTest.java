import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import domain.UserRegistryImpl;

class UserTest {
    static UserRegistryImpl userRegistry;

    @BeforeAll
    static void beforeAll() {
        userRegistry = new UserRegistryImpl();
        userRegistry.initialize();
        userRegistry.addUser("TestUser");
    }

    @Test
    void userGetUsername() {
        assertEquals("TestUser", userRegistry.userMap.get(userRegistry.getUsername("TestUser")).getUsername());
    }

    @Test
    void userToString() {
        userRegistry.addFavoriteItem("TestUser", "TestItem");
        userRegistry.addFavoriteItem("TestUser", "TestItem2");
        System.out.println(userRegistry.userMap.get(userRegistry.getUsername("TestUser").toString()));
        assertEquals("TestUser;TestItem2,TestItem;", userRegistry.userMap.get(userRegistry.getUsername("TestUser")).toString());
    }

}