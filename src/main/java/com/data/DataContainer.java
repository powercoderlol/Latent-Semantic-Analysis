package main.java.com.data;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ivan on 16.04.2017.
 */
public class DataContainer {
    private HashMap<String, Integer > StoredDataAllTextEntries;
    private HashMap< String, ArrayList< Integer > > StoredDataParagraphEntries;


    public DataContainer() {
        StoredDataAllTextEntries = new HashMap<>();
        StoredDataParagraphEntries = new HashMap<>();
    }

    /*
    public DataContainer(HashMap<String, Integer > data) {
        StoredDataAllTextEntries = data;
    }
    */

    public void setDataAllTextEntries(HashMap<String, Integer > InputStoredData) {
        StoredDataAllTextEntries = InputStoredData;
    }

    public void setStoredDataParagraphEntries(HashMap<String, ArrayList<Integer>> InputStoredData) {
        StoredDataParagraphEntries = InputStoredData;
    }

    public HashMap<String, Integer> getDataFullTextEntries() {
        return StoredDataAllTextEntries;
    }

    public HashMap<String, ArrayList<Integer>> getStoredDataParagraphEntries() {
        return StoredDataParagraphEntries;
    }

}
