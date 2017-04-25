package com.view.utils;

import com.controller.DataController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Ivan on 17.04.2017.
 */
public class DataPrinter {

    public void printContainerFullText(HashMap<String, Integer> matrix) {
        Iterator matrixIter = matrix.entrySet().iterator();
        while(matrixIter.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)matrixIter.next();
            System.out.println(pair.getKey()+" : "+ pair.getValue());
        }
    }

    public void printContainerParagraph(HashMap<String, int[]> matrix) {
        Iterator matrixIter = matrix.entrySet().iterator();
        while(matrixIter.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)matrixIter.next();
            int[] arr = (int[])pair.getValue();
            System.out.print(pair.getKey()+" ");
            for(int i = 0; i < arr.length; i++) {
                System.out.print(arr[i]+" ");
            }
            System.out.println();
        }
    }
}
