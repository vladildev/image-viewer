package ca.ets.log121.labo5.imageviewer.controller;
import ca.ets.log121.labo5.imageviewer.tools.command.*;

public class Invoker {
    private static Invoker instance;
    
    private Invoker() {
    }
    
    public static Invoker getInstance() {
        if (instance == null) {
            instance = new Invoker();
        }
        return instance;
    }

    public void executeCommand(Command command) {
        if (command.execute()) {
            Manager.getInstance().getCommandHistory().addCommand(command);
        }
    }
}
