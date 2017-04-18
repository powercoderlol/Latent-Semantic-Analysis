package main.java.com.data;


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

    public DataContainer(HashMap<String, Integer > data) {
        StoredDataAllTextEntries = data;
    }

    public void setDataAllTextEntries(HashMap<String, Integer > InputStoredData) {
        StoredDataAllTextEntries = InputStoredData;
    }

    public HashMap<String, Integer> getDataAllTextEntries() {
        return StoredDataAllTextEntries;
    }

}
