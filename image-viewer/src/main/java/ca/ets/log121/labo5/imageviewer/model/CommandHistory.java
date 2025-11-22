package ca.ets.log121.labo5.imageviewer.model;

import ca.ets.log121.labo5.imageviewer.tools.command.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {
    private List<Command> history = new ArrayList<Command>();

    public void addMemento(Command command) {
        history.add(command);
    }

    public boolean isEmpty() {
        return history.isEmpty();
    }
}
