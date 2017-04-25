package com.controller;

import com.data.DataContainer;
import com.view.utils.DataPrinter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;
import org.ejml.simple.SimpleSVD;

import org.ejml.alg.dense.decomposition.svd.SvdImplicitQrDecompose_D64;
import org.ejml.data.DenseMatrix64F;

/**
 * Created by Ivan on 17.04.2017.
 */
public class DataController {
    private HashMap<String, Integer > StoredDataAllTextEntries;
    private HashMap<String, int[] > StoredDataParagraphEntries;
    private DataContainer ContainerData;
    private DataPrinter ContainerPrinter;
    private int paragraphOrder;
    private int keyValueMappings;
    double[] MainMatrix;


    public DataController() {
        StoredDataAllTextEntries = new HashMap<>();
        StoredDataParagraphEntries = new HashMap<>();
        paragraphOrder = 0;

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

    public void addDataParagraph(String token, int currOrder) {
        addDataParagraphOrder(token);
        StoredDataParagraphEntries.get(token)[currOrder]++;
    }

    private void convertDataParagraphIntoMatrix() {

        keyValueMappings = StoredDataParagraphEntries.size();
        MainMatrix = new double[paragraphOrder*keyValueMappings];
        Iterator matrixIter = StoredDataParagraphEntries.entrySet().iterator();
        for(int i = 0; (i < keyValueMappings) && matrixIter.hasNext(); i++) {
            HashMap.Entry pair = (HashMap.Entry)matrixIter.next();
            for(int j = 0; j < paragraphOrder; j++) {
                int arr[] = (int[])pair.getValue();
                MainMatrix[i] = arr[j];
            }
        }
        //DenseMatrix64F matr = new DenseMatrix64F(keyValueMappings,paragraphOrder,true,MainMatrix);
        //matr.print();
    }

    public double[] getMatrixAlgoData() {
        convertDataParagraphIntoMatrix();
        return MainMatrix;
    }

    public int getMatrixAlgoCols() {
        return paragraphOrder;
    }

    public int getMatrixAlgoNums() {
        keyValueMappings = StoredDataParagraphEntries.size();
        return keyValueMappings;
    }


    private void addDataParagraphOrder(String token) {

        StoredDataParagraphEntries.computeIfAbsent(token, key -> new int[paragraphOrder]);

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

    public void setCurrDataParagraphs(HashMap<String, int[]> StoredData) {
        ContainerData.setStoredDataParagraphEntries(StoredData);
    }

    public void setCurrDataParagraphs() {
        ContainerData.setStoredDataParagraphEntries(StoredDataParagraphEntries);
    }

    public void setCurrView(DataPrinter ContainerPrinter) { this.ContainerPrinter = ContainerPrinter; }

    public void printMatrixFullText() {
        ContainerPrinter.printContainerFullText(ContainerData.getDataFullTextEntries());
    }

    public void printMatrixParagraph() {
        ContainerPrinter.printContainerParagraph(ContainerData.getStoredDataParagraphEntries());
    }

    public void setDataParagraphOrder(int order) {
        paragraphOrder = order;
    }

}
