package ca.ets.log121.labo5.imageviewer.model.memento;

import ca.ets.log121.labo5.imageviewer.model.observer.Perspective;
import java.time.LocalDateTime;

public class EditorMemento implements Memento {
    private Perspective perspective;
    private LocalDateTime createdAt;

    public EditorMemento(Perspective perspective, LocalDateTime createdAt) {
        this.perspective = perspective;
        this.createdAt = createdAt;
    }

    public Perspective getPerspective() {
        return perspective;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
