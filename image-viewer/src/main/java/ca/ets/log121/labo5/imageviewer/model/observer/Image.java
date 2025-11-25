package ca.ets.log121.labo5.imageviewer.model.observer;

import java.util.ArrayList;

import ca.ets.log121.labo5.imageviewer.model.Manager;

public class Image implements Observable {
    private int height, width;
    private ArrayList<Observer> observers;
    private String path;
    //private File file;
    //Obervers[]

    public Image(int height, int width) {
        this.height = height;
        this.width = width;
        this.observers = new ArrayList<Observer>();
    }


    // ========== Observable methods ==========

    @Override
    public void attach(Observer o) {
        observers.add(o);
    }
    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }
    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(this);
        }
    }

    // =========== Core Methods ===========

    public void importImage(String path) {
        this.path = path;
        
        // Charger l'image pour obtenir ses dimensions réelles
        try {
            javafx.scene.image.Image fxImage = new javafx.scene.image.Image("file:" + path);
            this.width = (int) fxImage.getWidth();
            this.height = (int) fxImage.getHeight();
            System.out.println("Image imported: " + path + " (" + width + "x" + height + ")");
            
            // Mettre à jour la perspective avec les dimensions de l'image
            updatePerspective();
        } catch (Exception e) {
            System.err.println("Error loading image dimensions: " + e.getMessage());
        }
        
        notifyObservers();
    }
    
    private void updatePerspective() {
        // Notifier le Manager pour mettre à jour la perspective
        Manager.getInstance().getEditor().getPerspective().updatePerspectiveDimensions(this.width, this.height);
    }



    // ========== Getters and Setters ==========

    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    public String getPath() {
        return path;
    }
}
