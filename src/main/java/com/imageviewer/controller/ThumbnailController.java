package com.imageviewer.controller;

import com.imageviewer.model.Manager;
import com.imageviewer.model.observer.Image;
import com.imageviewer.model.observer.Observable;
import com.imageviewer.model.observer.Observer;
import com.imageviewer.model.observer.Perspective;
import com.imageviewer.view.*;

/**
 * Contrôleur pour la vue miniature (thumbnail) de l'image.
 * <p>
 * Cette classe gère l'affichage d'une prévisualisation de l'image
 * montrant la zone qui sera sauvegardée. Elle implémente le patron Observer
 * pour mettre à jour automatiquement la miniature lorsque l'image
 * ou la perspective change.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Observer
 * @see ThumbnailView
 */
public class ThumbnailController implements Observer {
    /** La vue miniature associée à ce contrôleur. */
    ThumbnailView view;
    /** La vue éditeur parente. */
    EditorView editorView;

    /**
     * Constructeur du contrôleur de miniature.
     * 
     * @param thumbnailView la vue miniature à contrôler
     * @param editorView la vue éditeur parente
     */
    public ThumbnailController(ThumbnailView thumbnailView, EditorView editorView) {
        this.view = thumbnailView;
        this.editorView = editorView;
    }

    /**
     * Calcule et met à jour les coordonnées de la zone de recadrage.
     * <p>
     * Utilise la même logique que {@link Manager#saveImageToFile(String)}
     * pour calculer les coordonnées du recadrage à partir des données
     * de la perspective et de l'image.
     * </p>
     */
    private void updateCropPreview() {
        Image baseImage = Manager.getInstance().getEditor().getImage();
        Perspective perspective = Manager.getInstance().getEditor().getPerspective();
        
        if (baseImage.getPath() == null || baseImage.getPath().isEmpty()) {
            return;
        }
        
        // Calculate scale factor between displayed and original image
        double factorX = baseImage.getWidth() / 600.0;
        double factorY = baseImage.getHeight() / (600.0 * baseImage.getHeight() / baseImage.getWidth() );
        
        // Calculate crop coordinates (same logic as saveImageToFile)
        int cropX = (baseImage.getWidth() / 2) + ((int) (perspective.getX() * factorX) - (int) (perspective.getWidth() * factorX / 2.0));
        int cropY = (baseImage.getHeight() / 2) + ((int) (perspective.getY() * factorY) - (int) (perspective.getHeight() * factorY / 2.0));
        cropX = Math.max(0, cropX);
        cropY = Math.max(0, cropY);
        
        int cropWidth = (int) (perspective.getWidth() * factorX);
        int cropHeight = (int) (perspective.getHeight() * factorY);
        
        // Update the view with calculated crop coordinates
        view.updateCropPreview(cropX, cropY, cropWidth, cropHeight);
    }

    /**
     * Met à jour la vue en réponse à un changement d'état observé.
     * <p>
     * Cette méthode est appelée automatiquement lorsqu'un objet observé
     * (Image ou Perspective) notifie ses observateurs d'un changement.
     * Elle met à jour la miniature pour refléter la zone de recadrage actuelle.
     * </p>
     * 
     * @param o l'objet observable qui a changé d'état
     */
    @Override
    public void update(Observable o) {
        if (o instanceof Image) {
            view.setParentStack(editorView.getThumbnailStack());
            view.show();
            view.setImage(((Image) o).getPath());
            // Update preview after image is loaded
            updateCropPreview();
        }
        else if (o instanceof Perspective) {
            // Update preview when perspective changes (zoom/translate)
            updateCropPreview();
        }
    }
}
