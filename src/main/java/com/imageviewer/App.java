package com.imageviewer;

import com.imageviewer.controller.EditorController;
import com.imageviewer.controller.HomeController;
import com.imageviewer.controller.ThumbnailController;
import com.imageviewer.model.Manager;
import com.imageviewer.view.EditorView;
import com.imageviewer.view.HomeView;
import com.imageviewer.view.ThumbnailView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe principale de l'application JavaFX de visualisation d'images.
 * 
 * Cette classe étend {@link Application} et initialise tous les composants
 * de l'application selon le patron MVC (Modèle-Vue-Contrôleur).
 * Elle configure les vues (EditorView, ThumbnailView, HomeView) et leurs
 * contrôleurs respectifs, puis établit les liens avec le patron Observer
 * pour la mise à jour automatique de l'interface.
 *
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Application
 * @see EditorController
 * @see HomeController
 * @see ThumbnailController
 */
public class App extends Application {
    /**
     * Constructeur par défaut de l'application.
     */
    public App() {
        // Constructeur par défaut requis par JavaFX
    }

    /**
     * Point d'entrée de l'application JavaFX.
     *
     * Cette méthode initialise tous les composants de l'application :
     * <ul>
     *   <li>Création des vues (EditorView, ThumbnailView, HomeView)</li>
     *   <li>Création des contrôleurs correspondants</li>
     *   <li>Configuration du patron Observer pour les mises à jour automatiques</li>
     *   <li>Affichage de la vue d'accueil</li>
     * </ul>
     *
     * 
     * @param stage la fenêtre principale de l'application
     * @throws IOException si le chargement des ressources FXML échoue
     */
    @Override
    public void start(Stage stage) throws IOException {
        // EDITOR Components creation
        EditorView editorView = new EditorView(stage);
        EditorController editorController = new EditorController(editorView);
        editorView.setController(editorController);

        // THUMBNAIL Components creation
        ThumbnailView thumbnailView = new ThumbnailView();
        ThumbnailController thumbnailController = new ThumbnailController(thumbnailView, editorView);
        thumbnailView.setThumbnailController(thumbnailController);

        // HOMEVIEW Components creation
        HomeView homeView = new HomeView(stage);
        HomeController homeController = new HomeController(homeView);
        homeView.setHomeController(homeController);

        // MANAGER Components creation
        Manager manager = Manager.getInstance();
        manager.getEditor().getImage().attach(editorController);
        manager.getEditor().getPerspective().attach(editorController);
        manager.getEditor().getImage().attach(thumbnailController);
        manager.getEditor().getPerspective().attach(thumbnailController);

        homeView.show();
    }
}
