package ca.ets.log121.labo5.imageviewer.model;


import ca.ets.log121.labo5.imageviewer.model.memento.Memento;
import ca.ets.log121.labo5.imageviewer.model.memento.MementoHistory;
import ca.ets.log121.labo5.imageviewer.model.observer.Image;
import ca.ets.log121.labo5.imageviewer.model.observer.Perspective;
import ca.ets.log121.labo5.imageviewer.tools.command.Command;

public class Manager {
    private static Manager instance;
    private Editor editor;
    //private CommandHistory commandHistory;
    private MementoHistory mementoHistory;
    private CommandHistory commandHistory;

    private Manager() {
        Image image = new Image(800, 600);
        Perspective perspective = new Perspective(800, 600, 0, 0);
        this.editor = new Editor(image, perspective);
        //this.commandHistory = new CommandHistory();
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



    // =========== METHODS ===========

    public void undo() {
        if(commandHistory.canUndo()){
            Command command = commandHistory.getIterator().previous();
            command.undo();
        }
    }
    public void redo() {
        if(commandHistory.canRedo()){
            Command command = commandHistory.getIterator().next();
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
