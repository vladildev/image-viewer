package ca.ets.log121.labo5.imageviewer.tools.command;

import ca.ets.log121.labo5.imageviewer.model.Manager;

/**
 * Commande pour sauvegarder la configuration dans un fichier.
 * <p>
 * Cette commande sauvegarde l'état actuel de la perspective (zoom et position)
 * dans un fichier JSON. Cette commande n'est pas ajoutée à l'historique.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Command
 */
public class SaveConfigFileCommand implements Command
{
    /** Le chemin du fichier de destination. */
    private String configFilePath;

    /**
     * Constructeur de la commande de sauvegarde de configuration.
     * 
     * @param configFilePath le chemin du fichier de destination
     */
    public SaveConfigFileCommand(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Sauvegarde la configuration dans le fichier spécifié.
     * </p>
     * 
     * @return {@code false} car cette commande n'est pas ajoutée à l'historique
     */
    public boolean execute() {
        Manager.getInstance().saveConfigToFile(configFilePath);
        return false;
    }

    /**
     * {@inheritDoc}
     * <p>
     * L'annulation de la sauvegarde n'est pas implémentée.
     * </p>
     */
    public void undo() {
        // Implementation for undoing the save config file action
    }
}