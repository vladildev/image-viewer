package ca.ets.log121.labo5.imageviewer.model.memento;

import ca.ets.log121.labo5.imageviewer.model.Editor;

public class EditorMemento implements Memento {
    private Editor editor;

    public EditorMemento(Editor editor) {
        this.editor = editor;
    }
}
