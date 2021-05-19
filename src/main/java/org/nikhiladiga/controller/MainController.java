package org.nikhiladiga.controller;

import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.controlsfx.control.CheckComboBox;
import org.nikhiladiga.service.DesktopEntryService;

import java.io.File;
import java.util.regex.Pattern;

public class MainController {

    private final DesktopEntryService desktopEntryService = new DesktopEntryService();

    @FXML
    private TextField appVersion;

    @FXML
    private TextField appName;

    @FXML
    private TextField appComment;

    @FXML
    private Label appExecPath;

    @FXML
    private Label appIconPath;

    @FXML
    private TextField appKeywords;

    @FXML
    private CheckComboBox<String> appCategories;

    @FXML
    private JFXRadioButton terminalTrue;

    @FXML
    private ToggleGroup terminalRequiredGroup;

    @FXML
    private JFXRadioButton terminalFalse;


    /**
     * Method to fills in values to the CheckComboBox and adds listeners to nodes
     */
    public void initialize() {
        //Add categories to CheckComboBox
        final ObservableList<String> categories = FXCollections.observableArrayList();
        categories.add("AudioVideo");
        categories.add("Development");
        categories.add("Education");
        categories.add("Game");
        categories.add("Graphics");
        categories.add("Network");
        categories.add("Office");
        categories.add("Science");
        categories.add("Settings");
        categories.add("System");
        categories.add("Utility");
        categories.add("IDE");
        categories.add("Debugger");
        categories.add("GUIDesigner");
        categories.add("Database");
        categories.add("Chat");
        categories.add("RemoteAccess");
        categories.add("Player");
        categories.add("Art");
        categories.add("Music");
        categories.add("XDE");
        categories.add("XFCE");
        categories.add("GTK");
        categories.add("Qt");
        categories.add("Java");
        categories.add("ConsoleOnly");
        categories.add("Documentation");

        appCategories.getItems().addAll(categories);
        appCategories.setStyle("-fx-text-fill:#FFF");

        //Add application executable path file dialogue
        appExecPath.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose executable or script");
                File selectedFilePath = fileChooser.showOpenDialog(appExecPath.getScene().getWindow());
                if (selectedFilePath != null) {
                    appExecPath.setText(selectedFilePath.getAbsolutePath());
                    appExecPath.setTextFill(Color.web("#fff"));
                }
            }
        });

        //Add application icon path file dialogue
        appIconPath.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose application icon");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("PNG Files", "*.png")
                );
                File selectedFilePath = fileChooser.showOpenDialog(appExecPath.getScene().getWindow());
                if (selectedFilePath != null) {
                    appIconPath.setText(selectedFilePath.getAbsolutePath());
                    appIconPath.setTextFill(Color.web("#fff"));
                }
            }
        });


    }

    /**
     * Method to validate all input entered by the user
     * @return boolean
     */
    public boolean validateInput() {
        boolean valid = true;

        if (appVersion.getText() == null || appVersion.getText().equals("")) {
            valid = false;
            desktopEntryService.displayAlert("Please enter a valid version", "Error", Alert.AlertType.ERROR);
            return valid;
        } else {
            if (!(Pattern.matches("[0-9]+.[0-9]+", appVersion.getText()))) {
                valid = false;
                desktopEntryService.displayAlert("Please enter a valid version","Error", Alert.AlertType.ERROR);
                return valid;
            }
        }

        if (appName.getText() == null || appName.getText().equals("")) {
            valid = false;
            desktopEntryService.displayAlert("Please enter a valid app name","Error", Alert.AlertType.ERROR);
            return valid;
        }

        if (appComment.getText() == null || appComment.getText().equals("")) {
            valid = false;
            desktopEntryService.displayAlert("Please enter a valid comment","Error", Alert.AlertType.ERROR);
            return valid;
        }

        if (appExecPath.getText() == null || appExecPath.getText().equals("") || appExecPath.getText().equals("Path to executable file or script")) {
            valid = false;
            desktopEntryService.displayAlert("Please choose a valid executable", "Error",Alert.AlertType.ERROR);
            return valid;
        }

        if (appIconPath.getText() == null || appIconPath.getText().equals("") || appExecPath.getText().equals("Path to application icon")) {
            valid = false;
            desktopEntryService.displayAlert("Please choose a valid app icon","Error", Alert.AlertType.ERROR);
            return valid;
        }

        return valid;

    }

    @FXML
    void handleSave(ActionEvent event) {

        boolean valid = validateInput();

        if (valid) {
            var result = desktopEntryService.generateFileContent(
                    appVersion.getText(),
                    terminalTrue.isSelected(),
                    appName.getText(),
                    appComment.getText(),
                    appExecPath.getText(),
                    appIconPath.getText(),
                    appCategories.getCheckModel().getCheckedItems()
            );

            if (result) {
                appVersion.setText("");
                appName.setText("");
                appComment.setText("");
                appExecPath.setText("");
                appIconPath.setText("");
                appCategories.getCheckModel().clearChecks();
            }

        }
    }


}
