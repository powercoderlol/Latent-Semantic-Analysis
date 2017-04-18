package main.java.com.view;


import main.java.com.controller.utils.TextReader;
import main.java.com.controller.DataController;

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
        try{
            TextReader tr = new TextReader( new File("DeutschlandNoun.txt"));

            while( !(line = tr.next()).equals("")) {
                currDataInput.addDataFullText(line);
            }

            tr.openFile( new File("DeutschlandNoun.txt"));

            while( !(line = tr.nextParagraphToken()).equals("")) {
                int count = tr.getParagraphCounter();
                currDataInput.addDataParagraph(line, count);
            }


            currDataInput.setCurrDataParagraphs();
            currDataInput.printMatrixParagraph();
            //currDataInput.setCurrDataFullText();
            //currDataInput.printMatrixFullText();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
