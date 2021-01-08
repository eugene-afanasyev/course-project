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
import org.jgroups.protocols.AUTH;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        UserService<DBUserDAO> userService = new UserService<>(DBUserDAO::new);
        RoleService<DBRoleDAO> rolesService = new RoleService<>(DBRoleDAO::new);
        DisciplineService<DBDisciplineDAO> disciplineService = new DisciplineService<>(DBDisciplineDAO::new);

        Initializer initializer = new FromXLSInitializer();

        //initializer.initializeRoles("roles.xls");
        //initializer.initializeRegions("regions.xls");
        //initializer.initializeDisciplines("skills.xls");
        //initializer.initializeChampionships("wsi.xls");
        //initializer.initializeUsers("users.xls");

        //initializer.initializeResults("results.xls");

        //
//        var userLogin = AuthManager.Current.getUser().getLogin();
//        assert (userLogin.equals("2012R2100000266C"));

        var disciplines = disciplineService.findAll();
        var trueResult = new Hasher().checkPassword("ppU$ktDw".toCharArray(), "$31$16$iL4HszwYH6hijv7w4j5FvDyCe0BMHQyPL5S1eIlxByQ");
        var falseResult = new Hasher().checkPassword("ppU$ktDW".toCharArray(), "$31$16$iL4HszwYH6hijv7w4j5FvDyCe0BMHQyPL5S1eIlxByQ");

        var disci = new Discipline();
        Parent root = FXMLLoader.load(getClass().getResource("/Views/main.fxml"));
        Scene primaryScene = new Scene(root, 920, 640);
        primaryScene.getStylesheets().add(getClass().getResource("/stylesheets/style.css").toExternalForm());
        primaryStage.setTitle("WSR 2017");
        primaryStage.setScene(primaryScene);
        primaryStage.show();
        primaryStage.setMinWidth(920);
        primaryStage.setMinHeight(640);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
