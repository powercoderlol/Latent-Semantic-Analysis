package main.java.com.controller;

import main.java.com.controller.utils.DataContainer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ivan on 17.04.2017.
 */
public class DataController {
    HashMap<String, ArrayList<Integer> > StoredData;
    DataContainer currData;
    int TextOrder;


    public void FillData(String token, int order) {

        StoredData = new HashMap<>(1000);

        if (!StoredData.containsValue(token)) StoredData.put(token, new ArrayList<>());
        StoredData.get(token).set(order, StoredData.get(token).get(order)+ 1);

    }

    public void setCurrData() {
        currData = new DataContainer(StoredData);
    }


}
