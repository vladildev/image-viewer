package ca.ets.log121.labo5.imageviewer.view;

import ca.ets.log121.labo5.imageviewer.controller.EditorController;
import ca.ets.log121.labo5.imageviewer.model.Manager;
import ca.ets.log121.labo5.imageviewer.tools.command.*;
import ca.ets.log121.labo5.imageviewer.controller.ThumbnailController;
import ca.ets.log121.labo5.imageviewer.model.Manager;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class EditorView {
    private Stage stage;
    private EditorController controller;
    private FileChooser fileChooser = new FileChooser();
    private ThumbnailController thumbnailController;
    

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
    private VBox thumbnailContainer;

    // ======================================
    
    public EditorView(Stage stage, ThumbnailController thumbnailController) {
        this.stage = stage;
        this.thumbnailController = thumbnailController;
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

        stage.setScene(scene);
        stage.show();
        loadThumbnailView();
    }

    public void loadThumbnailView() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ca/ets/log121/labo5/imageviewer/thumbnail-view.fxml")
            );

            Parent thumbRoot = loader.load();

            thumbnailController = loader.getController();
            thumbnailController.setEditor(controller);

            thumbnailContainer.getChildren().add(thumbRoot);

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @FXML
    public void openThumbnail() {
        if (controller != null) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/ca/ets/log121/labo5/imageviewer/view/ThumbnailView.fxml")
                );

                Parent thumbRoot = loader.load();
                thumbnailController = loader.getController();
                thumbnailController.setEditor(controller);

                thumbnailContainer.getChildren().add(thumbRoot);
                Manager.getInstance().getEditor().getImage().attach(thumbnailController);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
