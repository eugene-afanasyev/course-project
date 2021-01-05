package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller  {
    Model model = new Model();

    @FXML
    public BorderPane contentBorder;

    @FXML
    public ImageView logo = new ImageView();

    @FXML
    Group content;

    @FXML
    public void aboutWorldSkills() {
        moveToScene("/Views/aboutWorldskills.fxml");
    }

    private void moveToScene(String viewPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(viewPath));
            Scene scene = new Scene(root, 920, 640);
            Stage stage = (Stage) contentBorder.getScene().getWindow();
            scene.getStylesheets().add(getClass().getResource("/stylesheets/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Controller() {

        Image logoImg = new Image(getClass().getResource("/logo.png").toExternalForm());
        logo.setImage(logoImg);
    }
}
