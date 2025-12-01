package ca.ets.log121.labo5.imageviewer.view;

import ca.ets.log121.labo5.imageviewer.controller.ThumbnailController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.IOException;

public class ThumbnailView {
    ThumbnailController thumbnailController;

    @FXML
    private ImageView thumbnailImage;
    private StackPane editorStack;

    public void setImage(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                thumbnailImage.setImage(image);
            }
        }
    }

    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ca/ets/log121/labo5/imageviewer/thumbnail-view.fxml")
            );
            loader.setController(this);
            Parent thumbRoot = loader.load();

            this.editorStack.getChildren().add(thumbRoot);

            StackPane.setMargin(thumbRoot, new Insets(10));
            StackPane.setAlignment(thumbRoot, Pos.BOTTOM_RIGHT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the thumbnail to show what would be saved
     * @param cropX X position of the crop in the original image
     * @param cropY Y position of the crop in the original image
     * @param cropWidth Width of the crop zone
     * @param cropHeight Height of the crop zone
     */
    public void updateCropPreview(int cropX, int cropY, int cropWidth, int cropHeight) {
        if (thumbnailImage != null && thumbnailImage.getImage() != null) {
            // Create a viewport to display only the zone that would be saved
            javafx.geometry.Rectangle2D viewport = new javafx.geometry.Rectangle2D(
                cropX, cropY, cropWidth, cropHeight
            );
            thumbnailImage.setViewport(viewport);
            
            System.out.println("Thumbnail updated: crop=(" + cropX + "," + cropY + ") size=" + cropWidth + "x" + cropHeight);
        }
    }

    public void setParentStack(StackPane editorStack) {
        this.editorStack = editorStack;
    }
    public void setThumbnailController(ThumbnailController thumbnailController) {
        this.thumbnailController = thumbnailController;
    }
}
