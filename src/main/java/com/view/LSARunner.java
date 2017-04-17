package main.java.com.view;

import main.java.com.controller.utils.TextReader;
import main.java.com.controller.DataController;
import main.java.com.controller.utils.DataContainer;

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
        DataContainer currDataContainer;
        DataController currDataInput = new DataController();
        String line;
        try{
            TextReader tr = new TextReader( new File("Deutschland.txt"));
            while( (line = tr.next()) != "") {
                //currDataInput.FillData(line, 0);
            }
            currDataInput.setCurrData();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
