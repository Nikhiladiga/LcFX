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

    private Utils utils = new Utils();

    //Method with basic file structure as string that accepts dynamic values
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
            displayAlert("Success!", Alert.AlertType.INFORMATION);
            return true;
        }else{
            displayAlert("Unable to create file. Please try running the application as a root user", Alert.AlertType.ERROR);
            return false;
        }

    }


    //Method to write file to location
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


    public void displayAlert(String headerText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Error");
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

}
