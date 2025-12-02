package ca.ets.log121.labo5.imageviewer.tools.command;
import ca.ets.log121.labo5.imageviewer.model.Manager;

/**
 * Commande pour effectuer une translation sur la zone visible de l'image.
 * <p>
 * Cette commande déplace la zone visible de l'image selon les valeurs
 * delta spécifiées. Cette commande est annulable et est ajoutée à l'historique.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Command
 */
public class TranslateCommand implements Command {
    /** Le déplacement horizontal. */
    private final int deltaX;
    /** Le déplacement vertical. */
    private final int deltaY;

    /**
     * Constructeur de la commande de translation.
     * 
     * @param deltaX le déplacement horizontal (positif vers la droite)
     * @param deltaY le déplacement vertical (positif vers le bas)
     */
    public TranslateCommand(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Applique la translation sur la perspective.
     * </p>
     * 
     * @return {@code true} car cette commande est ajoutée à l'historique
     */
    @Override
    public boolean execute() {
        Manager.getInstance().getEditor().getPerspective().translate(deltaX, deltaY);
        return true;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Annule la translation en appliquant le déplacement inverse.
     * </p>
     */
    @Override
    public void undo() {
        Manager.getInstance().getEditor().getPerspective().translate(-deltaX, -deltaY);
    }
}
