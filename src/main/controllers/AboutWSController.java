package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AboutWSController {
    @FXML
    public BorderPane contentBorder;

    public void toWorldSkillsHistory(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Views/WorldSkillsHistory.fxml"));
            Scene scene = new Scene(root, 920, 640);
            Stage stage = (Stage) contentBorder.getScene().getWindow();
            scene.getStylesheets().add(getClass().getResource("/stylesheets/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
