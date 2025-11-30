package ca.ets.log121.labo5.imageviewer.controller;

import ca.ets.log121.labo5.imageviewer.model.observer.Image;
import ca.ets.log121.labo5.imageviewer.model.observer.Observable;
import ca.ets.log121.labo5.imageviewer.model.observer.Observer;
import ca.ets.log121.labo5.imageviewer.model.observer.Perspective;
import ca.ets.log121.labo5.imageviewer.view.*;

public class ThumbnailController implements Observer {
    ThumbnailView view;
    EditorView editorView;

    public ThumbnailController(ThumbnailView thumbnailView, EditorView editorView) {
        this.view = thumbnailView;
        this.editorView = editorView;
    }
    private void doZoom(){

    }

    private void doTranslate() {

    }

    @Override
    public void update(Observable o) {
        if (o instanceof Image) {
            view.setParentStack(editorView.getThumbnailStack());
            view.show();
            view.setImage(((Image) o).getPath());
        }
        else if (o instanceof Perspective) {
            view.translateOnZone();
            view.zoomOnZone();
        }
    }
}
