package main.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class CoordinatorMenuController {
    public Label welcomeTextLabel;

    private void moveToScene(String viewPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(viewPath));
            Scene scene = new Scene(root, 920, 640);
            Stage stage = (Stage) welcomeTextLabel.getScene().getWindow();
            scene.getStylesheets().add(getClass().getResource("/stylesheets/style.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toManageVolunteers() {
        moveToScene("/Views/volunteerManagement.fxml");
    }

    public void toManageSponsors(MouseEvent event) {
        moveToScene("/Views/InProgress.fxml");
    }

    public void toMyResults(MouseEvent event) {
        moveToScene("/Views/InProgress.fxml");
    }
}
