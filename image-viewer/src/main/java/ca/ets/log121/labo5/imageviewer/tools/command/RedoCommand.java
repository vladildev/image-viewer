package ca.ets.log121.labo5.imageviewer.tools.command;

public class RedoCommand implements Command {
    @Override
    public boolean execute() {
        Manager.getInstance().getEditor().redo();
        return true;
    }

    @Override
    public void undo() {
        // No undo for redo
    }
}
