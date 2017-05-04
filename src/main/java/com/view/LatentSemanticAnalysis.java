package com.view;


import java.io.File;
import static com.controller.utils.Constants.*;
import com.controller.utils.MenuExecutor;

/**
 * Created by Ivan on 02.05.2017.
 */
public class LatentSemanticAnalysis {
     private static MenuExecutor MainMenu;


    private static void printWelcomeScreen() {
        for(int i = 0; i < WELCOME_MSG.length()+4; i++) {
            System.out.print("*");
        }
        System.out.print("\n \n  "+WELCOME_MSG+"  \n \n");
        for(int i = 0; i < WELCOME_MSG.length()+4; i++) {
            System.out.print("*");
        }
        System.out.println();
    }

    private static void showMenu() {
        MainMenu = new MenuExecutor();
    }

    public static void run() {
        printWelcomeScreen();
        showMenu();
    }
}

