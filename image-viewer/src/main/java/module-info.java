module ca.ets.log121.labo5.imageviewer {
    requires javafx.controls;
    requires javafx.fxml;


    opens ca.ets.log121.labo5.imageviewer to javafx.fxml;
    opens ca.ets.log121.labo5.imageviewer.controller to javafx.fxml;
    opens ca.ets.log121.labo5.imageviewer.view to javafx.fxml;

    exports ca.ets.log121.labo5.imageviewer;
    exports ca.ets.log121.labo5.imageviewer.controller;
}