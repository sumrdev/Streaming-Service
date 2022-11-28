package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class DataAccessImpl implements DataAccess {

    private final String path;

    public DataAccessImpl(String path) {
        this.path = path;
    }

    // Load data from file and return as a list of strings
    // Each string is a line from the file
    // Throws an exception if the file is not found or if there is an error reading the file
    @Override
    public List<String> load(String dataField) {
        List<String> data = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.path + "/" + dataField + ".txt"));
            reader.lines().forEach(data::add);
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO Exception while closing file");
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return data;
    }

    // Save data to file
    // Throws an exception if there is an error writing to the file
    @Override
    public void save(String dataField, List<String> data) {
        File f = new File(this.path + "/" + dataField + ".txt");
        PrintWriter pw;
        try {
            pw = new PrintWriter(f);
            for (String s : data) {
                pw.println(s);
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}