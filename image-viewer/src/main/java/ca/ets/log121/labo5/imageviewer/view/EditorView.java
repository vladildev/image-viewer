package ca.ets.log121.labo5.imageviewer.view;

import ca.ets.log121.labo5.imageviewer.controller.EditorController;
import ca.ets.log121.labo5.imageviewer.model.Manager;
import ca.ets.log121.labo5.imageviewer.tools.command.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class EditorView {
    private Stage stage;
    private EditorController controller;
    private FileChooser fileChooser = new FileChooser();
    

    // =========== FXML COMPONENTS ===========
    @FXML
    private ImageView imageView;

    // ======================================
    
    public EditorView(Stage stage) {
        this.stage = stage;
    }

    public void show() throws IOException {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/ca/ets/log121/labo5/imageviewer/editor-view.fxml")
        );
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Éditeur d'images");
        imageView = new ImageView(Manager.getInstance().getEditor().getImage().getPath());
        stage.setScene(scene);
        stage.show();
    }

    public void setController(EditorController controller) {
        this.controller = controller;
    }

    // =========== SETTERS ===========
    
    public void setImage(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
            }
        }
    }
    
    /**
     * Applique un zoom sur l'image en définissant une nouvelle taille de crop
     * @param cropWidth Nouvelle largeur de la zone visible
     * @param cropHeight Nouvelle hauteur de la zone visible
     */
    public void zoomOnImage(int cropWidth, int cropHeight) {
        if (imageView != null && imageView.getImage() != null) {
            Image image = imageView.getImage();
            double imageWidth = image.getWidth();
            double imageHeight = image.getHeight();
            
            // Calculer le facteur de zoom (rapport entre l'image complète et le crop)
            double zoomFactorX = imageWidth / cropWidth;
            double zoomFactorY = imageHeight / cropHeight;
            double zoomFactor = Math.max(zoomFactorX, zoomFactorY);
            
            // Appliquer le zoom
            imageView.setScaleX(zoomFactor);
            imageView.setScaleY(zoomFactor);
            
            System.out.println("Zoom applied: crop=" + cropWidth + "x" + cropHeight + 
                             " zoom factor=" + zoomFactor);
        }
    }
    
    /**
     * Déplace l'image vers une nouvelle position (translation)
     * @param deltaX Déplacement horizontal (positif = droite, négatif = gauche)
     * @param deltaY Déplacement vertical (positif = bas, négatif = haut)
     */
    public void translateOnImage(int deltaX, int deltaY) {
        if (imageView != null) {
            
            // Appliquer le déplacement relatif
            imageView.setTranslateX(deltaX);
            imageView.setTranslateY(deltaY);
            
            System.out.println("Translation applied: delta=(" + deltaX + "," + deltaY + 
                             ") new pos=(" + imageView.getTranslateX() + "," + 
                             imageView.getTranslateY() + ")");
        }
    }












    // ============ EVENT HANDLERS ============

    @FXML
    public void handleZoom(ActionEvent event) {
        if (controller != null) {
            controller.doZoom(1.2);
        }
    }
    
    @FXML
    public void handleTranslate(ActionEvent event) {
        if (controller != null) {
            controller.doTranslate(10, 10);
        }
    }
    
    @FXML
    public void handleImportImage(ActionEvent event) {
        if (controller != null) {
            fileChooser.setTitle("Open Image File");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
            );

            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                controller.doImportImage(selectedFile.getPath());
            }
        } else {
            System.out.println("Controller is null!");
        }
    }
    
    @FXML
    public void handleSaveImage(ActionEvent event) {
        if (controller != null) {
            fileChooser.setTitle("Save Edited Image");

            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                    "PNG Image", "*.png"
            ));

            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                controller.doSaveImage(file.getPath());
            }
        }
    }
    
    @FXML
    public void handleImportConfig(ActionEvent event) {
        if (controller != null) {
            fileChooser.setTitle("Open Configuration File");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Json files", "*.json")
            );

            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                controller.doLoadConfigFile(selectedFile.getPath());
            }
        }
    }
    
    @FXML
    public void handleSaveConfig(ActionEvent event) {
        if (controller != null) {
            fileChooser.setTitle("Save Configurations");

            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                    "Json File", "*.json"
            ));

            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                controller.doSaveConfigFile(file.getPath());
            }
        }
    }
    
    @FXML
    public void handleUndo(ActionEvent event) {
        if (controller != null) {
            controller.doUndo();
        }
    }
    
    @FXML
    public void handleRedo(ActionEvent event) {
        if (controller != null) {
            controller.doRedo();
        }
    }
}
