package ca.ets.log121.labo5.imageviewer.tools.command;

public class UndoCommand implements Command {
    @Override
    public boolean execute() {
        Manager.getInstance().getEditor().undo();
        return false;
    }

    @Override
    public void undo() {
        // No undo for undo
    }
}
