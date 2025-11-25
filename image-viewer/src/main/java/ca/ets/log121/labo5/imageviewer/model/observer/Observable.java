package ca.ets.log121.labo5.imageviewer.model.observer;

public interface Observable {
    public void attach(Observer o);
    public void detach(Observer o);
    public void notifyObservers();
}
