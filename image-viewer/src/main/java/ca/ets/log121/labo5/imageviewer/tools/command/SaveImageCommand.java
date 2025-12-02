package ca.ets.log121.labo5.imageviewer.tools.command;

import ca.ets.log121.labo5.imageviewer.model.Manager;

/**
 * Commande pour sauvegarder l'image recadrée dans un fichier.
 * <p>
 * Cette commande sauvegarde la zone visible de l'image (selon la perspective
 * actuelle) dans un fichier. Les formats supportés sont PNG, JPG et BMP.
 * Cette commande n'est pas ajoutée à l'historique.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Command
 */
public class SaveImageCommand implements Command {
    /** Le chemin du fichier de destination. */
    private String imagePath;

    /**
     * Constructeur de la commande de sauvegarde d'image.
     * 
     * @param imagePath le chemin du fichier de destination
     */
    public SaveImageCommand(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Sauvegarde l'image recadrée dans le fichier spécifié.
     * </p>
     * 
     * @return {@code false} car cette commande n'est pas ajoutée à l'historique
     */
    public boolean execute() {
        Manager.getInstance().saveImageToFile(imagePath);
        return false;
    }

    /**
     * {@inheritDoc}
     * <p>
     * L'annulation de la sauvegarde n'est pas implémentée.
     * </p>
     */
    public void undo() {
        // Implementation for undoing the save image action
    }
}
