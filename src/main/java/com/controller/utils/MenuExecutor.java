package com.controller.utils;

import com.controller.DataController;
import com.view.utils.DataPrinter;

import com.algo.EMJLSVD;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;


import static com.controller.utils.Constants.*;

/**
 * Created by Ivan on 02.05.2017.
 */
public class MenuExecutor {
    private String filename;
    private EMJLSVD lsaAlgoMachine;


    private Menu currentMenu;

    private Menu subMenuEMJLLSA;
    private Menu subMenuFilesJob;

    private Menu subMenuEMJLLSATools;


    public MenuExecutor() {
        currentMenu = new Menu(MAIN_MENU_NAME);
        subMenuEMJLLSA = new Menu(EMJL_PROCESS_MENU_NAME);
        subMenuFilesJob = new Menu(FILES_JOB_MENU_NAME);

        subMenuEMJLLSATools = new Menu(EMJL_PROCESS_MENU_NAME);

        currentMenu.putCommand("Work with files", () -> activateMenu(subMenuFilesJob));
        currentMenu.putCommand("EMJL tools", () -> activateMenu(subMenuEMJLLSA));
        currentMenu.putCommand("Quit", () -> System.exit(0));

        subMenuEMJLLSA.putCommand("Start LSA", () -> activateMenu(subMenuEMJLLSATools));
        subMenuEMJLLSA.putCommand("Return to Main Menu", () -> activateMenu(currentMenu));
        subMenuEMJLLSA.putCommand("Quit", () -> System.exit(0));

        subMenuFilesJob.putCommand("Show files in source dir", () -> TextReader.checkCreateShowFolder(SOURCE_DIRECTORY, true, "exist"));
        subMenuFilesJob.putCommand("Show files in output dir", () -> TextReader.checkCreateShowFolder(OUTPUT_DIRECTORY, true, "exist"));
        subMenuFilesJob.putCommand("Return to Main Menu", () -> activateMenu(currentMenu));
        subMenuFilesJob.putCommand("Quit", ()-> System.exit(0));


        subMenuEMJLLSATools.putCommand("Set file to process", () -> setFileToProcess());
        subMenuEMJLLSATools.putCommand("Get clusterization and dendogram", () -> getClusterization());
        subMenuEMJLLSATools.putCommand("Get SVD result for paragraph in series", () -> getSvdInSeries());
        subMenuEMJLLSATools.putCommand("Get SVD result for paragraph all to all", () -> getSvdAllToAll());
        subMenuEMJLLSATools.putCommand("Print V^t main diagonal Matrix on screen", () -> printVtMatrixMainDiagonal());
        subMenuEMJLLSATools.putCommand("Print V^t Matrix on screen", () -> printVtMatrixOnScreen());
        subMenuEMJLLSATools.putCommand("Print V^t Matrix in file", () -> printVtMatrixInFile());
        subMenuEMJLLSATools.putCommand("Return to LSA tools menu", () -> activateMenu(subMenuEMJLLSA));
        subMenuEMJLLSATools.putCommand("Return to Main Menu", () -> activateMenu(currentMenu));
        subMenuEMJLLSATools.putCommand("Quit", ()-> System.exit(0));

        activateMenu(currentMenu);
    }


    private void activateMenu(Menu currMenu) {
        System.out.println(currMenu.showMenu());
        Scanner commandReader = new Scanner(System.in);
        while(true) {
            System.out.print("Next command: ");
            int commandNumber = commandReader.nextInt();
            currMenu.executeCommand(commandNumber);
        }
    }

    private void getClusterization() {
        int currentFileOrder;
        String currentFileName;
        Scanner reader = new Scanner(System.in);
        if(filename == null) {
            System.out.println("Set file to process first");
        } else {
            try {
                TextReader.checkCreateShowFolder(OUTPUT_DIRECTORY,true,"exist");
                System.out.println("Set file for clusterization: ");
                currentFileOrder = reader.nextInt();
                currentFileName = TextReader.getFileName(currentFileOrder);
                Process cluster = Runtime.getRuntime().exec("python scripts/cluster.py " + OUTPUT_DIRECTORY + currentFileName);
                System.out.print("Press any key to continue...");
                System.in.read();
                cluster.destroy();
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    private void printVtMatrixOnScreen() {
        if(filename == null) {
            System.out.println("Set file to process first");
        } else {
            System.out.println(lsaAlgoMachine.toString());
        }
    }

    private void printVtMatrixInFile() {
        Date currentDate;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy'_T_'HH-mm-ss");
        currentDate = new Date();
        String buffDate = sdf.format(currentDate);
        String outputFileName = OUTPUT_DIRECTORY + "Vt matrix data " + buffDate + ".lsa";
        if(filename == null) {
            System.out.println("Set file to process first");
        } else {
            try ( PrintWriter writer = new PrintWriter(outputFileName)) {
                writer.print(lsaAlgoMachine.toString());
                System.out.println("Data was successfullly printed in file "+outputFileName);
                System.out.println("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getSvdInSeries() {
        String outputFileName, buffFileName, buffDate;
        Date currentDate;
        if (filename == null) {
            System.out.println("Set file to process first");
        } else {
            if (TextReader.checkCreateShowFolder(OUTPUT_DIRECTORY, false, "exist")) {
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

    private void getSvdAllToAll() {
        String outputDirName, buffDate, buffFileName;
        Date currentDate;
        SimpleDateFormat sdf;
        DataController dc;
        if (filename == null) {
            System.out.println("Set file to process first");
        } else {
            if (TextReader.checkCreateShowFolder(OUTPUT_DIRECTORY, false, "exist")) {
                System.out.println("Calculation in progress...");
                sdf = new SimpleDateFormat("dd-MM-yyyy_'T'_HH-mm-ss");
                currentDate = new Date();
                buffDate = sdf.format(currentDate);
                buffFileName = filename.substring(SOURCE_DIRECTORY.length(), filename.length()-4);
                outputDirName = OUTPUT_DIRECTORY + "SVD comparsion all to all " + buffFileName + " " + buffDate + "/";
                if(TextReader.checkCreateShowFolder(outputDirName, false, "exist")) {
                    for(int i = 0; i < lsaAlgoMachine.getCols(); i++) {
                        buffFileName = outputDirName + (i+1) + " paragraph.txt";
                        DataPrinter.printDataInFile(buffFileName, lsaAlgoMachine.getComparsionAll(i));
                    }
                }
                System.out.println("Done! See files with data in " + outputDirName + " directiry");
            }
        }

    }

    private void printVtMatrixMainDiagonal() {
        if(filename == null) {
            System.out.println("Set file to process first");
        } else {
            DataPrinter.printComparsionVMatrix(lsaAlgoMachine.getComparsion(), lsaAlgoMachine.getCols(), true);
        }
    }

    private void setFileToProcess() {
        int mainParagraphOrder;
        String line;
        TextReader tr;
        DataController dc = new DataController();
        Scanner commandReader = new Scanner(System.in);
        if( TextReader.checkCreateShowFolder(SOURCE_DIRECTORY, true, "files") ) {
            System.out.print("Choose file by index: ");
            int fileNumber = commandReader.nextInt();
            System.out.println("File in process...");
            try {
                filename = SOURCE_DIRECTORY+TextReader.getFileName(fileNumber);
                tr = new TextReader(new File(filename));
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

}
