package domain;
import java.util.*;

public interface userRegistry {
    public void initialize();
    public void addUser(String username);
    public void addUser(String username, ArrayList<String> favorites);
    public void removeUser(String username);
    public void save();
}
