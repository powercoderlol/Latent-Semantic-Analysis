package main.java.com.controller.utils;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ivan on 16.04.2017.
 */
public class DataContainer {
    private HashMap<String, ArrayList<Integer> > StoredData;
    private int TextCount = 0;


    public DataContainer() {
        StoredData = new HashMap<>();
        TextCount++;
    }

    public DataContainer(HashMap<String, ArrayList<Integer> > data, int TextOrder) {
        TextCount = TextOrder;
        StoredData = data;
    }

    public DataContainer(HashMap<String, ArrayList<Integer> > data) {
        StoredData = data;
    }

    public void setData(HashMap<String, ArrayList<Integer> > InputStoredData) {
        StoredData = InputStoredData;
    }


}
