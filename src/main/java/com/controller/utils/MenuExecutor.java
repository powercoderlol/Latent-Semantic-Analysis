package com.controller.utils;

import com.algo.EMJLSVD;

import java.util.Scanner;

import static com.controller.utils.Constants.*;

/**
 * Created by Ivan on 02.05.2017.
 */
public class MenuExecutor {

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

        subMenuFilesJob.putCommand("Show files in output dir", () -> TextReaderWriter.checkCreateShowFolder(OUTPUT_DIRECTORY, true, "exist"));
        subMenuFilesJob.putCommand("Return to Main Menu", () -> activateMenu(currentMenu));
        subMenuFilesJob.putCommand("Quit", ()-> System.exit(0));


        subMenuEMJLLSATools.putCommand("Set file to process", TextReaderWriter::setFileToProcess);
        subMenuEMJLLSATools.putCommand("Get clusterization and dendogram", TextReaderWriter::getClusterization);
        subMenuEMJLLSATools.putCommand("Get SVD result for paragraph in series", TextReaderWriter::getSvdInSeries);
        subMenuEMJLLSATools.putCommand("Get SVD result for paragraph all to all", TextReaderWriter::getSvdAllToAll);
        subMenuEMJLLSATools.putCommand("Print V^t main diagonal Matrix on screen", TextReaderWriter::printVtMatrixMainDiagonal);
        subMenuEMJLLSATools.putCommand("Print Matrix on screen", TextReaderWriter::printMatrixOnScreen);
        subMenuEMJLLSATools.putCommand("Print Matrix in file", TextReaderWriter::printMatrixInFile);
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

    

}
