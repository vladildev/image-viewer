package ca.ets.log121.labo5.imageviewer.model;


import ca.ets.log121.labo5.imageviewer.model.memento.Memento;
import ca.ets.log121.labo5.imageviewer.model.memento.MementoHistory;
import ca.ets.log121.labo5.imageviewer.model.observer.Image;
import ca.ets.log121.labo5.imageviewer.model.observer.Perspective;
import ca.ets.log121.labo5.imageviewer.tools.command.Command;

public class Manager {
    private static Manager instance;
    private Editor editor;
    private MementoHistory mementoHistory;
    private CommandHistory commandHistory;

    private Manager() {
        Image image = new Image(3000, 3000);
        Perspective perspective = new Perspective(3000, 3000, 0, 0);
        this.editor = new Editor(image, perspective);
        this.mementoHistory = new MementoHistory();
        this.commandHistory = new CommandHistory();
    }


    // =========== GETTERS ===========

    public static Manager getInstance() {
        if (instance == null) {
            instance = new Manager();
        }
        return instance;
    }
    public Editor getEditor() {
        return editor;
    }
    public CommandHistory getCommandHistory() {
        return commandHistory;
    }



    // =========== METHODS ===========

    public void undo() {
        if(commandHistory.canUndo()){
            System.out.println("UNDO - Index avant: " + commandHistory.getIterator().getIndex());
            Command command = commandHistory.getIterator().previous();
            System.out.println("UNDO - Index après: " + commandHistory.getIterator().getIndex() + " - Command: " + command.getClass().getSimpleName());
            command.undo();
        }
    }
    public void redo() {
        if(commandHistory.canRedo()){
            System.out.println("REDO - Index avant: " + commandHistory.getIterator().getIndex());
            Command command = commandHistory.getIterator().next();
            System.out.println("REDO - Index après: " + commandHistory.getIterator().getIndex() + " - Command: " + command.getClass().getSimpleName());
            command.execute();
        }
    }
    public void restoreConfigFromFile(String filePath) {
        // Implementation for restoring configuration from file
    }
    public void saveConfigToFile(String filePath) {
        // Implementation for saving configuration to file
    }
    public void saveImageToFile(String filePath) {
        // Implementation for saving image to file
    }

    public void createMemento() {
        Memento memento = editor.createEditorMemento();
        mementoHistory.addMemento(memento);
    }
}
