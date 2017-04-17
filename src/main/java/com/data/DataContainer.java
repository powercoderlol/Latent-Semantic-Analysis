package main.java.com.data;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ivan on 16.04.2017.
 */
public class DataContainer {
    private HashMap<String, Integer > StoredData;


    public DataContainer() {
        StoredData = new HashMap<>();
    }

    public DataContainer(HashMap<String, Integer > data) {
        StoredData = data;
    }

    public void setData(HashMap<String, Integer > InputStoredData) {
        StoredData = InputStoredData;
    }

    public HashMap<String, Integer> getData() {
        return StoredData;
    }

}
