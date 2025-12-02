package ca.ets.log121.labo5.imageviewer.model;

import ca.ets.log121.labo5.imageviewer.model.memento.EditorMemento;
import ca.ets.log121.labo5.imageviewer.model.memento.Memento;
import ca.ets.log121.labo5.imageviewer.model.observer.Image;
import ca.ets.log121.labo5.imageviewer.model.observer.Perspective;

/**
 * Représente l'éditeur d'images de l'application.
 * <p>
 * Cette classe contient les données principales de l'édition :
 * l'image en cours d'édition et sa perspective (zoom et position).
 * Elle peut créer des mementos pour sauvegarder son état actuel.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Image
 * @see Perspective
 * @see Memento
 */
public class Editor {
    /** La perspective actuelle (zoom et position de la zone visible). */
    private Perspective perspective;
    /** L'image en cours d'édition. */
    private Image image;

    /**
     * Constructeur de l'éditeur.
     * 
     * @param image l'image à éditer
     * @param perspective la perspective initiale
     */
    public Editor(Image image, Perspective perspective) {
        this.perspective = perspective;
        this.image = image;
    }

    /**
     * Retourne la perspective actuelle.
     * 
     * @return la perspective (zoom et position)
     */
    public Perspective getPerspective() {
        return perspective;
    }

    /**
     * Retourne l'image en cours d'édition.
     * 
     * @return l'image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Crée une copie de cet éditeur.
     * 
     * @return une nouvelle instance d'Editor avec les mêmes données
     */
    private Editor copyEditor() {
        return new Editor(image, perspective);
    }

    /**
     * Crée un memento de la configuration actuelle de l'éditeur.
     * <p>
     * Le memento sauvegarde l'état de la perspective (dimensions et position)
     * ainsi que la date de création.
     * </p>
     * 
     * @return un nouveau memento contenant l'état actuel de la perspective
     */
    public Memento createConfigEditorMemento() {
        return new EditorMemento(new Perspective(this.perspective.getHeight(), this.perspective.getWidth(),
            this.perspective.getX(), this.perspective.getY()), java.time.LocalDateTime.now());
    }
}
