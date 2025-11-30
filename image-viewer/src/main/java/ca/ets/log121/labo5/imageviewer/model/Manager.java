package ca.ets.log121.labo5.imageviewer.model;


import ca.ets.log121.labo5.imageviewer.model.memento.Memento;
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
        // Implementation for restoring configuration from file
    }
    public void saveConfigToFile(String filePath) {
        // Implementation for saving configuration to file
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
            
            // Obtenir les dimensions et position du crop depuis la perspective
            int cropX, cropY;
            System.out.println((baseImage.getWidth()/2)+"+ ("+perspective.getX()+"- "+perspective.getWidth()/2+")");
            System.out.println((baseImage.getHeight()/2)+"+ ("+perspective.getY()+"- "+perspective.getHeight()/2+")");

            cropX = (baseImage.getWidth()/2) + (perspective.getX()*3- (perspective.getWidth()/2)*3); ;
            cropY = (baseImage.getHeight()/2) + (perspective.getY()*3- (perspective.getHeight()/2)*3); ;
            cropX = Math.max(0, cropX);
            cropY = Math.max(0, cropY);
            System.out.println("Crop X: " + cropX + ", Crop Y: " + cropY);
            //int cropY = perspective.getY() + (baseImage.getHeight() - perspective.getHeight()) / 2;
            int cropWidth = perspective.getWidth()*3;
            int cropHeight = perspective.getHeight()*3;
            
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
        Memento memento = editor.createEditorMemento();
        mementoHistory.addMemento(memento);
    }
}
