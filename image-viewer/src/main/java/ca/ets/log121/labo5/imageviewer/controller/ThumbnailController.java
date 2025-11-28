package ca.ets.log121.labo5.imageviewer.controller;

import ca.ets.log121.labo5.imageviewer.model.Manager;
import ca.ets.log121.labo5.imageviewer.model.observer.Observable;
import ca.ets.log121.labo5.imageviewer.model.observer.Observer;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class ThumbnailController implements Observer {

    @FXML
    private ImageView thumbnailImage;
    @FXML
    private Rectangle viewportRect;

    private EditorController editor;

    public void setEditor(EditorController editor) {
        this.editor = editor;

        String path = Manager.getInstance().getEditor().getImage().getPath();
        Image img = new Image(new File(path).toURI().toString());
        thumbnailImage.setImage(img);
    }

    private void doZoom(){

    }

    private void doTranslate() {

    }

    @Override
    public void update(Observable o) {

    }
}
