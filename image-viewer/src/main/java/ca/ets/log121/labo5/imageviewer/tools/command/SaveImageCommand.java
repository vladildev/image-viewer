package ca.ets.log121.labo5.imageviewer.tools.command;

import ca.ets.log121.labo5.imageviewer.model.Manager;

public class SaveImageCommand implements Command {
    private String imagePath;

    public SaveImageCommand(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean execute() {
        Manager.getInstance().saveImageToFile(imagePath);
        return false;
    }

    public void undo() {
        // Implementation for undoing the save image action
    }
}
