package data;

import java.util.List;

interface DataAccess {
    public List<String> load(String dataFields);
    public void save(String dataField, List<String> data);

}