package ca.ets.log121.labo5.imageviewer.view;

import ca.ets.log121.labo5.imageviewer.controller.HomeController;
import ca.ets.log121.labo5.imageviewer.tools.command.Command;
import ca.ets.log121.labo5.imageviewer.tools.command.ImportImageCommand;
import ca.ets.log121.labo5.imageviewer.tools.command.LoadConfigFileCommand;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HomeView {
    private Command importImageCommand;
    private HomeController homeController;
    private final FileChooser fileChooser = new FileChooser();
    private Stage stage;

    public HomeView(Stage stage) {
        this.stage = stage;
    }

    @FXML
    protected void searchLocalFiles() throws IOException {
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            homeController.doImport(selectedFile.getPath());
        }
    }

    public void show() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ca/ets/log121/labo5/imageviewer/home-view.fxml")
        );
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root, 400, 400);
        stage.setTitle("Home Screen");
        stage.setScene(scene);
        stage.show();
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    public Stage getStage() {
        return stage;
    }
}

