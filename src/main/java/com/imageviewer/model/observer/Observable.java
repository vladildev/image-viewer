package com.imageviewer.model.observer;

/**
 * Interface pour les objets observables dans le patron Observer.
 * <p>
 * Cette interface définit le contrat pour les objets qui peuvent être
 * observés par des observateurs. Les objets observables maintiennent
 * une liste d'observateurs et les notifient lorsque leur état change.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Observer
 */
public interface Observable {
    /**
     * Attache un observateur à cet objet observable.
     * <p>
     * L'observateur sera notifié lors des changements d'état.
     * </p>
     * 
     * @param o l'observateur à attacher
     */
    void attach(Observer o);

    /**
     * Détache un observateur de cet objet observable.
     * <p>
     * L'observateur ne sera plus notifié lors des changements d'état.
     * </p>
     * 
     * @param o l'observateur à détacher
     */
    void detach(Observer o);

    /**
     * Notifie tous les observateurs attachés d'un changement d'état.
     * <p>
     * Cette méthode appelle la méthode {@link Observer#update(Observable)}
     * de chaque observateur attaché.
     * </p>
     */
    void notifyObservers();
}
