package com.imageviewer.model.observer;

import java.util.ArrayList;

import com.imageviewer.model.Manager;

/**
 * Représente la perspective de visualisation de l'image.
 * <p>
 * Cette classe définit la zone visible de l'image (dimensions et position)
 * et implémente le patron Observer en tant qu'objet observable.
 * Elle permet les opérations de zoom et de translation sur la zone visible.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Observable
 * @see Observer
 */
public class Perspective implements Observable {
    /** La hauteur de la zone visible en pixels. */
    private int height, width;
    /** La position X et Y du centre de la zone visible. */
    private int x, y;
    /** Liste des observateurs attachés. */
    private ArrayList<Observer> observers;

    /**
     * Constructeur de la perspective.
     * 
     * @param height la hauteur de la zone visible en pixels
     * @param width la largeur de la zone visible en pixels
     * @param x la position X du centre de la zone visible
     * @param y la position Y du centre de la zone visible
     */
    public Perspective(int height, int width, int x, int y) {
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
        this.observers = new ArrayList<Observer>();
    }

    // =============== Observable methods ================

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

    // ================== Getters and Setters ==================

    /**
     * Retourne la hauteur de la zone visible.
     * 
     * @return la hauteur en pixels
     */
    public int getHeight() {
        return height;
    }

    /**
     * Retourne la largeur de la zone visible.
     * 
     * @return la largeur en pixels
     */
    public int getWidth() {
        return width;
    }

    /**
     * Retourne la position X du centre de la zone visible.
     * 
     * @return la position X
     */
    public int getX() {
        return x;
    }

    /**
     * Retourne la position Y du centre de la zone visible.
     * 
     * @return la position Y
     */
    public int getY() {
        return y;
    }
    
    /**
     * Définit la largeur de la zone visible.
     * 
     * @param width la nouvelle largeur en pixels
     */
    public void setWidth(int width) {
        this.width = width;
    }
    
    /**
     * Définit la hauteur de la zone visible.
     * 
     * @param height la nouvelle hauteur en pixels
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    /**
     * Définit la position X du centre de la zone visible.
     * 
     * @param x la nouvelle position X
     */
    public void setX(int x) {
        this.x = x;
    }
    
    /**
     * Définit la position Y du centre de la zone visible.
     * 
     * @param y la nouvelle position Y
     */
    public void setY(int y) {
        this.y = y;
    }


    //  ==================== Core Methods ====================

    // possibilité de faire fune fonc crop() ici
    
    /**
     * Met à jour les dimensions de la perspective.
     * <p>
     * Réinitialise la position à (0, 0) et notifie les observateurs.
     * </p>
     * 
     * @param width la nouvelle largeur en pixels
     * @param height la nouvelle hauteur en pixels
     */
    public void updatePerspectiveDimensions(int width, int height) {
        this.setWidth(width);
        this.setHeight(height);
        this.setX(0);
        this.setY(0);
        notifyObservers();
    }

    /**
     * Applique un zoom sur la zone visible.
     * <p>
     * Multiplie les dimensions actuelles par le facteur de zoom
     * et notifie les observateurs du changement.
     * </p>
     * 
     * @param facteur le facteur de zoom (supérieur à 1 pour agrandir, inférieur à 1 pour réduire)
     */
    public void zoom(double facteur){
        this.width *= facteur;
        this.height *= facteur;
        notifyObservers();
        System.out.println(">>> Perspective.zoom() - new size: " + width + "x" + height + " - observers: " + observers.size());
        
    }

    /**
     * Applique une translation sur la zone visible.
     * <p>
     * Déplace le centre de la zone visible selon les valeurs delta
     * et notifie les observateurs du changement.
     * </p>
     * 
     * @param deltaX le déplacement horizontal (positif vers la droite)
     * @param deltaY le déplacement vertical (positif vers le bas)
     */
    public void translate(int deltaX, int deltaY){
        this.x += deltaX;
        this.y += deltaY;
        notifyObservers();
    }
}
