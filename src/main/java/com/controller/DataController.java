package main.java.com.controller;

import main.java.com.data.DataContainer;
import main.java.com.view.utils.DataPrinter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ivan on 17.04.2017.
 */
public class DataController {
    private HashMap<String, Integer > StoredDataAllTextEntries;
    private HashMap<String, ArrayList<Integer> > StoredDataParagraphEntries;
    private DataContainer ContainerData;
    private DataPrinter ContainerPrinter;
    private int TextOrder;

    public DataController() {



        ContainerData = new DataContainer();
        ContainerPrinter = new DataPrinter();
    }

    public void addDataFullText(String token) {
        Integer value;
        value = this.StoredDataAllTextEntries.get(token);

        if (value != null) {
            this.StoredDataAllTextEntries.put(token, this.StoredDataAllTextEntries.get(token) + 1);
        } else {
            this.StoredDataAllTextEntries.put(token, 1);
        }
    }

    /*
    public void FillDataParagraph(String token, int order) {

        if(this.StoredDataParagraphEntries.get(token) != null) {
            this.StoredDataParagraphEntries.put(token, this.StoredDataParagraphEntries.get(token).)
        } else {

        }
    }
    */

    public void setCurrDataFullText(DataContainer ContainerData) {
        this.ContainerData = ContainerData;
    }

    public void setCurrDataFullText(HashMap<String, Integer> StoredData) {
        this.ContainerData.setDataAllTextEntries(StoredData);
        this.StoredDataAllTextEntries = StoredData;
    }

    public void setCurrDataFullText() {
        this.ContainerData.setDataAllTextEntries(this.StoredDataAllTextEntries);
    }

    public void setCurrView(DataPrinter ContainerPrinter) { this.ContainerPrinter = ContainerPrinter; }


    public void printMatrixFullText() {
        ContainerPrinter.printContainer(this.ContainerData.getDataAllTextEntries());
    }

}
