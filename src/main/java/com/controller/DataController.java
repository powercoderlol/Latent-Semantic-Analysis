package main.java.com.controller;

import main.java.com.data.DataContainer;
import main.java.com.view.utils.DataPrinter;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ivan on 17.04.2017.
 */
public class DataController {
    HashMap<String, Integer > StoredData;
    DataContainer ContainerData;
    DataPrinter ContainerPrinter;
    int TextOrder;

    public DataController() {
        StoredData = new HashMap<>(1000);
        ContainerData = new DataContainer(StoredData);
        ContainerPrinter = new DataPrinter();
    }

    public void FillData(String token) {
        Integer value;
        value = this.StoredData.get(token);

        if (value != null) {
            this.StoredData.put(token, this.StoredData.get(token) + 1);
        } else {
            this.StoredData.put(token, 1);
        }
    }

    public void setCurrData(DataContainer ContainerData) {
        this.ContainerData = ContainerData;
    }

    public void setCurrData(HashMap<String, Integer> StoredData) {
        this.ContainerData.setData(StoredData);
        this.StoredData = StoredData;
    }

    public void setCurrData() {
        this.ContainerData.setData(this.StoredData);
    }

    public void setCurrView(DataPrinter ContainerPrinter) { this.ContainerPrinter = ContainerPrinter; }


    public void updateView() {
        ContainerPrinter.printContainer(this.ContainerData.getData());
    }

}
