package ca.ets.log121.labo5.imageviewer.tools.command;

import ca.ets.log121.labo5.imageviewer.model.Manager;

public class SaveConfigFileCommand implements Command
{
    private String configFilePath;

    public SaveConfigFileCommand(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    public boolean execute() {
        Manager.getInstance().saveConfigToFile(configFilePath);
        return false;
    }

    public void undo() {
        // Implementation for undoing the save config file action
    }
}