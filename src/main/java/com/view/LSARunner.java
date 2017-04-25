package com.view;


import com.algo.LSA;
import com.controller.utils.TextReader;
import com.controller.DataController;

import java.io.File;
import java.io.IOException;

/**
 * Created by Ivan on 16.04.2017.
 */
public class LSARunner {
    public static void main(String arg[]) {
        System.out.println("Welcome!");
        new LSARunner().run();
    }

    private void run() {
        DataController currDataInput = new DataController();
        String line;
        int mainOrder;
        try{
            TextReader tr = new TextReader( new File("DeutschlandNounCommon.txt"));

            while( !(line = tr.next()).equals("")) {
                currDataInput.addDataFullText(line);
            }

            currDataInput.setDataParagraphOrder(tr.getParagraphOrder("DeutschlandNounCommon.txt"));
            tr.openFile( new File("DeutschlandNounCommon.txt"));

            while( !(line = tr.nextParagraphToken()).equals("")) {
                int count = tr.getParagraphCounter();
                currDataInput.addDataParagraph(line, count);
            }
            currDataInput.setCurrDataParagraphs();
            currDataInput.printMatrixParagraph();

            //currDataInput.getMatrixAlgoData();

            LSA algoMachine = new LSA(currDataInput.getMatrixAlgoCols(),currDataInput.getMatrixAlgoNums(),currDataInput.getMatrixAlgoData());
            algoMachine.printW();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
