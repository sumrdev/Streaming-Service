package domain;

public interface userRegistry {
    public void initialize();
    public void addUser(String username);
    public void addUser(String username, String[] favorites);
    public void removeUser(String username);
    public void save();
}
