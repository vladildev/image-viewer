package ca.ets.log121.labo5.imageviewer.tools.iterator;

import java.util.List;

import ca.ets.log121.labo5.imageviewer.tools.command.Command;

public class CommandHistoryIterator implements Iterator {
    private List<Command> history;
    private int index;

    public CommandHistoryIterator(List<Command> history) {
        this.history = history;
        this.index = 0;
    }
    @Override
    public Object next() {
        if (hasNext()) {
            return history.get(index++);
        }
        return null;
    }
    @Override
    public Object previous() {
        if (hasPrevious()) {
            return history.get(--index);
        }
        return null;
    }
    @Override
    public boolean hasNext() {
        return index < history.size();
    }
    @Override
    public boolean hasPrevious() {
        return index > 0;
    }
    @Override
    public int getIndex() {
        return index;
    }
    public void resetIndex(){
        this.index = 0;
    }
}
