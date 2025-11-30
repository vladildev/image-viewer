package ca.ets.log121.labo5.imageviewer.model.memento;

import ca.ets.log121.labo5.imageviewer.model.observer.Perspective;

public class EditorMemento implements Memento {
    private Perspective perspective;

    public EditorMemento(Perspective perspective) {
        this.perspective = perspective;
    }

    public Perspective getPerspective() {
        return perspective;
    }
}
