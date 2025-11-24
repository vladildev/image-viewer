package ca.ets.log121.labo5.imageviewer.model;


import ca.ets.log121.labo5.imageviewer.model.memento.Memento;
import ca.ets.log121.labo5.imageviewer.model.memento.MementoHistory;
import ca.ets.log121.labo5.imageviewer.model.observer.Image;
import ca.ets.log121.labo5.imageviewer.model.observer.Perspective;

public class Manager {
    private Editor editor;
    //private CommandHistory commandHistory;
    private MementoHistory mementoHistory;

    public Manager(Image image, Perspective perspective) {
        this.editor = new Editor(image, perspective);
        //this.commandHistory = new CommandHistory();
        this.mementoHistory = new MementoHistory();
    }

    public void createMemento() {
        Memento memento = editor.createEditorMemento();
        mementoHistory.addMemento(memento);
    }
}
