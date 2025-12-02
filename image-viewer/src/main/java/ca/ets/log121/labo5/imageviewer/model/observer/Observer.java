package ca.ets.log121.labo5.imageviewer.model.observer;

/**
 * Interface pour les observateurs dans le patron Observer.
 * <p>
 * Cette interface définit le contrat pour les objets qui observent
 * des objets observables. Lorsqu'un objet observé change d'état,
 * la méthode {@link #update(Observable)} est appelée automatiquement.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Observable
 */
public interface Observer {
    /**
     * Méthode appelée lorsqu'un objet observé change d'état.
     * <p>
     * Cette méthode est appelée automatiquement par l'objet observable
     * lorsqu'il notifie ses observateurs.
     * </p>
     * 
     * @param o l'objet observable qui a changé d'état
     */
    void update(Observable o);
}
