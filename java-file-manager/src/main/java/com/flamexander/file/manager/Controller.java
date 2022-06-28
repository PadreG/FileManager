package com.flamexander.file.manager;

import javafx.application.Platform;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Controller {
    @FXML
    VBox leftPanel, rightPanel;

    public void menuItemExit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void exitBtnAction(ActionEvent actionEvent) {
        Platform.exit();

    }
    public void copyBtnAction(ActionEvent actionEvent) {
        PanelController leftPC = (PanelController) leftPanel.getProperties().get("ctrl");
        PanelController rightPC = (PanelController) rightPanel.getProperties().get("ctrl");

        if (leftPC.getSelectedFilename() == null && rightPC.getSelectedFilename() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ні один файл не був вибраний", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        PanelController srcPC = null;
        PanelController dstPC = null;
        if (leftPC.getSelectedFilename() != null) {
            srcPC = leftPC;
            dstPC = rightPC;
        }
        if (rightPC.getSelectedFilename() != null) {
            srcPC = rightPC;
            dstPC = leftPC;
        }

        Path srcFile = srcPC.getCurrentPath().resolve(srcPC.getSelectedFilename());
        Path dstDir = dstPC.getCurrentPath();

        try {
            Files.copy(srcFile, dstDir.resolve(srcFile.getFileName()));
            dstPC.updateList();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не вдалось скопіювати файл", ButtonType.OK);
            alert.showAndWait();
        }
    }
    public void deleteBtnAction(ActionEvent actionEvent) {
        PanelController leftPC = (PanelController) leftPanel.getProperties().get("ctrl");
        PanelController rightPC = (PanelController) rightPanel.getProperties().get("ctrl");
        if (leftPC.getSelectedFilename() == null && rightPC.getSelectedFilename() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ні одного файла не вибрано", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        PanelController currentPC = null;
        if (leftPC.getSelectedFilename() != null) {
            currentPC = leftPC;
        }
        if (rightPC.getSelectedFilename() != null) {
            currentPC = rightPC;
        }
        Path pathToFile = currentPC.getCurrentPath().resolve(currentPC.getSelectedFilename());
        if(!Files.isDirectory(pathToFile)) {
            try {
                Files.delete(pathToFile);
                currentPC.updateList();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Не вдалося видалити вибраний файл", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

}