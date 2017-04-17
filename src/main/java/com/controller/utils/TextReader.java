package main.java.com.controller.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Ivan on 16.04.2017.
 */
public class TextReader {
    private static List<String> ignorechars;
    private BufferedReader buffReader;
    private StringTokenizer stringTokenizer;
    private boolean eof;

    private void fillIgnoreChars() throws FileNotFoundException {
        String line;
        buffReader = new BufferedReader(new FileReader("IgnoreList"));
        ignorechars = new ArrayList<>(1000);
        try{
            while((line = buffReader.readLine()) != null) {
                ignorechars.add(line);
            }
            buffReader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private String nextSpecToken() {
        String line;
        while( stringTokenizer == null || !stringTokenizer.hasMoreTokens()) {
            try {
                line = buffReader.readLine();
                line = line.toLowerCase();
                stringTokenizer = new StringTokenizer(line, Constants.delimiters);
            } catch (Exception e) {
                eof = true;
                return "";
            }
        }
        line = stringTokenizer.nextToken();
        return ignorechars.contains(line)? nextSpecToken() : line;
    }

    public TextReader(File currentfile) throws FileNotFoundException {
        fillIgnoreChars();
        buffReader = new BufferedReader(new FileReader(currentfile));
        eof = false;
    }

    public String next() {
        return nextSpecToken();
    }

    public String readAll() {
        String s = "";
        while( eof == false) {
            s += next() + " ";
        }
        return s;
    }




}
