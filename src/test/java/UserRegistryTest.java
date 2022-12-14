import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import domain.UserRegistry;
import domain.UserRegistryImpl;

class UserRegistryTest {
    static UserRegistryImpl userRegistry;

    @BeforeAll
    static void beforeAll() {
        userRegistry = new UserRegistryImpl();
        userRegistry.initialize();
    }

    @Test
    void addUserTest() {
        userRegistry.addUser("TestUser1");
        assertTrue( userRegistry.getUsernameList().contains("TestUser1"));
        userRegistry.removeUser("TestUser1");
    }

    @Test
    void userGetUsername() {
        userRegistry.addUser("TestUser2");
        assertEquals("TestUser2", userRegistry.getUsername("TestUser2"));
        userRegistry.removeUser("TestUser2");
    }

    @Test
    void userAddGetFavoriteItems() {
        userRegistry.addUser("TestUser3");
        userRegistry.addFavoriteItem("TestUser3", "TestItem");
        assertTrue( userRegistry.getFavoriteItems("TestUser3").contains("TestItem"));
        userRegistry.removeUser("TestUser3");
    }

    @Test
    void userRemoveFavoriteItems() {
        userRegistry.addUser("TestUser4");
        userRegistry.addFavoriteItem("TestUser4", "TestItem");
        userRegistry.removeFavoriteItem("TestUser4", "TestItem");
        assertTrue( !userRegistry.getFavoriteItems("TestUser4").contains("TestItem"));
        userRegistry.removeUser("TestUser4");
    }

    @Test
    void userAlreadyExistsTest() {
        System.out.println(userRegistry.getUsernameList());
        userRegistry.addUser("TestUser");
        try{
            userRegistry.addUser("TestUser");
            assertTrue(false);
        } catch (IllegalArgumentException e){
            assertEquals("User already exists", e.getMessage());
        } 
        userRegistry.removeUser("TestUser");
    }


    @Test
    void removeUserTest() {
        userRegistry.addUser("TestUser5");
        userRegistry.removeUser("TestUser5");
        assertTrue( !userRegistry.getUsernameList().contains("TestUser5"));
    }        


    @Test
    void saveUserRegistryTest() {
        userRegistry.addUser("TestUser6");
        userRegistry.save();
        UserRegistry userRegistry2 = new UserRegistryImpl();
        userRegistry2.initialize();
        assertTrue( userRegistry2.getUsernameList().contains("TestUser6"));
        //cleanup
        userRegistry.removeUser("TestUser6");
        userRegistry.save();
    }
}