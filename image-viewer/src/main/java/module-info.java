/**
 * Module principal de l'application de visualisation d'images.
 * <p>
 * Ce module fournit une application JavaFX pour visualiser, éditer et manipuler des images
 * avec des fonctionnalités de zoom, translation, sauvegarde et gestion de l'historique des commandes.
 * </p>
 * <p>
 * L'application utilise les patrons de conception suivants :
 * </p>
 * <ul>
 *   <li>Observer - pour la notification des changements d'état</li>
 *   <li>Command - pour l'exécution et l'annulation des actions</li>
 *   <li>Memento - pour la sauvegarde et restauration de l'état</li>
 *   <li>Iterator - pour le parcours de l'historique des commandes</li>
 *   <li>Singleton - pour le gestionnaire principal</li>
 * </ul>
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 */
module ca.ets.log121.labo5.imageviewer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires java.desktop;
    
    opens ca.ets.log121.labo5.imageviewer to javafx.fxml;
    opens ca.ets.log121.labo5.imageviewer.controller to javafx.fxml;
    opens ca.ets.log121.labo5.imageviewer.view to javafx.fxml;

    exports ca.ets.log121.labo5.imageviewer;
    exports ca.ets.log121.labo5.imageviewer.controller;
}
