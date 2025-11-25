package ca.ets.log121.labo5.imageviewer.tools.iterator;

public interface Iterator {
    boolean hasNext();
    boolean hasPrevious();
    Object previous();
    Object next();
    int getIndex();
}
