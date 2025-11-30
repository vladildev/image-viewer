package ca.ets.log121.labo5.imageviewer.model;


import ca.ets.log121.labo5.imageviewer.model.memento.Memento;
import ca.ets.log121.labo5.imageviewer.model.memento.EditorMemento;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import ca.ets.log121.labo5.imageviewer.model.memento.MementoHistory;
import ca.ets.log121.labo5.imageviewer.model.observer.Image;
import ca.ets.log121.labo5.imageviewer.model.observer.Perspective;
import ca.ets.log121.labo5.imageviewer.tools.command.Command;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Manager {
    private static Manager instance;
    private Editor editor;
    private MementoHistory mementoHistory;
    private CommandHistory commandHistory;

    private Manager() {
        Image image = new Image(3000, 3000);
        Perspective perspective = new Perspective(3000, 3000, 0, 0);
        this.editor = new Editor(image, perspective);
        this.mementoHistory = new MementoHistory();
        this.commandHistory = new CommandHistory();
    }


    // =========== GETTERS ===========

    public static Manager getInstance() {
        if (instance == null) {
            instance = new Manager();
        }
        return instance;
    }
    public Editor getEditor() {
        return editor;
    }
    public CommandHistory getCommandHistory() {
        return commandHistory;
    }

    /**
     * Return a copy of mementos stored in the manager.
     */
    public java.util.List<Memento> getMementos() {
        return mementoHistory.getHistory();
    }



    // =========== METHODS ===========

    public void undo() {
        if(commandHistory.canUndo()){
            System.out.println("UNDO - Index avant: " + commandHistory.getIterator().getIndex());
            Command command = commandHistory.getIterator().previous();
            System.out.println("UNDO - Index après: " + commandHistory.getIterator().getIndex() + " - Command: " + command.getClass().getSimpleName());
            command.undo();
        }
    }
    public void redo() {
        if(commandHistory.canRedo()){
            System.out.println("REDO - Index avant: " + commandHistory.getIterator().getIndex());
            Command command = commandHistory.getIterator().next();
            System.out.println("REDO - Index après: " + commandHistory.getIterator().getIndex() + " - Command: " + command.getClass().getSimpleName());
            command.execute();
        }
    }
    public void restoreConfigFromFile(String filePath) {
        File in = new File(filePath);
        if (!in.exists()) {
            System.err.println("restoreConfigFromFile: file does not exist: " + filePath);
            return;
        }

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Failed to read config file: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        String json = sb.toString();
        if (json.isEmpty()) {
            System.err.println("restoreConfigFromFile: empty file: " + filePath);
            return;
        }

        // Very small JSON parser for the expected format: {"height":3000,"width":3000,"x":0,"y":0}
        try {
            String trimmed = json.trim();
            if (trimmed.startsWith("{")) trimmed = trimmed.substring(1);
            if (trimmed.endsWith("}")) trimmed = trimmed.substring(0, trimmed.length() - 1);
            String[] pairs = trimmed.split(",");
            Map<String, Integer> map = new HashMap<>();
            for (String pair : pairs) {
                String[] kv = pair.split(":");
                if (kv.length != 2) continue;
                String key = kv[0].trim().replaceAll("\"", "");
                String valStr = kv[1].trim().replaceAll("\"", "");
                try {
                    int v = Integer.parseInt(valStr);
                    map.put(key, v);
                } catch (NumberFormatException nfe) {
                    // ignore non-int values
                }
            }

            Perspective p = editor.getPerspective();
            if (map.containsKey("height")) p.setHeight(map.get("height"));
            if (map.containsKey("width")) p.setWidth(map.get("width"));
            if (map.containsKey("x")) p.setX(map.get("x"));
            if (map.containsKey("y")) p.setY(map.get("y"));
            p.notifyObservers();

        } catch (Exception e) {
            System.err.println("Failed to parse config JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void saveConfigToFile(String filePath) {
        Memento configFile = editor.createConfigEditorMemento();
        if (!(configFile instanceof EditorMemento)) {
            System.err.println("saveConfigToFile: Unexpected memento type: " + configFile.getClass().getName());
            return;
        }
        EditorMemento em = (EditorMemento) configFile;
        Perspective p = em.getPerspective();

        String json = String.format("{\"height\":%d,\"width\":%d,\"x\":%d,\"y\":%d}",
                p.getHeight(), p.getWidth(), p.getX(), p.getY());

        File out = new File(filePath);
        File parent = out.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(out))) {
            writer.write(json);
        } catch (IOException e) {
            System.err.println("Failed to write config file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void saveImageToFile(String filePath) {
        try {
            // Récupérer l'image de base et la perspective
            Image baseImage = editor.getImage();
            Perspective perspective = editor.getPerspective();
            
            // Vérifier que l'image a été importée
            if (baseImage.getPath() == null || baseImage.getPath().isEmpty()) {
                System.err.println("Aucune image n'a été importée.");
                return;
            }
            
            // Charger l'image JavaFX depuis le chemin
            javafx.scene.image.Image fxImage = new javafx.scene.image.Image("file:" + baseImage.getPath());
            double factor = baseImage.getWidth()/600.0;
            System.out.println("Factor: " + factor);
            // Obtenir les dimensions et position du crop depuis la perspective
            int cropX, cropY;
            System.out.println((baseImage.getWidth()/2)+"+ ("+perspective.getX()+"- "+perspective.getWidth()/2+")");
            System.out.println((baseImage.getHeight()/2)+"+ ("+perspective.getY()+"- "+perspective.getHeight()/2+")");

            cropX = (baseImage.getWidth()/2) + ((int) (perspective.getX()*factor) - (int) (perspective.getWidth()*factor/2.0)); ;
            cropY = (baseImage.getHeight()/2) + ((int) (perspective.getY()*factor) - (int) (perspective.getHeight()*factor/2.0)); ;
            cropX = Math.max(0, cropX);
            cropY = Math.max(0, cropY);
            System.out.println("Crop X: " + cropX + ", Crop Y: " + cropY);
            //int cropY = perspective.getY() + (baseImage.getHeight() - perspective.getHeight()) / 2;
            
            System.out.println("Crop Width: " + (perspective.getWidth()*factor) + ", Crop Height: " + (perspective.getHeight()*factor));
            int cropWidth = (int) (perspective.getWidth()*factor);
            int cropHeight = (int) (perspective.getHeight()*factor);
            
            // Créer une WritableImage avec les dimensions du crop
            WritableImage croppedImage = new WritableImage(cropWidth, cropHeight);
            PixelReader pixelReader = fxImage.getPixelReader();
            
            // Copier les pixels de la zone croppée
            croppedImage.getPixelWriter().setPixels(0, 0, cropWidth, cropHeight, 
                                                     pixelReader, cropX, cropY);
            
            // Convertir en BufferedImage pour sauvegarder
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(croppedImage, null);
            
            // Déterminer le format du fichier depuis l'extension
            String format = "png"; // Format par défaut
            if (filePath.toLowerCase().endsWith(".jpg") || filePath.toLowerCase().endsWith(".jpeg")) {
                format = "jpg";
            } else if (filePath.toLowerCase().endsWith(".bmp")) {
                format = "bmp";
            }
            
            // Sauvegarder l'image croppée
            File outputFile = new File(filePath);
            ImageIO.write(bufferedImage, format, outputFile);
            
            System.out.println("Image sauvegardée avec succès: " + filePath);
            System.out.println("Dimensions du crop: " + cropWidth + "x" + cropHeight + " à partir de (" + cropX + ", " + cropY + ")");
            
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde de l'image: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erreur inattendue: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void createMemento() {
        Memento memento = editor.createConfigEditorMemento();
        mementoHistory.addMemento(memento);
    }

    /**
     * Restore editor state from the provided memento.
     */
    public void restoreFromMemento(Memento memento) {
        if (!(memento instanceof EditorMemento)) return;
        EditorMemento em = (EditorMemento) memento;
        Perspective p = em.getPerspective();
        Perspective current = editor.getPerspective();
        current.setHeight(p.getHeight());
        current.setWidth(p.getWidth());
        current.setX(p.getX());
        current.setY(p.getY());
        // notify observers so views update
        current.notifyObservers();
    }
}
