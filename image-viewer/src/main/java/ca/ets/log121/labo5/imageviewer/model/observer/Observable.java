package ca.ets.log121.labo5.imageviewer.model.observer;

public interface Observable {
    void attach(Observer o);
    void detach(Observer o);
    void notifyObservers();
}
