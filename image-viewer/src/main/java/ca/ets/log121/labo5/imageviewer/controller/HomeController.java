package ca.ets.log121.labo5.imageviewer.controller;

import ca.ets.log121.labo5.imageviewer.model.Manager;
import ca.ets.log121.labo5.imageviewer.model.observer.Image;
import ca.ets.log121.labo5.imageviewer.model.observer.Observable;
import ca.ets.log121.labo5.imageviewer.model.observer.Observer;
import ca.ets.log121.labo5.imageviewer.tools.command.ImportImageCommand;
import ca.ets.log121.labo5.imageviewer.view.EditorView;
import ca.ets.log121.labo5.imageviewer.view.HomeView;

import java.io.IOException;

public class HomeController implements Observer {
    HomeView homeView;
    EditorView editorView;

    public HomeController(HomeView homeView, EditorView editorView) {
        this.homeView = homeView;
        this.editorView = editorView;
    }

    @Override
    public void update(Observable o) {
        if (o instanceof Image && Manager.getInstance().getEditor().getImage().getPath() != null) {
            Manager.getInstance().getEditor().getImage().detach(this);

            try {
                editorView.show();
            } catch (IOException e) {
                System.out.println(e);
                // Nothing else as it means it didn't work and user has to re-try
            }
        }
    }

    public void doImport(String filePath) {
        Invoker.getInstance().executeCommand(new ImportImageCommand(filePath));
    }
}
