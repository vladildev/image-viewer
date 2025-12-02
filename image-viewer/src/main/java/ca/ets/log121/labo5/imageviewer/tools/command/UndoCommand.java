package ca.ets.log121.labo5.imageviewer.tools.command;
import ca.ets.log121.labo5.imageviewer.model.Manager;

/**
 * Commande pour annuler la dernière action.
 * <p>
 * Cette commande permet d'annuler l'effet de la dernière commande exécutée.
 * Cette commande n'est pas elle-même ajoutée à l'historique.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Command
 * @see RedoCommand
 */
public class UndoCommand implements Command {
    /**
     * {@inheritDoc}
     * <p>
     * Annule la dernière action exécutée.
     * </p>
     * 
     * @return {@code false} car cette commande n'est pas ajoutée à l'historique
     */
    @Override
    public boolean execute() {
        Manager.getInstance().undo();
        return false;
    }

    /**
     * {@inheritDoc}
     * <p>
     * L'annulation du Undo n'est pas implémentée.
     * </p>
     */
    @Override
    public void undo() {
        // No undo for undo
    }
}
