package main.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SignedUserHeaderController {
    @FXML
    public Button backButton;

    public static String viewPath;

    public void initialize() {
        backButton.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource(viewPath));
                    Scene scene = new Scene(root, 920, 640);
                    Stage stage = (Stage) backButton.getScene().getWindow();
                    scene.getStylesheets().add(getClass().getResource("/stylesheets/style.css").toExternalForm());
                    stage.setScene(scene);
                    stage.setResizable(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void logout(MouseEvent event) {
    }
}
