package ca.ets.log121.labo5.imageviewer.tools.command;

public interface Command {
    boolean execute();
    void undo();
}
