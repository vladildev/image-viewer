package ca.ets.log121.labo5.imageviewer.controller;
import ca.ets.log121.labo5.imageviewer.model.Manager;
import ca.ets.log121.labo5.imageviewer.model.observer.*;
import ca.ets.log121.labo5.imageviewer.tools.command.*;
import ca.ets.log121.labo5.imageviewer.view.EditorView;

import java.io.IOException;

public class EditorController implements Observer {
    EditorView view;
    
    public EditorController(EditorView view) {
        this.view = view;
    }

    public void update(Observable o) {
        if (o instanceof Image){
            view.setImage( ((Image) o).getPath() );
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
}
