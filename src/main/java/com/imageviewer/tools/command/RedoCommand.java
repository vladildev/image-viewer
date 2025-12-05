package com.imageviewer.tools.command;
import com.imageviewer.model.Manager;

/**
 * Commande pour rétablir la dernière action annulée.
 * <p>
 * Cette commande permet de réexécuter une action précédemment annulée
 * par la commande Undo. Cette commande n'est pas elle-même ajoutée à
 * l'historique.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Command
 * @see UndoCommand
 */
public class RedoCommand implements Command {
    /**
     * {@inheritDoc}
     * <p>
     * Rétablit la dernière action annulée.
     * </p>
     * 
     * @return {@code false} car cette commande n'est pas ajoutée à l'historique
     */
    @Override
    public boolean execute() {
        Manager.getInstance().redo();
        return false;
    }

    /**
     * {@inheritDoc}
     * <p>
     * L'annulation du Redo n'est pas implémentée.
     * </p>
     */
    @Override
    public void undo() {
        // No undo for redo
    }
}
