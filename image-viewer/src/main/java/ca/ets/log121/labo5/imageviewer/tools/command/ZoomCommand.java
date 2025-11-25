package ca.ets.log121.labo5.imageviewer.tools.command;
import ca.ets.log121.labo5.imageviewer.model.Manager;

public class ZoomCommand implements Command {
    private final double zoomFactor;

    public ZoomCommand(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    @Override
    public boolean execute() {
        System.out.println(">>> ZoomCommand.execute() called with factor: " + zoomFactor);
        Manager.getInstance().getEditor().getPerspective().zoom(zoomFactor);
        return true;
    }

    @Override
    public void undo() {
        System.out.println(">>> ZoomCommand.undo() called with factor: " + (1/zoomFactor));
        Manager.getInstance().getEditor().getPerspective().zoom(1 / zoomFactor);
    }
}
