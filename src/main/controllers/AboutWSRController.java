package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class AboutWSRController {
    @FXML
    public BorderPane contentBorder;

    public void initialize() {
        HeaderController.viewPath = "/Views/main.fxml";
    }
}
