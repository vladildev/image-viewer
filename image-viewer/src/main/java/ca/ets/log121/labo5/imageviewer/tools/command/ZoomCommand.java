package ca.ets.log121.labo5.imageviewer.tools.command;
import ca.ets.log121.labo5.imageviewer.model.Manager;

/**
 * Commande pour effectuer un zoom sur la zone visible de l'image.
 * <p>
 * Cette commande modifie les dimensions de la zone visible en les multipliant
 * par le facteur de zoom spécifié. Cette commande est annulable et est
 * ajoutée à l'historique.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Command
 */
public class ZoomCommand implements Command {
    /** Le facteur de zoom à appliquer. */
    private final double zoomFactor;

    /**
     * Constructeur de la commande de zoom.
     * 
     * @param zoomFactor le facteur de zoom (supérieur à 1 pour agrandir,
     *                   inférieur à 1 pour réduire)
     */
    public ZoomCommand(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Applique le zoom sur la perspective.
     * </p>
     * 
     * @return {@code true} car cette commande est ajoutée à l'historique
     */
    @Override
    public boolean execute() {
        System.out.println(">>> ZoomCommand.execute() called with factor: " + zoomFactor);
        Manager.getInstance().getEditor().getPerspective().zoom(zoomFactor);
        return true;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Annule le zoom en appliquant le facteur inverse.
     * </p>
     */
    @Override
    public void undo() {
        System.out.println(">>> ZoomCommand.undo() called with factor: " + (1/zoomFactor));
        Manager.getInstance().getEditor().getPerspective().zoom(1 / zoomFactor);
    }
}
