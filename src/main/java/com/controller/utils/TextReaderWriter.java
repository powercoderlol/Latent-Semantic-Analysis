package com.controller.utils;

import com.algo.EMJLSVD;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.controller.utils.Constants.OUTPUT_DIRECTORY;
import static com.controller.utils.Constants.SOURCE_DIRECTORY;
import static com.controller.utils.Constants.SUCCESS_CREATING_FOLDER;

import com.algo.EMJLSVD;

import com.controller.DataController;
import com.view.utils.DataPrinter;

/**
 * Created by Ivan on 16.04.2017.
 */
public class TextReaderWriter {
    private static List<String> ignoreChars;
    private BufferedReader buffReader;
    private StringTokenizer stringTokenizer;
    private boolean eof;
    private int paragraphCounter;
    private static File[] listOfFiles;

    private static String filename;
    private static EMJLSVD lsaAlgoMachine;
    private static Scanner reader = new Scanner(System.in);

    public TextReaderWriter(File currentfile) throws FileNotFoundException {
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

    public static void setFileToProcess() {
        int mainParagraphOrder;
        String line;
        TextReaderWriter tr;
        DataController dc = new DataController();
        Scanner commandReader = new Scanner(System.in);
        if( TextReaderWriter.checkCreateShowFolder(SOURCE_DIRECTORY, true, "files") ) {
            System.out.print("Choose file by index: ");
            int fileNumber = commandReader.nextInt();
            System.out.println("File in process...");
            try {
                filename = SOURCE_DIRECTORY+ TextReaderWriter.getFileName(fileNumber);
                tr = new TextReaderWriter(new File(filename));
                mainParagraphOrder = tr.getParagraphOrder(filename);
                dc.setDataParagraphOrder(mainParagraphOrder);
                while(!(line = tr.nextParagraphToken()).equals("") )  {
                    mainParagraphOrder = tr.getParagraphCounter();
                    dc.addDataParagraph(line, mainParagraphOrder);
                }
                lsaAlgoMachine = new EMJLSVD(dc.getMatrixAlgoCols(), dc.getMatrixAlgoNums(), dc.getMatrixAlgoData());
                System.out.println("Done!\n");
            } catch (FileNotFoundException e) {
                e.getMessage();
            }
        } else {
            System.out.println("Nothing to process.");
        }
    }

    public static void printVtMatrixMainDiagonal() {
        if(filename == null) {
            System.out.println("Set file to process first");
        } else {
            DataPrinter.printComparsionVMatrix(lsaAlgoMachine.getComparsion(), lsaAlgoMachine.getCols(), true);
        }
    }

    public static void getSvdAllToAll() {
        String outputDirName, buffDate, buffFileName;
        Date currentDate;
        SimpleDateFormat sdf;
        DataController dc;
        if (filename == null) {
            System.out.println("Set file to process first");
        } else {
            if (TextReaderWriter.checkCreateShowFolder(OUTPUT_DIRECTORY, false, "exist")) {
                System.out.println("Calculation in progress...");
                sdf = new SimpleDateFormat("dd-MM-yyyy_'T'_HH-mm-ss");
                currentDate = new Date();
                buffDate = sdf.format(currentDate);
                buffFileName = filename.substring(SOURCE_DIRECTORY.length(), filename.length()-4);
                outputDirName = OUTPUT_DIRECTORY + "SVD comparsion all to all " + buffFileName + " " + buffDate + "/";
                if(TextReaderWriter.checkCreateShowFolder(outputDirName, false, "exist")) {
                    for(int i = 0; i < lsaAlgoMachine.getCols(); i++) {
                        buffFileName = outputDirName + (i+1) + " paragraph.txt";
                        DataPrinter.printDataInFile(buffFileName, lsaAlgoMachine.getComparsionAll(i));
                    }
                }
                System.out.println("Done! See files with data in " + outputDirName + " directiry");
            }
        }

    }

    public static void getSvdInSeries() {
        String outputFileName, buffFileName, buffDate;
        Date currentDate;
        if (filename == null) {
            System.out.println("Set file to process first");
        } else {
            if (TextReaderWriter.checkCreateShowFolder(OUTPUT_DIRECTORY, false, "exist")) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy'_T_'HH-mm-ss");
                currentDate = new Date();
                buffDate = sdf.format(currentDate);
                buffFileName = filename.substring(SOURCE_DIRECTORY.length(),filename.length()-4);
                outputFileName = OUTPUT_DIRECTORY + "SVD comparsion in series " + buffFileName + " " + buffDate + ".txt";
                DataPrinter.printComparsionVMatrix(lsaAlgoMachine.getComparsion(), lsaAlgoMachine.getCols(), true);
                DataPrinter.printDataInFile(outputFileName, lsaAlgoMachine.getComparsion());
            }
        }
    }

    public static void printMatrixInFile() {
        Date currentDate;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy'_T_'HH-mm-ss");
        currentDate = new Date();
        String buffDate = sdf.format(currentDate);
        String outputFileName = OUTPUT_DIRECTORY + "matrix data " + buffDate + ".lsa";
        if(filename == null) {
            System.out.println("Set file to process first");
        } else {
            try ( PrintWriter writer = new PrintWriter(outputFileName)) {
                System.out.print("Matrix option for print (V or U or W): ");
                String name = reader.next();
                writer.print(lsaAlgoMachine.toString(name));
                System.out.println("Data was successfullly printed in file "+outputFileName);
                System.out.println("");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void printMatrixOnScreen() {
        String name = "";
        if(filename == null) {
            System.out.println("Set file to process first");
        } else {
            System.out.print("Matrix option for print (V or U or W): ");
            name = reader.next();
            System.out.println(lsaAlgoMachine.toString(name));
        }
    }

    public static void getClusterization() {
        int currentFileOrder;
        String currentFileName;
        Scanner reader = new Scanner(System.in);
        if(filename == null) {
            System.out.println("Set file to process first");
        } else {
            try {
                TextReaderWriter.checkCreateShowFolder(OUTPUT_DIRECTORY,true,"exist");
                System.out.print("Set file for clusterization: ");
                currentFileOrder = reader.nextInt();
                currentFileName = TextReaderWriter.getFileName(currentFileOrder);
                Process cluster = Runtime.getRuntime().exec("python scripts/cluster.py " + OUTPUT_DIRECTORY + currentFileName);
                System.out.println("Script in job. Wait for a while...");
                // add checker of exit codes
                // add inner readers
                while(cluster.isAlive()) { };
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }





}
