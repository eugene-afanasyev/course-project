package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class  AboutWSController {
    @FXML
    private BorderPane contentBorder;

    public void initialize() {
        HeaderController.viewPath = "/Views/main.fxml";
    }

    public void moveToScene(String path) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            Scene scene = new Scene(root, 920, 640);
            Stage stage = (Stage) contentBorder.getScene().getWindow();
            scene.getStylesheets().add(getClass().getResource("/stylesheets/style.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toWorldSkillsHistory() {
        moveToScene("/Views/WorldSkillsHistory.fxml");
    }

    public void toNatioanalChampionships() {
        moveToScene("/Views/NationalChampionship.fxml");
    }

    public void toChampionshipCompetencies() {
        moveToScene("/Views/championshipCompetencies.fxml");
    }
}
