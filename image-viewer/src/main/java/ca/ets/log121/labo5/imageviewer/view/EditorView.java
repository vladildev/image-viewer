package ca.ets.log121.labo5.imageviewer.view;

import ca.ets.log121.labo5.imageviewer.controller.EditorController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class EditorView {
    private Stage stage;
    private EditorController controller;
    private boolean isOpen = false;
    

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
        stage.setScene(scene);
        stage.show();
        isOpen = true;
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
     * Apply zoom on image while defining new crop size
     * @param cropWidth New width for visible zone
     * @param cropHeight New height for visible zone
     */
    public void zoomOnImage(int cropWidth, int cropHeight) {
        if (imageView != null && imageView.getImage() != null) {
            Image image = imageView.getImage();
            double imageWidth = image.getWidth();
            double imageHeight = image.getHeight();
            
            // Compute zoom factor (relationship between the complete image and crop)
            double zoomFactorX = imageWidth / cropWidth;
            double zoomFactorY = imageHeight / cropHeight;
            double zoomFactor = Math.max(zoomFactorX, zoomFactorY);
            
            // Apply zoom
            imageView.setScaleX(zoomFactor);
            imageView.setScaleY(zoomFactor);
            
            System.out.println("Zoom applied: crop=" + cropWidth + "x" + cropHeight + 
                             " zoom factor=" + zoomFactor);
        }
    }
    
    /**
     * Translate image to new position (translation)
     * @param deltaX horizontal translation (positive = right, négative = left)
     * @param deltaY Vertical translation (positive = down, négative = up)
     */
    public void translateOnImage(int deltaX, int deltaY) {
        if (imageView != null) {
            
            // Apply relative translation
            imageView.setTranslateX(deltaX);
            imageView.setTranslateY(deltaY);
            
            System.out.println("Translation applied: delta=(" + deltaX + "," + deltaY + 
                             ") new pos=(" + imageView.getTranslateX() + "," + 
                             imageView.getTranslateY() + ")");
        }
    }


    // ============ GETTERS & SETTERS ============

    public boolean getIsOpen() {
        return isOpen;
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
        System.out.println("Button clicked!");
        if (controller != null) {
            // TODO: Ouvrir un FileChooser
            System.out.println("Importing image...");
            controller.doImportImage("C:\\Users\\louca\\Downloads\\IMG_3645.jpeg");
        } else {
            System.out.println("Controller is null!");
        }
    }
    
    @FXML
    public void handleSaveImage(ActionEvent event) {
        if (controller != null) {
            // TODO: Ouvrir un FileChooser
            controller.doSaveImage("path/to/save.jpg");
        }
    }
    
    @FXML
    public void handleImportConfig(ActionEvent event) {
        if (controller != null) {
            // TODO: Ouvrir un FileChooser
            controller.doLoadConfigFile("path/to/config.json");
        }
    }
    
    @FXML
    public void handleSaveConfig(ActionEvent event) {
        if (controller != null) {
            // TODO: Ouvrir un FileChooser
            controller.doSaveConfigFile("path/to/config.json");
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
