package ca.ets.log121.labo5.imageviewer;

import ca.ets.log121.labo5.imageviewer.controller.EditorController;
import ca.ets.log121.labo5.imageviewer.model.Manager;
import ca.ets.log121.labo5.imageviewer.view.EditorView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        EditorView editorView = new EditorView(stage);
        EditorController controller = new EditorController(editorView);


        editorView.setController(controller);

        Manager manager = Manager.getInstance();
        manager.getEditor().getImage().attach(controller);
        manager.getEditor().getPerspective().attach(controller);

        editorView.show();
    }
}
