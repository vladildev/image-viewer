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
        if (canRedo()){
            history = history.subList(iterator.getIndex(), history.size());
            iterator.resetIndex();
        }
        history.add(command);
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
