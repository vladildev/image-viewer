package ca.ets.log121.labo5.imageviewer.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class EditorView {
    private Parent root;
    
    public EditorView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editor-view.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Parent getRoot() {
        return root;
    }
}
