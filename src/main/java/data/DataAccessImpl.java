package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;

public class DataAccessImpl implements DataAccess {

    private final String path;

    public DataAccessImpl(String path) {
        this.path = path;
    }

    /**
     * Load data from file
     * 
     * @param dataField The name of the data field to load
     * @return A list of strings containing the data
     * @throws NullPointerException
     * @throws SecurityException
     * @throws Exception
     */
    @Override
    public List<String> load(String dataField) {
        List<String> data = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.path + "/" + dataField + ".txt"));
            reader.lines().forEach(data::add);
            reader.close();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error, file not found");
            alert.setContentText("Please try again");
            alert.showAndWait();
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error loading data");
            alert.setContentText("Please try again");
            alert.showAndWait();
        }
        return data;
    }

    /**
     * Save data to file
     * 
     * @param dataField The filename to save to
     * @param data      A list of strings containing the data
     * @
     */
    @Override
    public void save(String dataField, List<String> data) {
        try {
            File f = new File(this.path + "/" + dataField + ".txt");
            PrintWriter pw;
            pw = new PrintWriter(f);
            for (String s : data) {
                pw.println(s);
            }
            pw.close();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error saving data, no file name specified");
            alert.setContentText("Please try again");
            alert.showAndWait();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error saving data, no file name specified");
            alert.setContentText("Please try again");
            alert.showAndWait();
        } catch (SecurityException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Your system does not allow access to the database");
            alert.setContentText("Please try again");
            alert.showAndWait();
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error saving data");
            alert.setContentText("Please try again");
            alert.showAndWait();
        }
    }

}