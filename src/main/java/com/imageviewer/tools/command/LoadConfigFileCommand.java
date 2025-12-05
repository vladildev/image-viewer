package com.imageviewer.tools.command;

import com.imageviewer.model.Manager;

/**
 * Commande pour charger une configuration depuis un fichier.
 * <p>
 * Cette commande restaure l'état de la perspective (zoom et position)
 * depuis un fichier JSON. Cette commande n'est pas ajoutée à l'historique
 * car le chargement n'est pas annulable.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Command
 */
public class LoadConfigFileCommand implements Command {
    /** Le chemin du fichier de configuration à charger. */
    private String configFilePath;

    /**
     * Constructeur de la commande de chargement de configuration.
     * 
     * @param configFilePath le chemin du fichier de configuration à charger
     */
    public LoadConfigFileCommand(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Charge la configuration depuis le fichier spécifié.
     * </p>
     * 
     * @return {@code false} car cette commande n'est pas ajoutée à l'historique
     */
    public boolean execute() {
        // Implementation for executing the load config file action
        Manager.getInstance().restoreConfigFromFile(configFilePath);
        return false;
    }

    /**
     * {@inheritDoc}
     * <p>
     * L'annulation du chargement n'est pas implémentée.
     * </p>
     */
    public void undo() {
        // Implementation for undoing the load config file action
    }
}
