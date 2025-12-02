package ca.ets.log121.labo5.imageviewer.tools.command;

/**
 * Interface pour le patron Command.
 * <p>
 * Cette interface définit le contrat pour toutes les commandes de l'application.
 * Chaque commande encapsule une action qui peut être exécutée et annulée.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 */
public interface Command {
    /**
     * Exécute la commande.
     * 
     * @return {@code true} si la commande doit être ajoutée à l'historique
     *         pour permettre l'annulation, {@code false} sinon
     */
    boolean execute();

    /**
     * Annule l'effet de la commande.
     * <p>
     * Cette méthode restaure l'état précédent à l'exécution de la commande.
     * </p>
     */
    void undo();
}
