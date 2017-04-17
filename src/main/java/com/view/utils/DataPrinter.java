package main.java.com.view.utils;

import main.java.com.controller.DataController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Ivan on 17.04.2017.
 */
public class DataPrinter {

    public void printContainer(HashMap<String, Integer> matrix) {
        Iterator matrixIter = matrix.entrySet().iterator();
        while(matrixIter.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)matrixIter.next();
            System.out.println(pair.getKey()+" : "+ pair.getValue());
        }
    }
}
