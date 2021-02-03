package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AboutWSRController {
    @FXML
    public VBox contentBorder;
    public VBox filesBox;

    public void initialize() {
        HeaderController.viewPath = "/Views/main.fxml";
    }


    public void showTestFiles1(MouseEvent event) {
        filesBox.getChildren().clear();
        for (int i = 1; i <= 3; i++) {
            filesBox.getChildren().add(createLoadLink("Test file 1." + i));
        }
    }

    public void showTestFiles2(MouseEvent event) {
        filesBox.getChildren().clear();
        for (int i = 1; i <= 4; i++) {
            filesBox.getChildren().add(createLoadLink("Test file 2." + i));
        }
    }

    public void showTestFiles3(MouseEvent event) {
        filesBox.getChildren().clear();
        for (int i = 1; i <= 7; i++) {
            filesBox.getChildren().add(createLoadLink("Test file 3." + i));
        }
    }

    private Label createLoadLink(String text) {
        Label l = new Label(text);
        l.setWrapText(true);
        l.setOnMouseEntered((event -> {
            l.setUnderline(true);
        }));
        l.setOnMouseExited((event -> {
            l.setUnderline(false);
        }));
        l.setTextFill(Color.CORNFLOWERBLUE);
        l.setStyle("-fx-font-size: 26; -fx-font-weight: bold");
        l.setOnMouseClicked((event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.showSaveDialog(new Stage());
        }));
        return l;
    }
}
