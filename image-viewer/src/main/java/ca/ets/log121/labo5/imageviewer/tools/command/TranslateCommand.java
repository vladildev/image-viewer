package ca.ets.log121.labo5.imageviewer.tools.command;

public class TranslateCommand implements Command {
    private final int deltaX;
    private final int deltaY;

    public TranslateCommand(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    @Override
    public boolean execute() {
        Manager.getInstance().getEditor().getPerspective().translate(deltaX, deltaY);
        return true;
    }

    @Override
    public void undo() {
        Manager.getInstance().getEditor().getPerspective().translate(-deltaX, -deltaY);
    }
}
