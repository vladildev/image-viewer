package ca.ets.log121.labo5.imageviewer;

import ca.ets.log121.labo5.imageviewer.view.HomeView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        HomeView homeView = new HomeView();
        homeView.setStage(stage);
        stage.setTitle("Prêt pour LOG121!");
        stage.setScene(scene);
        stage.show();
    }
}
