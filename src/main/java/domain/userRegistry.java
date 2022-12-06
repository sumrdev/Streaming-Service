package domain;
import java.util.*;

public interface UserRegistry {
    public void initialize();
    public void addUser(String username);
    public void addUser(String username, String[] favorites);
    public void removeUser(String username);
    public void save();
    public String getUsername(String userKey);
    public HashSet<String> getFavoriteItems (String userKey);
    public void addFavoriteItem (String userKey, String itemKey);
    public void removeFavoriteItem (String userKey, String itemKey);
    public ArrayList<String> getUsernameList();
    public void selectUser(String userKey);
}
