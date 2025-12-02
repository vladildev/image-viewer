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

/**
 * Vue miniature affichant une prévisualisation de l'image.
 * <p>
 * Cette classe affiche une miniature de l'image avec la zone de recadrage
 * actuelle, permettant à l'utilisateur de voir ce qui sera sauvegardé.
 * Elle est intégrée dans la vue d'édition principale.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see ThumbnailController
 */
public class ThumbnailView {
    /** Le contrôleur associé à cette vue. */
    ThumbnailController thumbnailController;

    /** Composant FXML pour afficher l'image miniature. */
    @FXML
    private ImageView thumbnailImage;
    /** Référence vers le conteneur parent dans la vue éditeur. */
    private StackPane editorStack;

    /**
     * Définit l'image à afficher dans la miniature.
     * 
     * @param imagePath le chemin du fichier image
     */
    public void setImage(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                thumbnailImage.setImage(image);
            }
        }
    }

    /**
     * Affiche la vue miniature.
     * <p>
     * Charge le fichier FXML et ajoute la miniature au conteneur parent
     * dans le coin inférieur droit.
     * </p>
     */
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
     * Met à jour la miniature pour afficher la zone qui sera sauvegardée.
     * <p>
     * Cette méthode configure le viewport de l'ImageView pour afficher
     * uniquement la zone de recadrage spécifiée.
     * </p>
     * 
     * @param cropX la position X du recadrage dans l'image originale
     * @param cropY la position Y du recadrage dans l'image originale
     * @param cropWidth la largeur de la zone de recadrage
     * @param cropHeight la hauteur de la zone de recadrage
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

    /**
     * Définit le conteneur parent de la miniature.
     * 
     * @param editorStack le StackPane de la vue éditeur
     */
    public void setParentStack(StackPane editorStack) {
        this.editorStack = editorStack;
    }

    /**
     * Définit le contrôleur de cette vue.
     * 
     * @param thumbnailController le contrôleur à associer
     */
    public void setThumbnailController(ThumbnailController thumbnailController) {
        this.thumbnailController = thumbnailController;
    }
}
