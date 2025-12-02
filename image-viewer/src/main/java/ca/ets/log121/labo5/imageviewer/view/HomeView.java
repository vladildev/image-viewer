package ca.ets.log121.labo5.imageviewer.view;

import ca.ets.log121.labo5.imageviewer.controller.HomeController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Vue d'accueil de l'application.
 * <p>
 * Cette classe gère l'interface utilisateur de l'écran d'accueil
 * qui permet à l'utilisateur de sélectionner une image à éditer.
 * Elle utilise JavaFX et FXML pour l'interface graphique.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see HomeController
 */
public class HomeView {
    /** Le contrôleur associé à cette vue. */
    private HomeController homeController;
    /** Sélecteur de fichiers pour l'importation d'images. */
    private final FileChooser fileChooser = new FileChooser();
    /** La fenêtre principale de l'application. */
    private Stage stage;

    /**
     * Constructeur de la vue d'accueil.
     * 
     * @param stage la fenêtre principale de l'application
     */
    public HomeView(Stage stage) {
        this.stage = stage;
    }

    // =========== FXML COMPONENTS ===========

    /**
     * Gère l'action de recherche de fichiers locaux.
     * <p>
     * Ouvre un sélecteur de fichiers permettant à l'utilisateur de choisir
     * une image (PNG, JPG ou GIF) à importer.
     * </p>
     * 
     * @throws IOException si le chargement de la vue échoue
     */
    @FXML
    protected void searchLocalFiles() throws IOException {
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            homeController.doImport(selectedFile.getPath());
        }
    }

    /**
     * Affiche la vue d'accueil.
     * <p>
     * Charge le fichier FXML et configure la scène avec les dimensions
     * spécifiées.
     * </p>
     * 
     * @throws IOException si le chargement du fichier FXML échoue
     */
    public void show() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ca/ets/log121/labo5/imageviewer/home-view.fxml")
        );
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root, 400, 400);
        stage.setTitle("Home Screen");
        stage.setScene(scene);
        stage.show();
    }


    // =========== SETTERS & GETTERS ===========

    /**
     * Définit le contrôleur de cette vue.
     * 
     * @param homeController le contrôleur à associer
     */
    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    /**
     * Retourne la fenêtre principale.
     * 
     * @return la fenêtre (Stage)
     */
    public Stage getStage() {
        return stage;
    }
}

