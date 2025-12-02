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

/**
 * Vue principale de l'éditeur d'images.
 *
 * Cette classe gère l'interface utilisateur de l'édition d'images.
 * Elle permet de :
 * <ul>
 *   <li>Afficher et manipuler une image (zoom, translation)</li>
 *   <li>Importer et exporter des images</li>
 *   <li>Sauvegarder et charger des configurations</li>
 *   <li>Gérer les instantanés (snapshots) de configuration</li>
 *   <li>Effectuer des opérations Undo/Redo</li>
 * </ul>
 * </p>
 * <p>
 * L'interface prend en charge les interactions souris (drag pour translation,
 * Ctrl+scroll pour zoom) et les raccourcis clavier (Ctrl+Z pour Undo,
 * Ctrl+Y pour Redo).
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see EditorController
 */
public class EditorView {
    /** La fenêtre principale de l'application. */
    private Stage stage;
    /** Le contrôleur associé à cette vue. */
    private EditorController controller;
    /** Sélecteur de fichiers pour l'import/export. */
    private FileChooser fileChooser = new FileChooser();
    

    // =========== FXML COMPONENTS ===========
    /** Composant FXML pour afficher l'image. */
    @FXML
    private ImageView imageView;
    /** Champ de saisie pour le zoom. */
    @FXML
    private TextField zoomInput;
    /** Champ de saisie pour la translation X. */
    @FXML
    private TextField translateXInput;
    /** Champ de saisie pour la translation Y. */
    @FXML
    private TextField translateYInput;
    /** Conteneur principal pour l'empilement des éléments. */
    @FXML
    private StackPane editorStack;
    /** Rectangle représentant le cadre de sélection/recadrage. */
    @FXML
    private javafx.scene.shape.Rectangle cadre;
    /** Liste déroulante pour les instantanés de configuration. */
    @FXML
    private ComboBox<String> snapshotsComboBox;

    // ======================================
    
    /**
     * Constructeur de la vue d'édition.
     * 
     * @param stage la fenêtre principale de l'application
     */
    public EditorView(Stage stage) {
        this.stage = stage;
    }

    /**
     * Affiche la vue d'édition avec l'image actuellement chargée.
     * <p>
     * Cette méthode charge le fichier FXML, configure tous les gestionnaires
     * d'événements (scroll pour zoom, drag pour translation, raccourcis clavier)
     * et affiche la fenêtre.
     * </p>
     * 
     * @throws IOException si le chargement du fichier FXML échoue
     */
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

    /**
     * Retourne le conteneur principal pour l'intégration de la miniature.
     * 
     * @return le StackPane de l'éditeur
     */
    public StackPane getThumbnailStack() {
        return editorStack;
    }
    // =========== SETTERS ===========

    /**
     * Définit le contrôleur de cette vue.
     * 
     * @param controller le contrôleur à associer
     */
    public void setController(EditorController controller) {
        this.controller = controller;
    }

    /**
     * Définit l'image à afficher dans l'éditeur.
     * 
     * @param imagePath le chemin du fichier image
     */
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
     * Applique un zoom sur le cadre en modifiant ses dimensions.
     * <p>
     * Cette méthode met à jour les dimensions du rectangle de sélection
     * pour refléter le niveau de zoom actuel.
     * </p>
     * 
     * @param cropWidth nouvelle largeur de la zone visible (largeur du cadre)
     * @param cropHeight nouvelle hauteur de la zone visible (hauteur du cadre)
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
     * Applique une translation sur le cadre.
     * <p>
     * Cette méthode déplace le rectangle de sélection à la nouvelle position.
     * </p>
     * 
     * @param deltaX translation horizontale (positif = droite, négatif = gauche)
     * @param deltaY translation verticale (positif = bas, négatif = haut)
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
     * Définit les dimensions du cadre semi-transparent.
     * 
     * @param width la largeur du cadre
     * @param height la hauteur du cadre
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

    /**
     * Gère l'action de zoom avant (agrandir la vue).
     * 
     * @param event l'événement d'action déclencheur
     */
    @FXML
    public void handleZoomIn(ActionEvent event) {
        if (controller != null) {
            controller.doZoom(0.9);
        }
    }

    /**
     * Gère l'action de zoom arrière (réduire la vue).
     * 
     * @param event l'événement d'action déclencheur
     */
    @FXML
    public void handleZoomOut(ActionEvent event) {
        if (controller != null) {
            controller.doZoom(1.1);
        }
    }
    
    /**
     * Gère l'action de translation vers la gauche.
     * 
     * @param event l'événement d'action déclencheur
     */
    @FXML
    public void handleTranslateLeft(ActionEvent event) {
        if (controller != null) {
            controller.doTranslate(-10, 0);
        }
    }

    /**
     * Gère l'action de translation vers la droite.
     * 
     * @param event l'événement d'action déclencheur
     */
    @FXML
    public void handleTranslateRight(ActionEvent event) {
        if (controller != null) {
            controller.doTranslate(10, 0);
        }
    }

    /**
     * Gère l'action de translation vers le haut.
     * 
     * @param event l'événement d'action déclencheur
     */
    @FXML
    public void handleTranslateUp(ActionEvent event) {
        if (controller != null) {
            controller.doTranslate(0, -10);
        }
    }

    /**
     * Gère l'action de translation vers le bas.
     * 
     * @param event l'événement d'action déclencheur
     */
    @FXML
    public void handleTranslateBottom(ActionEvent event) {
        if (controller != null) {
            controller.doTranslate(0, 10);
        }
    }

    /**
     * Gère la sélection d'un instantané dans la liste déroulante.
     * <p>
     * Restaure la configuration de l'instantané sélectionné.
     * </p>
     * 
     * @param event l'événement d'action déclencheur
     */
    @FXML
    public void handleSnapshotSelection(ActionEvent event) {
        if (controller != null && snapshotsComboBox != null) {
            int idx = snapshotsComboBox.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                controller.restoreMementoAt(idx);
            }
        }
    }

    /**
     * Gère l'action de sauvegarde d'un instantané.
     * <p>
     * Crée un nouveau memento et met à jour la liste déroulante.
     * </p>
     * 
     * @param event l'événement d'action déclencheur
     */
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
    
    /**
     * Gère l'action d'importation d'une image.
     * <p>
     * Ouvre un sélecteur de fichiers pour choisir une image à importer.
     * </p>
     * 
     * @param event l'événement d'action déclencheur
     */
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
    
    /**
     * Gère l'action de sauvegarde de l'image recadrée.
     * <p>
     * Ouvre un sélecteur de fichiers pour choisir la destination
     * et sauvegarde la zone visible de l'image.
     * </p>
     * 
     * @param event l'événement d'action déclencheur
     */
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
    
    /**
     * Gère l'action d'importation d'une configuration.
     * <p>
     * Ouvre un sélecteur de fichiers pour choisir un fichier JSON
     * contenant une configuration à charger.
     * </p>
     * 
     * @param event l'événement d'action déclencheur
     */
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
    
    /**
     * Gère l'action de sauvegarde de la configuration.
     * <p>
     * Ouvre un sélecteur de fichiers pour choisir la destination
     * et sauvegarde la configuration actuelle en JSON.
     * </p>
     * 
     * @param event l'événement d'action déclencheur
     */
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
    
    /**
     * Gère l'action d'annulation (Undo).
     * <p>
     * Annule la dernière action effectuée.
     * </p>
     * 
     * @param event l'événement d'action déclencheur
     */
    @FXML
    public void handleUndo(ActionEvent event) {
        if (controller != null) {
            controller.doUndo();
        }
    }
    
    /**
     * Gère l'action de rétablissement (Redo).
     * <p>
     * Rétablit la dernière action annulée.
     * </p>
     * 
     * @param event l'événement d'action déclencheur
     */
    @FXML
    public void handleRedo(ActionEvent event) {
        if (controller != null) {
            controller.doRedo();
        }
    }
}
