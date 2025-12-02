package ca.ets.log121.labo5.imageviewer.tools.iterator;

import java.util.List;

import ca.ets.log121.labo5.imageviewer.tools.command.Command;

/**
 * Itérateur pour parcourir l'historique des commandes.
 * <p>
 * Cette classe implémente le patron Iterator et permet de parcourir
 * l'historique des commandes dans les deux sens pour les opérations
 * d'annulation (Undo) et de rétablissement (Redo).
 * </p>
 * <p>
 * L'historique est stocké avec les commandes les plus récentes en premier
 * (index 0). L'index de l'itérateur pointe vers la prochaine commande
 * à annuler.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Iterator
 * @see Command
 */
public class CommandHistoryIterator implements Iterator {
    /** Référence vers la liste de l'historique des commandes. */
    private List<Command> history;
    /** Index actuel dans l'historique. */
    private int index;

    /**
     * Constructeur de l'itérateur d'historique de commandes.
     * 
     * @param history la liste de commandes à parcourir
     */
    public CommandHistoryIterator(List<Command> history) {
        this.history = history;
        this.index = 0;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retourne la commande suivante (pour Redo) et décrémente l'index.
     * </p>
     * 
     * @return la commande suivante, ou {@code null} s'il n'y en a pas
     */
    @Override
    public Command next() {
        if (hasNext()) {
            return history.get(--index);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retourne la commande précédente (pour Undo) et incrémente l'index.
     * </p>
     * 
     * @return la commande précédente, ou {@code null} s'il n'y en a pas
     */
    @Override
    public Command previous() {
        if (hasPrevious()) {
            return history.get(index++);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Vérifie s'il existe des commandes à annuler.
     * </p>
     */
    @Override
    public boolean hasPrevious() {
        return index < history.size();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Vérifie s'il existe des commandes annulées à rétablir.
     * </p>
     */
    @Override
    public boolean hasNext() {
        return index > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIndex() {
        return index;
    }

    /**
     * Réinitialise l'index de l'itérateur à 0.
     * <p>
     * Utilisé lorsqu'une nouvelle commande est ajoutée après des annulations.
     * </p>
     */
    public void resetIndex(){
        this.index = 0;
    }
}
