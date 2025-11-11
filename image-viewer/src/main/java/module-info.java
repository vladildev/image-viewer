module ca.ets.log121.labo5.imageviewer {
    requires javafx.controls;
    requires javafx.fxml;


    opens ca.ets.log121.labo5.imageviewer to javafx.fxml;
    exports ca.ets.log121.labo5.imageviewer;
}