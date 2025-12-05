package com.imageviewer.controller;

import com.imageviewer.model.Manager;
import com.imageviewer.model.observer.Image;
import com.imageviewer.model.observer.Observable;
import com.imageviewer.model.observer.Observer;
import com.imageviewer.tools.command.ImportImageCommand;
import com.imageviewer.view.EditorView;
import com.imageviewer.view.HomeView;

import java.io.IOException;

/**
 * Contrôleur pour la vue d'accueil de l'application.
 * <p>
 * Cette classe gère les interactions de la vue d'accueil, notamment
 * l'importation initiale d'une image. Elle implémente le patron Observer
 * pour pouvoir réagir aux changements d'état si nécessaire.
 * </p>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Observer
 * @see HomeView
 */
public class HomeController implements Observer {
    /** La vue d'accueil associée à ce contrôleur. */
    HomeView homeView;

    /**
     * Constructeur du contrôleur d'accueil.
     * 
     * @param homeView la vue d'accueil à contrôler
     */
    public HomeController(HomeView homeView) {
        this.homeView = homeView;
    }

    /**
     * Met à jour la vue en réponse à un changement d'état observé.
     * <p>
     * Cette méthode est appelée automatiquement lorsqu'un objet observé
     * notifie ses observateurs d'un changement.
     * </p>
     * 
     * @param o l'objet observable qui a changé d'état
     */
    @Override
    public void update(Observable o) {
    }

    /**
     * Exécute l'importation d'une image depuis un fichier.
     * <p>
     * Cette méthode utilise le patron Command via l'Invoker pour
     * exécuter la commande d'importation d'image.
     * </p>
     * 
     * @param filePath le chemin du fichier image à importer
     */
    public void doImport(String filePath) {
        Invoker.getInstance().executeCommand(new ImportImageCommand(filePath));
    }
}
