package ca.ets.log121.labo5.imageviewer;

import ca.ets.log121.labo5.imageviewer.controller.EditorController;
import ca.ets.log121.labo5.imageviewer.controller.HomeController;
import ca.ets.log121.labo5.imageviewer.controller.ThumbnailController;
import ca.ets.log121.labo5.imageviewer.model.Manager;
import ca.ets.log121.labo5.imageviewer.view.EditorView;
import ca.ets.log121.labo5.imageviewer.view.HomeView;
import ca.ets.log121.labo5.imageviewer.view.ThumbnailView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        HomeView homeView = new HomeView(stage);
        HomeController homeController = new HomeController(homeView);
        homeView.setHomeController(homeController);

        EditorView editorView = new EditorView(stage);
        EditorController editorController = new EditorController(editorView);
        editorView.setController(editorController);
        
        // TODO edit thumbnail view and controller
        ThumbnailView thumbnailView = new ThumbnailView();
        ThumbnailController thumbnailController = new ThumbnailController();

        Manager manager = Manager.getInstance();
        manager.getEditor().getImage().attach(homeController);
        manager.getEditor().getImage().attach(editorController);
        manager.getEditor().getPerspective().attach(editorController);

        homeView.show();
    }
}
