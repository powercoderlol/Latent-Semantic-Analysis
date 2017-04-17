package main.java.com.view;


import main.java.com.controller.utils.TextReader;
import main.java.com.controller.DataController;
import main.java.com.data.DataContainer;

import java.io.File;
import java.io.IOException;
import main.java.com.algo.PortersStemming;

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
            TextReader tr = new TextReader( new File("Deutschland.txt"));
            while( (line = tr.next()) != "") {
                line = PortersStemming.stem(line);
                currDataInput.FillData(line);
            }
            currDataInput.setCurrData();
            currDataInput.updateView();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
