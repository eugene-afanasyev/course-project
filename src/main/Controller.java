package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller  {
    @FXML
    public Button loginButton;

    @FXML
    public BorderPane contentBorder;

    @FXML
    public ImageView logo = new ImageView();

    @FXML
    public void aboutWorldSkills() {
        moveToScene("/Views/AboutWorldSkills.fxml");
    }

    @FXML
    public void aboutKrasnodarRegion() {
        moveToScene("/Views/AboutKrasnodarRegion.fxml");
    }

    @FXML
    public void aboutWorldSkillsRussia(MouseEvent event) {
        moveToScene("/Views/AboutWSRussia.fxml");
    }

    private void moveToScene(String viewPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(viewPath));
            Scene scene = new Scene(root, 920, 640);
            Stage stage = (Stage) contentBorder.getScene().getWindow();
            scene.getStylesheets().add(getClass().getResource("/stylesheets/style.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Controller() {

        Image logoImg = new Image(getClass().getResource("/logo.png").toExternalForm());
        logo.setImage(logoImg);
    }

    public void moveToLoginScene(MouseEvent event) {
        moveToScene("/Views/AuthorizationMenu.fxml");
    }
}
