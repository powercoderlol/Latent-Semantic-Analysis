package com.data;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ivan on 16.04.2017.
 */
public class DataContainer {
    private HashMap<String, Integer > StoredDataAllTextEntries;
    private HashMap< String, int[] > StoredDataParagraphEntries;


    public DataContainer() {
        StoredDataAllTextEntries = new HashMap<>();
        StoredDataParagraphEntries = new HashMap<>();
    }

    public void setDataAllTextEntries(HashMap<String, Integer > InputStoredData) {
        StoredDataAllTextEntries = InputStoredData;
    }

    public void setStoredDataParagraphEntries(HashMap<String, int[]> InputStoredData) {
        StoredDataParagraphEntries = InputStoredData;
    }

    public HashMap<String, Integer> getDataFullTextEntries() {
        return StoredDataAllTextEntries;
    }

    public HashMap<String, int[]> getStoredDataParagraphEntries() {
        return StoredDataParagraphEntries;
    }

}
