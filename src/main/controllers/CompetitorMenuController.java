package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class CompetitorMenuController {
    @FXML
    public Label welcomeTextLabel;

    public void moveToScene(String path) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            Scene scene = new Scene(root, 920, 640);
            Stage stage = (Stage) welcomeTextLabel.getScene().getWindow();
            scene.getStylesheets().add(getClass().getResource("/stylesheets/style.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toMyProfile() {
        moveToScene("/Views/CompetitorProfile.fxml");
    }

    public void toMyCompetencies(MouseEvent event) {
        moveToScene("/Views/MyDisciplineView.fxml");
    }

    public void toMyResults(MouseEvent event) {
        moveToScene("/Views/MyResultView.fxml");
    }
}
