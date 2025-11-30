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

    public HomeController(HomeView homeView) {
        this.homeView = homeView;
    }

    @Override
    public void update(Observable o) {
        // Other classes have reactions but not this one
    }

    public void doImport(String filePath) {
        Invoker.getInstance().executeCommand(new ImportImageCommand(filePath));
    }
}
