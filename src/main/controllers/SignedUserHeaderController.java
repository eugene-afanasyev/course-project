package main.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.AuthManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void logout(MouseEvent event) {
        AuthManager.Current.logout();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Views/main.fxml"));
            Scene scene = new Scene(root, 920, 640);
            Stage stage = (Stage) backButton.getScene().getWindow();
            scene.getStylesheets().add(getClass().getResource("/stylesheets/style.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("remembered", false);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("user-data.json"));
            writer.write(gson.toJson(jsonObject));
            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
