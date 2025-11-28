package ca.ets.log121.labo5.imageviewer.view;

import ca.ets.log121.labo5.imageviewer.controller.EditorController;
import ca.ets.log121.labo5.imageviewer.model.Manager;
import ca.ets.log121.labo5.imageviewer.tools.command.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class EditorView {
    private Stage stage;
    private EditorController controller;
    private FileChooser fileChooser = new FileChooser();
    

    // =========== FXML COMPONENTS ===========
    @FXML
    private ImageView imageView;
    @FXML
    private TextField zoomInput;
    @FXML
    private TextField translateXInput;
    @FXML
    private TextField translateYInput;
    @FXML
    private javafx.scene.shape.Rectangle cadre;

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

        String path = Manager.getInstance().getEditor().getImage().getPath();
        Image img = new Image(new File(path).toURI().toString());
        imageView.setImage(img);
        imageView.setFitWidth(400);
        imageView.setPreserveRatio(true);

        // Capture zoom avec scroll de la souris (Ctrl + scroll)
        imageView.setOnScroll(event -> {
            if (event.isControlDown() && controller != null) {
                double zoomFactor = event.getDeltaY() > 0 ? 1.1 : 0.9;
                controller.doZoom(zoomFactor);
                event.consume();
            }
        });

        // Capture zoom avec gestes tactiles (pinch to zoom)
        imageView.setOnZoom(event -> {
            if (controller != null) {
                double zoomFactor = event.getZoomFactor();
                controller.doZoom(zoomFactor);
                event.consume();
            }
        });

        // Variables pour stocker la position initiale du drag
        final double[] dragStartX = new double[1];
        final double[] dragStartY = new double[1];

        // Capture le début du drag
        imageView.setOnMousePressed(event -> {
            dragStartX[0] = event.getSceneX();
            dragStartY[0] = event.getSceneY();
        });

        // Capture le drag (déplacement avec souris enfoncée)
        imageView.setOnMouseDragged(event -> {
            if (controller != null) {
                int deltaX = (int)(event.getSceneX() - dragStartX[0]);
                int deltaY = (int)(event.getSceneY() - dragStartY[0]);
                
                // Mettre à jour la position de départ pour le prochain drag
                dragStartX[0] = event.getSceneX();
                dragStartY[0] = event.getSceneY();
                
                // Appliquer la translation
                controller.doTranslate(deltaX, deltaY);
                event.consume();
            }
        });

        // Raccourcis clavier Ctrl+Z (Undo) et Ctrl+Y (Redo)
        scene.setOnKeyPressed(event -> {
            if (controller != null && event.isControlDown()) {
                switch (event.getCode()) {
                    case Z:
                        controller.doUndo();
                        event.consume();
                        break;
                    case Y:
                        controller.doRedo();
                        event.consume();
                        break;
                    default:
                        break;
                }
            }
        });

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
    
    /**
     * Set the dimensions of the semi-transparent frame (cadre) to match displayed image bounds
     * @param imageWidth Width of the original image
     * @param imageHeight Height of the original image
     */
    public void setCadreDimensions(int imageWidth, int imageHeight) {
        if (cadre != null && imageView != null && imageView.getImage() != null) {
            // Calculer les dimensions affichées de l'image (avec preserveRatio)
            double displayedWidth = imageView.getBoundsInParent().getWidth();
            double displayedHeight = imageView.getBoundsInParent().getHeight();
            
            cadre.setWidth(displayedWidth);
            cadre.setHeight(displayedHeight);
            System.out.println("Cadre dimensions set to displayed size: " + displayedWidth + "x" + displayedHeight);
        }
    }


    // ============ GETTERS & SETTERS ============


    // ============ EVENT HANDLERS ============

    @FXML
    public void handleZoom(ActionEvent event) {
        if (controller != null) {
            try {
                controller.doZoom(Double.parseDouble(zoomInput.getText()));
            } catch (NumberFormatException e) {
                System.out.println("Invalid number: " + zoomInput.getText());
            }
        }
    }
    
    @FXML
    public void handleTranslate(ActionEvent event) {
        if (controller != null) {
            int x = 0;
            int y = 0;
            try {
                x = translateXInput.getText().trim().isEmpty() ? 0 : Integer.parseInt(translateXInput.getText());
                y = translateYInput.getText().trim().isEmpty() ? 0 : Integer.parseInt(translateYInput.getText());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number x: " + translateXInput.getText() + ", y: " + translateYInput.getText());
            }
            controller.doTranslate(x, y);
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
