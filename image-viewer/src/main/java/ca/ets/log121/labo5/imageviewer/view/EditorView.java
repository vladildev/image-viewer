package ca.ets.log121.labo5.imageviewer.view;

import ca.ets.log121.labo5.imageviewer.controller.EditorController;
import ca.ets.log121.labo5.imageviewer.model.Manager;
import ca.ets.log121.labo5.imageviewer.controller.ThumbnailController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

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
    private StackPane editorStack;
    @FXML
    private javafx.scene.shape.Rectangle cadre;
    @FXML
    private ComboBox<String> snapshotsComboBox;

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
        Scene scene = new Scene(root, 1080, 800);
        stage.setTitle("Éditeur d'images");

        String path = Manager.getInstance().getEditor().getImage().getPath();
        Image img = new Image(new File(path).toURI().toString());
        imageView.setImage(img);
        imageView.setFitWidth(600);
        imageView.setPreserveRatio(true);

        // Initialiser le cadre au centre de l'image
        cadre.setWidth(imageView.getFitWidth());
        cadre.setHeight(imageView.getFitHeight());

        // Capture zoom avec scroll de la souris (Ctrl + scroll) sur le cadre
        cadre.setOnScroll(event -> {
            if (event.isControlDown() && controller != null) {
                double zoomFactor = event.getDeltaY() > 0 ? 1.1 : 0.9;
                controller.doZoom(zoomFactor);
                event.consume();
            }
        });

        // Capture zoom avec gestes tactiles (pinch to zoom) sur le cadre
        cadre.setOnZoom(event -> {
            if (controller != null) {
                double zoomFactor = event.getZoomFactor();
                controller.doZoom(zoomFactor);
                event.consume();
            }
        });

        // Variables pour stocker la position initiale du drag
        final double[] dragStartX = new double[1];
        final double[] dragStartY = new double[1];
        final double[] cadreStartX = new double[1];
        final double[] cadreStartY = new double[1];

        // Capture le début du drag sur le cadre
        cadre.setOnMousePressed(event -> {
            dragStartX[0] = event.getSceneX();
            dragStartY[0] = event.getSceneY();
            cadreStartX[0] = cadre.getTranslateX();
            cadreStartY[0] = cadre.getTranslateY();
            event.consume();
        });

        // Capture le drag (déplacement avec souris enfoncée) sur le cadre
        cadre.setOnMouseDragged(event -> {
            if (controller != null) {
                // Calculer le déplacement total depuis le début du drag
                double totalDeltaX = event.getSceneX() - dragStartX[0];
                double totalDeltaY = event.getSceneY() - dragStartY[0];
                
                // Appliquer le déplacement depuis la position initiale
                double newX = cadreStartX[0] + totalDeltaX;
                double newY = cadreStartY[0] + totalDeltaY;
                
                cadre.setTranslateX(newX);
                cadre.setTranslateY(newY);
                
                event.consume();
            }
        });
        
        // Sauvegarder la position finale dans le contrôleur quand on relâche
        cadre.setOnMouseReleased(event -> {
            if (controller != null) {
                int deltaX = (int)(event.getSceneX() - dragStartX[0]);
                int deltaY = (int)(event.getSceneY() - dragStartY[0]);
                if (deltaX != 0 || deltaY != 0) {
                    controller.doTranslate(deltaX, deltaY);
                }
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

        // Populate snapshots list at startup
        if (controller != null && snapshotsComboBox != null) {
            List<String> summaries = controller.getMementoSummaries();
            snapshotsComboBox.getItems().clear();
            if (summaries != null) snapshotsComboBox.getItems().addAll(summaries);
        }
    }

    public StackPane getThumbnailStack() {
        return editorStack;
    }
    // =========== SETTERS ===========

    public void setController(EditorController controller) {
        this.controller = controller;
    }

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
     * Apply zoom on the frame (cadre) by changing its size
     * @param cropWidth New width for visible zone (frame width)
     * @param cropHeight New height for visible zone (frame height)
     */
    public void zoomOnImage(int cropWidth, int cropHeight) {
        if (cadre != null) {
            // Appliquer les nouvelles dimensions au cadre
            cadre.setWidth(cropWidth);
            cadre.setHeight(cropHeight);
            
            System.out.println("Zoom applied to frame: width=" + cropWidth + " height=" + cropHeight);
        }
    }
    
    /**
     * Translate the frame (cadre) to new position
     * @param deltaX horizontal translation (positive = right, négative = left)
     * @param deltaY Vertical translation (positive = down, négative = up)
     */
    public void translateOnImage(int deltaX, int deltaY) {
        if (cadre != null) {
            // Définir directement la nouvelle position (pas d'ajout cumulatif)
            cadre.setTranslateX(deltaX);
            cadre.setTranslateY(deltaY);
            
            System.out.println("Translation applied to frame: pos=(" + deltaX + "," + deltaY + ")");
        }
    }
    
    /**
     * Set the dimensions of the semi-transparent frame (cadre)
     * @param width Width of the frame
     * @param height Height of the frame
     */
    public void setCadreDimensions(int width, int height) {
        if (cadre != null) {
            cadre.setWidth(width);
            cadre.setHeight(height);
            System.out.println("Frame dimensions set to: " + width + "x" + height);
        }
    }


    // ============ GETTERS & SETTERS ============


    // ============ EVENT HANDLERS ============

    @FXML
    public void handleZoomIn(ActionEvent event) {
        if (controller != null) {
            controller.doZoom(0.9);
        }
    }
    @FXML
    public void handleZoomOut(ActionEvent event) {
        if (controller != null) {
            controller.doZoom(1.1);
        }
    }
    
    @FXML
    public void handleTranslateLeft(ActionEvent event) {
        if (controller != null) {
            controller.doTranslate(-10, 0);
        }
    }
    @FXML
    public void handleTranslateRight(ActionEvent event) {
        if (controller != null) {
            controller.doTranslate(10, 0);
        }
    }
    @FXML
    public void handleTranslateUp(ActionEvent event) {
        if (controller != null) {
            controller.doTranslate(0, -10);
        }
    }
    @FXML
    public void handleTranslateBottom(ActionEvent event) {
        if (controller != null) {
            controller.doTranslate(0, 10);
        }
    }

    @FXML
    public void handleSnapshotSelection(ActionEvent event) {
        if (controller != null && snapshotsComboBox != null) {
            int idx = snapshotsComboBox.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                controller.restoreMementoAt(idx);
            }
        }
    }

    @FXML
    public void handleSaveSnapshot(ActionEvent event) {
        if (controller != null) {
            // Ask controller to create/save a new memento
            controller.doCreateMemento();

            // Refresh combo box with new summaries
            if (snapshotsComboBox != null) {
                List<String> summaries = controller.getMementoSummaries();
                snapshotsComboBox.getItems().clear();
                if (summaries != null) snapshotsComboBox.getItems().addAll(summaries);
            }
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
