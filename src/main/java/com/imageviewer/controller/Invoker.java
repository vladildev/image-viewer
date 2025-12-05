package com.imageviewer.controller;
import com.imageviewer.tools.command.*;
import com.imageviewer.model.Manager;

/**
 * Invocateur de commandes implémentant le patron Singleton.
 * <p>
 * Cette classe fait partie du patron Command et est responsable de l'exécution
 * des commandes. Elle centralise l'exécution de toutes les commandes de l'application
 * et gère l'ajout des commandes à l'historique pour permettre les opérations
 * d'annulation (Undo) et de rétablissement (Redo).
 * </p>
 * <p>
 * Le patron Singleton garantit qu'une seule instance de l'Invoker existe
 * dans l'application.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Command
 * @see Manager
 */
public class Invoker {
    /** L'instance unique de l'Invoker (patron Singleton). */
    private static Invoker instance;
    
    /**
     * Constructeur privé pour empêcher l'instanciation externe.
     * <p>
     * Ce constructeur est privé pour implémenter le patron Singleton.
     * </p>
     */
    private Invoker() {
    }
    
    /**
     * Retourne l'instance unique de l'Invoker.
     * <p>
     * Si l'instance n'existe pas encore, elle est créée.
     * </p>
     * 
     * @return l'instance unique de l'Invoker
     */
    public static Invoker getInstance() {
        if (instance == null) {
            instance = new Invoker();
        }
        return instance;
    }

    /**
     * Exécute une commande et l'ajoute à l'historique si l'exécution réussit.
     * <p>
     * Si la méthode {@link Command#execute()} retourne {@code true},
     * la commande est ajoutée à l'historique des commandes pour permettre
     * les opérations Undo/Redo.
     * </p>
     * 
     * @param command la commande à exécuter
     */
    public void executeCommand(Command command) {
        if (command.execute()) {
            Manager.getInstance().getCommandHistory().addCommand(command);
        }
    }
}
