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
        StoredDataAllTextEntries = new HashMap<>();
        StoredDataParagraphEntries = new HashMap<>();


        ContainerData = new DataContainer();
        ContainerPrinter = new DataPrinter();
    }

    public void addDataFullText(String token) {
        Integer value;
        value = StoredDataAllTextEntries.get(token);

        if (value != null) {
            StoredDataAllTextEntries.put(token, StoredDataAllTextEntries.get(token) + 1);
        } else {
            StoredDataAllTextEntries.put(token, 1);
        }
    }


    public void addDataParagraph(String token, int order) {

        StoredDataParagraphEntries.computeIfAbsent(token, key -> new ArrayList<>());


        if(StoredDataParagraphEntries.get(token).size() == 0) {
            for(int i= 0; i < order; i++) {
                StoredDataParagraphEntries.get(token).add(0, 0);
            }
        } else {
            int currVal = StoredDataParagraphEntries.get(token).get(order);
            StoredDataParagraphEntries.put(token, StoredDataParagraphEntries.get(token)).add(order, currVal + 1);
        }
    }


    public void setCurrDataFullText(DataContainer ContainerData) {
        this.ContainerData = ContainerData;
    }

    public void setCurrDataFullText(HashMap<String, Integer> StoredData) {
        ContainerData.setDataAllTextEntries(StoredData);
        StoredDataAllTextEntries = StoredData;
    }

    public void setCurrDataFullText() {
        ContainerData.setDataAllTextEntries(StoredDataAllTextEntries);
    }

    public void setCurrDataParagraphs(HashMap<String, ArrayList<Integer>> StoredData) {
        ContainerData.setStoredDataParagraphEntries(StoredData);
    }

    public void setCurrDataParagraphs() {
        ContainerData.setStoredDataParagraphEntries(StoredDataParagraphEntries);
    }

    //public void setCurrView(DataPrinter ContainerPrinter) { this.ContainerPrinter = ContainerPrinter; }

    public void printMatrixFullText() {
        ContainerPrinter.printContainerFullText(ContainerData.getDataFullTextEntries());
    }

    public void printMatrixParagraph() {
        ContainerPrinter.printContainerParagraph(ContainerData.getStoredDataParagraphEntries());
    }

}
