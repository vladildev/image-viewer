package ca.ets.log121.labo5.imageviewer.model.observer;

import java.util.ArrayList;

import ca.ets.log121.labo5.imageviewer.model.Manager;


public class Perspective implements Observable {
    private int height, width;
    private int x, y;
    private ArrayList<Observer> observers;
    //Obervers[]

    public Perspective(int height, int width, int x, int y) {
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
        this.observers = new ArrayList<Observer>();
    }

    // =============== Observable methods ================

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

    // ================== Getters and Setters ==================

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }


    //  ==================== Core Methods ====================

    // possibilité de faire fune fonc crop() ici
    
    public void updatePerspectiveDimensions(int width, int height) {
        this.setWidth(width);
        this.setHeight(height);
        this.setX(0);
        this.setY(0);
        notifyObservers();
    }

    public void zoom(double facteur){
        this.width *= facteur;
        this.height *= facteur;
        notifyObservers();
        System.out.println(">>> Perspective.zoom() - new size: " + width + "x" + height + " - observers: " + observers.size());
        
    }
    public void translate(int deltaX, int deltaY){
        this.x += deltaX;
        this.y += deltaY;
        notifyObservers();
    }
}
