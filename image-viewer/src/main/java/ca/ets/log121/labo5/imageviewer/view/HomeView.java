package ca.ets.log121.labo5.imageviewer.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HomeView {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private final FileChooser fileChooser = new FileChooser();

    @FXML
    protected void searchLocalFiles() throws IOException {
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            // call your method
            display(selectedFile);
        }
    }

    private void display(File file) throws IOException {
        // Load the next FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditorView.fxml"));
        Parent root = loader.load();

        // Give the image file to the next controller
        EditorView view = loader.getController();
        //view.setImage(file);

        // Create NEW window
        Stage editorStage = new Stage();
        editorStage.setTitle("Image Viewer");
        editorStage.setScene(new Scene(root));

        // Show new window
        editorStage.show();

        // CLOSE original window
        stage.close();
    }
}

