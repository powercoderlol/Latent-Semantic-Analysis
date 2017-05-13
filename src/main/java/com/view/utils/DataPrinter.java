package com.view.utils;

import com.controller.DataController;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Ivan on 17.04.2017.
 */
public class DataPrinter {
    private static PrintWriter pw;

    public void printContainerFullText(HashMap<String, Integer> matrix) {
        Iterator matrixIter = matrix.entrySet().iterator();
        while(matrixIter.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)matrixIter.next();
            System.out.println(pair.getKey()+" : "+ pair.getValue());
        }
    }

    public String printContainerParagraph(HashMap<String, double[]> matrix) {
        String result = "";
        Iterator matrixIter = matrix.entrySet().iterator();
        while(matrixIter.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)matrixIter.next();
            double[] arr = (double[])pair.getValue();
            System.out.print(pair.getKey()+" ");
            //result += pair.getKey()+" ";
            for(int i = 0; i < arr.length; i++) {
                result += arr[i] + " " ;
                System.out.print(arr[i]+" ");
            }
            result +=  "\n";
            System.out.println();
        }
        return result;
    }

    public static void printDataInFile(String filename, double[] data) {
        try {
            pw = new PrintWriter(filename);
            for(int i = 0; i < data.length; i++) {
                pw.println(data[i]);
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String printComparsionVMatrix(double[] res, int Cols, boolean onScreen) {
        String result = "";
        StringBuilder resultString = new StringBuilder();
        for(int i = 0; i < Cols-1; i++) {
            if (onScreen) System.out.println(res[i]);
            result = res[i] + "\n";
            resultString.append(result);
        }
        System.out.println();
        return resultString.toString();
    }
}
