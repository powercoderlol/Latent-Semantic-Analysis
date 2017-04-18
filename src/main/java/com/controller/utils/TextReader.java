package main.java.com.controller.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Ivan on 16.04.2017.
 */
public class TextReader {
    private static List<String> ignoreChars;
    private BufferedReader buffReader;
    private StringTokenizer stringTokenizer;
    private boolean eof;
    private int paragraphCounter;

    public TextReader(File currentfile) throws FileNotFoundException {
        paragraphCounter = -1;
        fillIgnoreChars();
        buffReader = new BufferedReader(new FileReader(currentfile));
        eof = false;
    }

    public boolean openFile(File currentfile) throws FileNotFoundException {
        paragraphCounter = -1;
        buffReader = new BufferedReader((new FileReader(currentfile)));
        eof = false;
        return true;
    }

    private void fillIgnoreChars() throws FileNotFoundException {
        String line;
        buffReader = new BufferedReader(new FileReader("IgnoreList"));
        ignoreChars = new ArrayList<>(1000);
        try{
            while((line = buffReader.readLine()) != null) {
                ignoreChars.add(line);
            }
            buffReader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private String nextToken() {
        String line;
        while( stringTokenizer == null || !stringTokenizer.hasMoreTokens()) {
            try {
                line = buffReader.readLine();
                line = line.toLowerCase();
                stringTokenizer = new StringTokenizer(line);
            } catch (Exception e) {
                eof = true;
                return "";
            }
        }
        line = stringTokenizer.nextToken();
        return line;
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
        return ignoreChars.contains(line)? nextSpecToken() : line;
    }

    private String readParagraphPerLine() {
        String line;
        try {
            if (stringTokenizer == null || !stringTokenizer.hasMoreTokens()) {
                paragraphCounter++;
                line = buffReader.readLine();
                stringTokenizer = new StringTokenizer(line);
                return stringTokenizer.nextToken();
            } else {
                return stringTokenizer.nextToken();
            }
        } catch (Exception e) {
            paragraphCounter = -1;
            eof = true;
            return "";
        }
    }

    public int getParagraphCounter() {
        return paragraphCounter;
    }

    public String nextParagraphToken() {
        return readParagraphPerLine();
    }

    public String nextSpec() {
        return nextSpecToken();
    }

    public String next() {
        return nextToken();
    }

    public String readFullText() {
        String s = "";
        while(!eof) {
            s += next() + " ";
        }
        return s;
    }


    public String readFullTextSpec() {
        String s = "";
        while(!eof) {
            s += nextSpec() + " ";
        }
        return s;
    }




}
