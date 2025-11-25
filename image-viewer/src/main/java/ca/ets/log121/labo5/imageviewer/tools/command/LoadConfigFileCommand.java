package ca.ets.log121.labo5.imageviewer.tools.command;

import ca.ets.log121.labo5.imageviewer.model.Manager;

public class LoadConfigFileCommand implements Command {

    private String configFilePath;

    public LoadConfigFileCommand(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    public boolean execute() {
        // Implementation for executing the load config file action
        Manager.getInstance().restoreConfigFromFile(configFilePath);
        return false;
    }

    public void undo() {
        // Implementation for undoing the load config file action
    }
}
