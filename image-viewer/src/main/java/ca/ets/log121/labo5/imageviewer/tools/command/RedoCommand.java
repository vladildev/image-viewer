package ca.ets.log121.labo5.imageviewer.tools.command;
import ca.ets.log121.labo5.imageviewer.model.Manager;

public class RedoCommand implements Command {
    @Override
    public boolean execute() {
        Manager.getInstance().redo();
        return false;
    }

    @Override
    public void undo() {
        // No undo for redo
    }
}
