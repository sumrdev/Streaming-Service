package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
     * @throws Exception
     * @throws IOException
     * @throws FileNotFoundException
     */
    @Override
    public List<String> load(String dataField) {
        List<String> data = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(
                this.path + "/" + dataField + ".txt")));
            System.out.println(reader.lines());
            reader.lines().forEach(data::add);
            reader.close();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error, file not found");
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
        File f = new File(getClass().getClassLoader().getResource(
            this.path + "/" + dataField + ".txt").getPath());
        PrintWriter pw;
        try {
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