package ca.ets.log121.labo5.imageviewer.model.observer;

import java.util.ArrayList;

import ca.ets.log121.labo5.imageviewer.model.Manager;

/**
 * Représente une image dans l'application.
 * <p>
 * Cette classe implémente le patron Observer en tant qu'objet observable.
 * Elle stocke les informations de l'image (chemin, dimensions) et notifie
 * ses observateurs lorsque l'image change.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Observable
 * @see Observer
 */
public class Image implements Observable {
    /** La hauteur de l'image en pixels. */
    private int height, width;
    /** Liste des observateurs attachés. */
    private ArrayList<Observer> observers;
    /** Le chemin du fichier image. */
    private String path;

    /**
     * Constructeur de l'image.
     * 
     * @param height la hauteur de l'image en pixels
     * @param width la largeur de l'image en pixels
     */
    public Image(int height, int width) {
        this.height = height;
        this.width = width;
        this.observers = new ArrayList<Observer>();
    }


    // ========== Observable methods ==========

    /**
     * {@inheritDoc}
     */
    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(this);
        }
    }

    // =========== Core Methods ===========

    /**
     * Importe une image depuis un fichier.
     * <p>
     * Cette méthode charge l'image pour obtenir ses dimensions réelles,
     * met à jour la perspective et notifie les observateurs.
     * </p>
     * 
     * @param path le chemin du fichier image à importer
     */
    public void importImage(String path) {
        this.path = path;
        
        // Charger l'image pour obtenir ses dimensions réelles
        try {
            javafx.scene.image.Image fxImage = new javafx.scene.image.Image("file:" + path);
            this.width = (int) fxImage.getWidth();
            this.height = (int) fxImage.getHeight();
            System.out.println("Image imported: " + path + " (" + width + "x" + height + ")");
            
            // Mettre à jour la perspective avec les dimensions de l'image
            updatePerspective();
        } catch (Exception e) {
            System.err.println("Error loading image dimensions: " + e.getMessage());
        }
        
        notifyObservers();
    }
    
    /**
     * Met à jour la perspective avec les dimensions de l'image.
     * <p>
     * Notifie le Manager pour mettre à jour la perspective
     * avec les nouvelles dimensions de l'image.
     * </p>
     */
    private void updatePerspective() {
        // Notifier le Manager pour mettre à jour la perspective
        Manager.getInstance().getEditor().getPerspective().updatePerspectiveDimensions(this.width, this.height);
    }



    // ========== Getters and Setters ==========

    /**
     * Retourne la hauteur de l'image.
     * 
     * @return la hauteur en pixels
     */
    public int getHeight() {
        return height;
    }

    /**
     * Retourne la largeur de l'image.
     * 
     * @return la largeur en pixels
     */
    public int getWidth() {
        return width;
    }

    /**
     * Retourne le chemin du fichier image.
     * 
     * @return le chemin du fichier
     */
    public String getPath() {
        return path;
    }
}
