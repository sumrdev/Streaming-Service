package presentation;
/**
 * The Main class to start the program from, calls MainWindow.main.
 * Javafx/Mvn-shade needs a class that does not extend Application to create jar,
 * hence this class is needed.
 */
public class Main {
    public static void main(String[] args) {
        MainWindow.main(args);
    }
}
