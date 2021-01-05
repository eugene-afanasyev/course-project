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
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            root = loader.load(getClass().getResource(viewPath));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 920, 640));
            stage.setResizable(false);
            stage.show();
            contentBorder.getScene().getWindow().hide();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Controller() {

        Image logoImg = new Image(getClass().getResource("/logo.png").toExternalForm());
        logo.setImage(logoImg);
    }
}
