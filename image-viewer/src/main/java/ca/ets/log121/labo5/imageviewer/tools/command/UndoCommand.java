package ca.ets.log121.labo5.imageviewer.tools.command;
import ca.ets.log121.labo5.imageviewer.model.Manager;

public class UndoCommand implements Command {
    @Override
    public boolean execute() {
        Manager.getInstance().undo();
        return false;
    }

    @Override
    public void undo() {
        // No undo for undo
    }
}
