package ca.ets.log121.labo5.imageviewer.controller;

import ca.ets.log121.labo5.imageviewer.model.Manager;
import ca.ets.log121.labo5.imageviewer.model.observer.Image;
import ca.ets.log121.labo5.imageviewer.model.observer.Observable;
import ca.ets.log121.labo5.imageviewer.model.observer.Observer;
import ca.ets.log121.labo5.imageviewer.model.observer.Perspective;
import ca.ets.log121.labo5.imageviewer.view.*;

public class ThumbnailController implements Observer {
    ThumbnailView view;
    EditorView editorView;

    public ThumbnailController(ThumbnailView thumbnailView, EditorView editorView) {
        this.view = thumbnailView;
        this.editorView = editorView;
    }

    /**
     * Calculate crop coordinates from perspective and image data
     * Uses the same logic as Manager.saveImageToFile
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
