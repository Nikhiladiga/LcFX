package org.nikhiladiga.service;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.nikhiladiga.utils.Utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class DesktopEntryService {

    private final Utils utils = new Utils();

    /**
     * Method that creates file structure from the input content
     * @param version Version of the application
     * @param isTerminalRequired If application uses terminal
     * @param appName Name of the application
     * @param appComment Comments
     * @param appExecPath Path to the executable file
     * @param appIconPath Path to the application icon
     * @param categories Categories that the application falls in
     * @return boolean
     */
    public boolean generateFileContent(String version, Boolean isTerminalRequired, String appName, String appComment, String appExecPath, String appIconPath, ObservableList<String> categories){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[Desktop Entry]");
        stringBuilder.append("\n");
        stringBuilder.append("Version=").append(version);
        stringBuilder.append("\n");
        stringBuilder.append("Type=Application");
        stringBuilder.append("\n");
        stringBuilder.append("Terminal=").append(isTerminalRequired);
        stringBuilder.append("\n");
        stringBuilder.append("Name=").append(appName);
        stringBuilder.append("\n");
        stringBuilder.append("Comment=").append(appComment);
        stringBuilder.append("\n");
        stringBuilder.append("Exec=").append(appExecPath);
        stringBuilder.append("\n");
        stringBuilder.append("Icon=").append(appIconPath);
        stringBuilder.append("\n");

        if(categories.size()>0){
            stringBuilder.append("Categories=");
            for(int i=0;i<categories.size();i++){
                if(i== categories.size()-1){
                    stringBuilder.append(categories.get(i));
                }else{
                    stringBuilder.append(categories.get(i)).append(";");
                }
            }
        }

        var result = saveFile(appName.toLowerCase().trim()+".desktop",stringBuilder);
        if(result){
            displayAlert("Success!","Success", Alert.AlertType.INFORMATION);
            return true;
        }else{
            displayAlert("Unknown error occurred!","Error", Alert.AlertType.ERROR);
            return false;
        }

    }


    /**
     * Method that saves file to local location
     * @param fileName Name of the file (Application name in lowercases appened with .desktop
     * @param content Generated contents of the file
     * @return boolean
     */
    public boolean saveFile(String fileName, StringBuilder content){
        String username = utils.execCmd(new String[]{"bash","-c","whoami"});
        if(username!=null){
            Path path = Paths.get("/home/"+username+"/.local/share/applications/"+fileName);
            try{
                Files.write(path, Collections.singleton(content));
                return true;
            }catch(IOException e){
                e.printStackTrace();
                return false;
            }
        }else{
            return false;
        }
    }


    /**
     * Method that displays alerts
     * @param headerText Alert header text
     * @param alertTitle Alert box title
     * @param alertType Type of alert
     */
    public void displayAlert(String headerText, String alertTitle,Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertTitle);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

}
