package ca.ets.log121.labo5.imageviewer.model.memento;

import ca.ets.log121.labo5.imageviewer.model.observer.Perspective;
import java.time.LocalDateTime;

/**
 * Memento pour sauvegarder l'état de l'éditeur.
 * <p>
 * Cette classe implémente le patron Memento et stocke l'état
 * de la perspective (dimensions et position de la zone visible)
 * ainsi que la date de création du memento.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Memento
 * @see Perspective
 */
public class EditorMemento implements Memento {
    /** La perspective sauvegardée. */
    private Perspective perspective;
    /** La date et heure de création du memento. */
    private LocalDateTime createdAt;

    /**
     * Constructeur du memento de l'éditeur.
     * 
     * @param perspective la perspective à sauvegarder
     * @param createdAt la date et heure de création
     */
    public EditorMemento(Perspective perspective, LocalDateTime createdAt) {
        this.perspective = perspective;
        this.createdAt = createdAt;
    }

    /**
     * Retourne la perspective sauvegardée.
     * 
     * @return la perspective
     */
    public Perspective getPerspective() {
        return perspective;
    }

    /**
     * Retourne la date et heure de création du memento.
     * 
     * @return la date et heure de création
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
