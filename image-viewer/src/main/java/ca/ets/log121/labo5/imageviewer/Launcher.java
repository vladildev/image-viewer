package ca.ets.log121.labo5.imageviewer;

import javafx.application.Application;

/**
 * Classe de lancement de l'application.
 *
 * Cette classe contient le point d'entrée principal (méthode main) de l'application.
 * Elle est séparée de la classe {@link App} pour permettre le lancement correct
 * de l'application JavaFX avec les modules Java.
 *
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see App
 */
public class Launcher {
    /**
     * Constructeur par défaut.
     */
    public Launcher() {
        // Constructeur par défaut
    }

    /**
     * Point d'entrée principal de l'application.
     * <p>
     * Lance l'application JavaFX en appelant {@link Application#launch(Class, String...)}
     * avec la classe {@link App}.
     * </p>
     * 
     * @param args les arguments de la ligne de commande
     */
    public static void main(String[] args) {
        Application.launch(App.class, args);
    }
}
