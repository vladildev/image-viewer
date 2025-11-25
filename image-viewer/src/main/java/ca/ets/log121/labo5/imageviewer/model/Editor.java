package ca.ets.log121.labo5.imageviewer.model;

import ca.ets.log121.labo5.imageviewer.model.memento.EditorMemento;
import ca.ets.log121.labo5.imageviewer.model.memento.Memento;
import ca.ets.log121.labo5.imageviewer.model.observer.Image;
import ca.ets.log121.labo5.imageviewer.model.observer.Perspective;

public class Editor {
    private Perspective perspective;
    private Image image;

    public Editor(Image image, Perspective perspective) {
        this.perspective = perspective;
        this.image = image;
    }

    public Perspective getPerspective() {
        return perspective;
    }
    public Image getImage() {
        return image;
    }

    private Editor copyEditor() {
        return new Editor(image, perspective);
    }

    public Memento createEditorMemento() {
        return new EditorMemento(copyEditor());
    }
}
