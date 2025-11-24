package ca.ets.log121.labo5.imageviewer.model.observer;

public class Image {
    private int height, width;
    //private File file;
    //Obervers[]

    public Image(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
}
