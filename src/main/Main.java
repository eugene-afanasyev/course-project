package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import main.initializers.FromXLSInitializer;
import main.initializers.Initializer;
import main.dao.*;
import main.models.Championship;
import main.models.Discipline;
import main.models.User;
import main.services.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setOnCloseRequest(event -> System.exit(0));

        var initializer = new FromXLSInitializer();

        initializer.initializeVolunteers();

        var initializer = new FromXLSInitializer();
   //     initializer.initializeRegions("regions.xls");
      /*  initializer.initializeRoles("roles.xls");
        initializer.initializeChampionships("wsi.xls");
        initializer.initializeDisciplines("skills.xls");*/
       // initializer.initializeUsers("users.xls");
//       initializer.initializeResults("results.xls");

        //initializer.initializeExperts();
       // initializer.distributeByDisciplines("Expert");
       // initializer.distributeByDisciplines("Volunteer");
       // initializer.distributeByDisciplines("Coordinator");

        Parent root = FXMLLoader.load(getClass().getResource("/Views/main.fxml"));
        Scene primaryScene = new Scene(root, 920, 640);
        primaryScene.getStylesheets().add(getClass().getResource("/stylesheets/style.css").toExternalForm());
        primaryStage.setTitle("WSR 2017");
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
