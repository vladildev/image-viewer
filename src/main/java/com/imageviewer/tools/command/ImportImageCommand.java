package com.imageviewer.tools.command;

import com.imageviewer.model.Manager;

/**
 * Commande pour importer une image.
 * <p>
 * Cette commande charge une image depuis un fichier et l'affiche
 * dans l'éditeur. Cette commande n'est pas ajoutée à l'historique
 * car l'importation n'est pas annulable.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Command
 */
public class ImportImageCommand implements Command {
    /** Le chemin du fichier image à importer. */
    private String imagePath;

    /**
     * Constructeur de la commande d'importation d'image.
     * 
     * @param imagePath le chemin du fichier image à importer
     */
    public ImportImageCommand(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Importe l'image spécifiée dans l'éditeur.
     * </p>
     * 
     * @return {@code false} car cette commande n'est pas ajoutée à l'historique
     */
    public boolean execute() {
        // Implementation for importing an image
        Manager.getInstance().getEditor().getImage().importImage(imagePath);
        return false;
    }

    /**
     * {@inheritDoc}
     * <p>
     * L'annulation de l'importation n'est pas implémentée.
     * </p>
     */
    public void undo() {
        // Implementation for undoing the import image action
    }
}
