package com.imageviewer.controller;
import com.imageviewer.model.observer.*;
import com.imageviewer.tools.command.*;
import com.imageviewer.view.EditorView;
import com.imageviewer.model.Manager;
import com.imageviewer.model.memento.Memento;
import com.imageviewer.model.memento.EditorMemento;
import com.imageviewer.model.observer.Perspective;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

/**
 * Contrôleur principal pour la vue d'édition d'images.
 *
 * Cette classe implémente le patron Observer pour recevoir les notifications
 * de changements d'état de l'image et de la perspective. Elle gère toutes
 * les interactions utilisateur liées à l'édition d'images, notamment :
 * <ul>
 *   <li>Zoom sur l'image</li>
 *   <li>Translation (déplacement) de la zone visible</li>
 *   <li>Annulation (Undo) et rétablissement (Redo) des actions</li>
 *   <li>Sauvegarde et chargement de configurations</li>
 *   <li>Import et export d'images</li>
 *   <li>Gestion des mementos (instantanés de l'état)</li>
 * </ul>
 *
 * 
 * @author LOG121 - Labo 5
 * @version 1.0
 * @see Observer
 * @see EditorView
 * @see Invoker
 */
public class EditorController implements Observer {
    /** La vue d'édition associée à ce contrôleur. */
    EditorView view;
    
    /**
     * Constructeur du contrôleur d'édition.
     * 
     * @param view la vue d'édition à contrôler
     */
    public EditorController(EditorView view) {
        this.view = view;
    }

    /**
     * Retourne une liste de résumés lisibles pour chaque memento sauvegardé.ardé.
     * <p>
     * Cette méthode garde la logique de formatage dans le contrôleur,
     * la vue ne reçoit que des chaînes de caractères.
     * </p>
     * 
     * @return une liste de chaînes décrivant chaque memento avec sa date de création
     */
    public List<String> getMementoSummaries() {
        List<String> summaries = new ArrayList<>();
        List<Memento> mems = Manager.getInstance().getMementos();
        int idx = 0;
        for (Memento m : mems) {
            idx++;
            if (m instanceof EditorMemento) {
                EditorMemento em = (EditorMemento) m;
                java.time.LocalDateTime dt = em.getCreatedAt();
                String s = dt == null ? (idx + ") [no date]") : dt.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                summaries.add(s);
            } else {
                summaries.add(idx + ") [unknown memento]");
            }
        }
        return summaries;
    }

    /**
     * Restaure le memento à l'index donné.
     * <p>
     * L'index est basé sur 0 (le premier élément est à l'index 0).
     * Si l'index est invalide, aucune action n'est effectuée.
     * </p>
     * 
     * @param index l'index du memento à restaurer (basé sur 0)
     */
    public void restoreMementoAt(int index) {
        List<Memento> mems = Manager.getInstance().getMementos();
        if (mems == null) return;
        if (index < 0 || index >= mems.size()) return;
        Manager.getInstance().restoreFromMemento(mems.get(index));
    }

    /**
     * Met à jour la vue en réponse à un changement d'état observé.
     * <p>
     * Cette méthode est appelée automatiquement lorsqu'un objet observé
     * (Image ou Perspective) notifie ses observateurs d'un changement.
     * </p>
     * 
     * @param o l'objet observable qui a changé d'état
     */
    public void update(Observable o) {
        if (o instanceof Image){
            Image img = (Image)o;
            try {
                view.show();
            } catch (IOException e) {
                System.out.println(e);
            }
            view.setImage(img.getPath());
            // Mettre à jour les dimensions du cadre avec les dimensions de l'image
            // view.setCadreDimensions(img.getWidth(), img.getHeight());
        } else if (o instanceof Perspective){
            Perspective p = (Perspective) o;
            // Appliquer le zoom avec les dimensions du crop
            view.zoomOnImage(p.getWidth(), p.getHeight());
            // Appliquer la translation avec la position du crop
            view.translateOnImage(p.getX(), p.getY());
        }
        
    }

    /**
     * Exécute une commande de zoom sur l'image.
     * 
     * @param factor le facteur de zoom (supérieur à 1 pour agrandir, inférieur à 1 pour réduire)
     */
    public void doZoom(double factor){
        Invoker.getInstance().executeCommand(new ZoomCommand(factor));
    }

    /**
     * Exécute une commande de translation (déplacement) de la zone visible.
     * 
     * @param deltaX le déplacement horizontal en pixels (positif vers la droite)
     * @param deltaY le déplacement vertical en pixels (positif vers le bas)
     */
    public void doTranslate(int deltaX, int deltaY){
        Invoker.getInstance().executeCommand(new TranslateCommand(deltaX, deltaY));
    }

    /**
     * Exécute une commande d'annulation (Undo) de la dernière action.
     */
    public void doUndo(){
        Invoker.getInstance().executeCommand(new UndoCommand());
    }

    /**
     * Exécute une commande de rétablissement (Redo) de la dernière action annulée.
     */
    public void doRedo(){
        Invoker.getInstance().executeCommand(new RedoCommand());
    }

    /**
     * Sauvegarde la configuration actuelle dans un fichier.
     * 
     * @param filePath le chemin du fichier de destination
     */
    public void doSaveConfigFile(String filePath){
        Invoker.getInstance().executeCommand(new SaveConfigFileCommand(filePath));
    }

    /**
     * Charge une configuration depuis un fichier.
     * 
     * @param filePath le chemin du fichier de configuration à charger
     */
    public void doLoadConfigFile(String filePath){
        Invoker.getInstance().executeCommand(new LoadConfigFileCommand(filePath));
    }

    /**
     * Importe une image depuis un fichier.
     * 
     * @param filePath le chemin de l'image à importer
     */
    public void doImportImage(String filePath){
        Invoker.getInstance().executeCommand(new ImportImageCommand(filePath));
    }

    /**
     * Sauvegarde l'image éditée (zone recadrée) dans un fichier.
     * 
     * @param filePath le chemin du fichier de destination
     */
    public void doSaveImage(String filePath){
        Invoker.getInstance().executeCommand(new SaveImageCommand(filePath));
    }

    /**
     * Crée et stocke un nouveau memento représentant la configuration actuelle de l'éditeur.
     * <p>
     * Le memento sauvegarde l'état actuel de la perspective (zoom et position)
     * pour permettre une restauration ultérieure.
     * </p>
     */
    public void doCreateMemento(){
        // Manager handles memento creation/storing
        Manager.getInstance().createMemento();
    }
}
