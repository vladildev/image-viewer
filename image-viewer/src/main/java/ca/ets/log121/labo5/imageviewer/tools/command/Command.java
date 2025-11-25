package ca.ets.log121.labo5.imageviewer.tools.command;

public interface Command {
    public boolean execute();
    public void undo();
}
