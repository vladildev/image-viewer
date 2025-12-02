package ca.ets.log121.labo5.imageviewer.model;

import ca.ets.log121.labo5.imageviewer.tools.command.Command;
import ca.ets.log121.labo5.imageviewer.tools.iterator.CommandHistoryIterator;

import java.util.ArrayList;
import java.util.List;

/**
 * Historique des commandes exécutées dans l'application.
 * <p>
 * Cette classe stocke toutes les commandes exécutées et permet de naviguer
 * dans l'historique pour les opérations d'annulation (Undo) et de
 * rétablissement (Redo). Elle utilise un itérateur pour parcourir
 * l'historique des commandes.
 * </p>
 * <p>
 * Lorsqu'une nouvelle commande est ajoutée alors que des commandes ont été
 * annulées, les commandes annulées sont supprimées de l'historique.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Command
 * @see CommandHistoryIterator
 */
public class CommandHistory {
    /** Liste des commandes dans l'historique. */
    private List<Command> history;
    /** Itérateur pour parcourir l'historique. */
    private CommandHistoryIterator iterator;
    
    /**
     * Constructeur de l'historique des commandes.
     * <p>
     * Initialise une liste vide et crée un itérateur associé.
     * </p>
     */
    public CommandHistory(){
        history = new ArrayList<Command>();
        iterator = new CommandHistoryIterator(history);
    }
    
    /**
     * Ajoute une commande à l'historique.
     * <p>
     * Si des commandes ont été annulées (canRedo() retourne true),
     * toutes les commandes après l'index courant sont supprimées
     * avant d'ajouter la nouvelle commande.
     * </p>
     * 
     * @param command la commande à ajouter à l'historique
     */
    public void addCommand(Command command) {
        System.out.println(">>> addCommand: " + command.getClass().getSimpleName() + " - canRedo: " + canRedo() + " - index: " + iterator.getIndex());
        if (canRedo()){
            // Delete everything that is after the current index
            // Use subList(0, index).clear() doesnt work well, keep the new list creation
            List<Command> newHistory = new ArrayList<>(history.subList(0, iterator.getIndex()));
            history.clear();
            history.addAll(newHistory);
            iterator.resetIndex();
        }
        history.add(0, command);
    }

    /**
     * Vérifie si l'historique est vide.
     * 
     * @return {@code true} si l'historique ne contient aucune commande
     */
    public boolean isEmpty() {
        return history.isEmpty();
    }

    /**
     * Vérifie si une opération d'annulation (Undo) est possible.
     * 
     * @return {@code true} s'il y a des commandes à annuler
     */
    public boolean canUndo() {
        return iterator.hasPrevious();
    }

    /**
     * Vérifie si une opération de rétablissement (Redo) est possible.
     * 
     * @return {@code true} s'il y a des commandes annulées à rétablir
     */
    public boolean canRedo() {
        return iterator.hasNext();
    }
    
    /**
     * Retourne l'itérateur de l'historique des commandes.
     * 
     * @return l'itérateur permettant de parcourir l'historique
     */
    public CommandHistoryIterator getIterator() {
        return iterator;
    }
}
