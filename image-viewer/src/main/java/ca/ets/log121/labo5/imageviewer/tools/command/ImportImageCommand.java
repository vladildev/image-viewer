package ca.ets.log121.labo5.imageviewer.tools.command;

import ca.ets.log121.labo5.imageviewer.model.Manager;

public class ImportImageCommand implements Command {
    private String imagePath;

    public ImportImageCommand(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean execute() {
        // Implementation for importing an image
        Manager.getInstance().getEditor().getImage().importImage(imagePath);
        return false;
    }
    public void undo() {
        // Implementation for undoing the import image action
    }
}
