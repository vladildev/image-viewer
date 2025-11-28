package ca.ets.log121.labo5.imageviewer.model.memento;


import java.util.ArrayList;
import java.util.List;

public class MementoHistory {
    private List<Memento> history = new ArrayList<Memento>();

    public void addMemento(Memento memento) {
        history.add(memento);
    }

    public boolean isEmpty() {
        return history.isEmpty();
    }
}
