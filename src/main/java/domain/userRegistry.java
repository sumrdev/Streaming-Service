package domain;

public interface userRegistry {
    public void initialize();
    public void addUser(String username, String password);
    public void removeUser(String username);
    public void save();
}
