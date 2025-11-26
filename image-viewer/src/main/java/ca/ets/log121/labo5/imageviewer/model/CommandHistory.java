package ca.ets.log121.labo5.imageviewer.model;

import ca.ets.log121.labo5.imageviewer.tools.command.Command;
import ca.ets.log121.labo5.imageviewer.tools.iterator.CommandHistoryIterator;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {
    private List<Command> history;
    private CommandHistoryIterator iterator;
    
    public CommandHistory(){
        history = new ArrayList<Command>();
        iterator = new CommandHistoryIterator(history);
    }
    
    public void addCommand(Command command) {
        System.out.println(">>> addCommand: " + command.getClass().getSimpleName() + " - canRedo: " + canRedo() + " - index: " + iterator.getIndex());
        if (canRedo()){
            // Delete everything that is after the current index
            // Use subList(0, index).clear() doesnt work well, keep the new list creation
            List<Command> newHistory = new ArrayList<>(history.subList(0, iterator.getIndex()));
            history.clear();
            history.addAll(newHistory);
            iterator.resetIndex();
        }
        history.add(0, command);
    }

    public boolean isEmpty() {
        return history.isEmpty();
    }

    public boolean canUndo() {
        return iterator.hasPrevious();
    }

    public boolean canRedo() {
        return iterator.hasNext();
    }
    
    public CommandHistoryIterator getIterator() {
        return iterator;
    }
}
