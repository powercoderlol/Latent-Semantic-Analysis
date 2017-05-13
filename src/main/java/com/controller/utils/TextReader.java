package com.controller.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static com.controller.utils.Constants.OUTPUT_DIRECTORY;
import static com.controller.utils.Constants.SOURCE_DIRECTORY;
import static com.controller.utils.Constants.SUCCESS_CREATING_FOLDER;

/**
 * Created by Ivan on 16.04.2017.
 */
public class TextReader {
    private static List<String> ignoreChars;
    private BufferedReader buffReader;
    private StringTokenizer stringTokenizer;
    private boolean eof;
    private int paragraphCounter;
    private static File[] listOfFiles;

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

    public int getParagraphOrder(String filename) {
        int order = 0;
        String currLine;
        try {
            if (openFile(new File(filename))) {
                while( (currLine = buffReader.readLine()) != null) {
                    order++;
                }
            }
            openFile(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return order;
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

    public static boolean checkCreateShowFolder(String folderName, boolean printContent, String checkOption) {
        File folder = new File(folderName);
        boolean successCreate = false;
        boolean exitsFiles = false;
        boolean existDir = false;
        if(!folder.exists()) {
            try{
                folder.mkdir();
                successCreate = true;
                existDir = true;
            } catch(SecurityException securityException) {
                securityException.getMessage();
            } finally {
                if (successCreate) {
                    System.out.println(SUCCESS_CREATING_FOLDER+" "+folderName);
                }
            }
        } else {
            existDir = true;
        }
        listOfFiles = folder.listFiles();
        if (printContent) {
            if (listOfFiles.length > 0) {
                exitsFiles = true;
                System.out.println();
                for (int i = 0; i < listOfFiles.length; i++) {
                    System.out.println((i + 1) + ":" + ((listOfFiles[i].isFile()) ? " (file) " : " (directory) ") + listOfFiles[i].getName());
                }
                System.out.println();
            } else {
                System.out.println("Nothing to show.");
            }
        }
        switch(checkOption.charAt(0)) {
            case 'e': return existDir;
            case 'f': return exitsFiles;
            default: return existDir;
        }
    }

    public static String getFileName(int order) {
        order--;
        if(!(order < 0) && !(order >= listOfFiles.length)) return listOfFiles[order].getName();
        else return "Failed to load file with index " + (order+1);
    }



}
