package com.controller.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


import static com.controller.utils.Constants.*;

/**
 * Created by Ivan on 02.05.2017.
 */
public class Menu {
    private String name;
    private LinkedHashMap<String, Runnable> commandMap;

    public Menu(String name) {
        this.name = name;
        commandMap = new LinkedHashMap<>();
    }

    public void putCommand(String name, Runnable command) {
        commandMap.put(name, command);
    }

    public void executeCommand(int commandNumber) {
        int realCommandNumber = commandNumber - 1;
        if(realCommandNumber < 0 || realCommandNumber >= commandMap.size()) {
            System.out.println("Fail on command: " + commandNumber);
        } else {
            List<Runnable> commands = new ArrayList<>(commandMap.values());
            commands.get(realCommandNumber).run();
        }
    }

    public String showMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n"+this.name).append(":\n");
        List<String> commandNames = new ArrayList<>(commandMap.keySet());
        for(int i = 0; i < commandNames.size(); i++) {
            sb.append(String.format(" %d: %s%n",i+1,commandNames.get(i)));
        }
        return sb.toString();
    }
}
