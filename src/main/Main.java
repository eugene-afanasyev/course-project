package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mchange.io.FileUtils;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import main.initializers.FromXLSInitializer;
import main.initializers.Initializer;
import main.dao.*;
import main.misc.UserData;
import main.models.Championship;
import main.models.Discipline;
import main.models.User;
import main.services.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        var initializer = new FromXLSInitializer();

        primaryStage.setOnCloseRequest(event -> System.exit(0));

        File file = new File("user-data.json");
        Parent root = null;

        if (file.exists()) {
            JsonObject jsonObject = (JsonObject) JsonParser.parseString(FileUtils.getContentsAsString(file));
            /* raw and unhandled loading*/
            if (jsonObject.get("remembered").getAsBoolean()) {
                Gson gson = new GsonBuilder().create();
                JsonObject userJson = jsonObject.getAsJsonObject("user");
                UserData userData = gson.fromJson(userJson, UserData.class);

                AuthManager.Current.authorize(userData.getLogin(), userData.getPassword());

                root = FXMLLoader.load(getClass().getResource("/Views/CompetitorMenu.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("/Views/main.fxml"));
            }
        }

        Scene primaryScene = new Scene(root, 920, 640);
        primaryScene.getStylesheets().add(getClass().getResource("/stylesheets/style.css").toExternalForm());
        primaryStage.setTitle("WSR 2017");
        primaryStage.setScene(primaryScene);
        primaryStage.show();

        //Логин: 2012R2100000001C
        //Пароль: ppU$ktDw
    }

    public static void main(String[] args) {
        launch(args);
    }
}