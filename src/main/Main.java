package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import main.models.*;
import main.dao.*;
import main.services.*;

import java.util.Calendar;
import java.util.Date;
import java.util.function.Supplier;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        UserService<DBUserDAO> userService = new UserService<>(DBUserDAO::new);
        User user = new User();

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene primaryScene = new Scene(root, 920, 640);
        primaryScene.getStylesheets().add(getClass().getResource("/stylesheets/style.css").toExternalForm());
        primaryStage.setTitle("WSR 2017");
        primaryStage.setScene(primaryScene);
        primaryStage.show();
        primaryStage.setMaxWidth(920);
        primaryStage.setMaxHeight(640);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
