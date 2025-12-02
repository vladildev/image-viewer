package ca.ets.log121.labo5.imageviewer.model.memento;


import java.util.ArrayList;
import java.util.List;

/**
 * Historique des mementos sauvegardés.
 * <p>
 * Cette classe stocke tous les mementos créés par l'utilisateur
 * pour permettre la restauration de configurations précédentes.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Memento
 */
public class MementoHistory {
    /** Liste des mementos sauvegardés. */
    private List<Memento> history = new ArrayList<Memento>();

    /**
     * Ajoute un memento à l'historique.
     * 
     * @param memento le memento à ajouter
     */
    public void addMemento(Memento memento) {
        history.add(memento);
    }

    /**
     * Vérifie si l'historique est vide.
     * 
     * @return {@code true} si l'historique ne contient aucun memento
     */
    public boolean isEmpty() {
        return history.isEmpty();
    }

    /**
     * Retourne une copie des mementos stockés.
     * <p>
     * La copie empêche la modification externe de la liste interne.
     * </p>
     * 
     * @return une nouvelle liste contenant les mementos sauvegardés
     */
    public java.util.List<Memento> getHistory() {
        return new java.util.ArrayList<Memento>(history);
    }
}
