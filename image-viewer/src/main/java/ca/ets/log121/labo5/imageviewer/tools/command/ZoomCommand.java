package ca.ets.log121.labo5.imageviewer.tools.command;

public class ZoomCommand implements Command {
    private final double zoomFactor;

    public ZoomCommand(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    @Override
    public boolean execute() {
        Manager.getInstance().getEditor().getPerspective().zoom(zoomFactor);
        return true;
    }

    @Override
    public void undo() {
        Manager.getInstance().getEditor().getPerspective().zoom(1 / zoomFactor);
    }
}
