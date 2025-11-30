package ca.ets.log121.labo5.imageviewer.controller;
import ca.ets.log121.labo5.imageviewer.model.observer.*;
import ca.ets.log121.labo5.imageviewer.tools.command.*;
import ca.ets.log121.labo5.imageviewer.view.EditorView;
import ca.ets.log121.labo5.imageviewer.model.Manager;
import ca.ets.log121.labo5.imageviewer.model.memento.Memento;
import ca.ets.log121.labo5.imageviewer.model.memento.EditorMemento;
import ca.ets.log121.labo5.imageviewer.model.observer.Perspective;
import java.util.ArrayList;
import java.util.List;

public class EditorController implements Observer {
    EditorView view;
    
    public EditorController(EditorView view) {
        this.view = view;
    }

    /**
     * Return a list of human-readable summaries for each saved memento.
     * This keeps formatting logic in controller and view receives strings only.
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
     * Restore the memento at the given index (0-based).
     */
    public void restoreMementoAt(int index) {
        List<Memento> mems = Manager.getInstance().getMementos();
        if (mems == null) return;
        if (index < 0 || index >= mems.size()) return;
        Manager.getInstance().restoreFromMemento(mems.get(index));
    }

    public void update(Observable o) {
        if (o instanceof Image){
            Image img = (Image) o;
            view.setImage(img.getPath());
            // Mettre à jour les dimensions du cadre avec les dimensions de l'image
            view.setCadreDimensions(img.getWidth(), img.getHeight());
        } else if (o instanceof Perspective){
            Perspective p = (Perspective) o;
            // Appliquer le zoom avec les dimensions du crop
            view.zoomOnImage(p.getWidth(), p.getHeight());
            // Appliquer la translation avec la position du crop
            view.translateOnImage(p.getX(), p.getY());
        }
        
    }

    public void doZoom(double factor){
        Invoker.getInstance().executeCommand(new ZoomCommand(factor));
    }
    public void doTranslate(int deltaX, int deltaY){
        Invoker.getInstance().executeCommand(new TranslateCommand(deltaX, deltaY));
    }
    public void doUndo(){
        Invoker.getInstance().executeCommand(new UndoCommand());
    }
    public void doRedo(){
        Invoker.getInstance().executeCommand(new RedoCommand());
    }
    public void doSaveConfigFile(String filePath){
        Invoker.getInstance().executeCommand(new SaveConfigFileCommand(filePath));
    }
    public void doLoadConfigFile(String filePath){
        Invoker.getInstance().executeCommand(new LoadConfigFileCommand(filePath));
    }
    public void doImportImage(String filePath){
        Invoker.getInstance().executeCommand(new ImportImageCommand(filePath));
    }
    public void doSaveImage(String filePath){
        Invoker.getInstance().executeCommand(new SaveImageCommand(filePath));
    }

    /**
     * Create and store a new memento representing current editor configuration.
     */
    public void doCreateMemento(){
        // Manager handles memento creation/storing
        Manager.getInstance().createMemento();
    }
}
