package ca.ets.log121.labo5.imageviewer.controller;

import ca.ets.log121.labo5.imageviewer.model.Manager;
import ca.ets.log121.labo5.imageviewer.model.observer.Image;
import ca.ets.log121.labo5.imageviewer.model.observer.Observable;
import ca.ets.log121.labo5.imageviewer.model.observer.Observer;
import ca.ets.log121.labo5.imageviewer.tools.command.ImportImageCommand;
import ca.ets.log121.labo5.imageviewer.view.HomeView;

public class HomeController implements Observer {
    HomeView homeView;

    public HomeController(HomeView view) {
        this.homeView = view;
    }

    @Override
    public void update(Observable o) {
        if (o instanceof Image && Manager.getInstance().getEditor().getImage().getPath() != null) {
            Manager.getInstance().getEditor().getImage().detach(this);
            homeView.getStage().close();
        }
    }

    public void doImport(String filePath) {
        Invoker.getInstance().executeCommand(new ImportImageCommand(filePath));
    }
}
