package main.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.AuthManager;
import main.models.Module;

public class UserResultController {

    @FXML
    private Label userName;

    @FXML
    private Label userGender;

    @FXML
    private Label userIdNumber;

    @FXML
    private Label userRegion;

    @FXML
    private Label userScore;

    @FXML
    private Label userChampionship;

    @FXML
    private Label discipline;

    @FXML
    private TableView<Module> modulesTable;

    @FXML
    private TableColumn<Module, Integer> idColumn;

    @FXML
    private TableColumn<Module, String > scoreColumn;

    @FXML
    void initialize(){
        var user = AuthManager.Current.getUser();
        userName.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
        userGender.setText(user.isMale() ? "Мужской" : "Женский");
        userIdNumber.setText(user.getLogin());
        userRegion.setText(user.getRegion().getName());
        var userResult = user.getResults().get(0);
        userScore.setText(String.format("%.2f", userResult.getScore()));
        userChampionship.setText(userResult.getChampionship().getName());
        discipline.setText(userResult.getDiscipline().getRuName());

        var scores = parseModules(userResult.getModules());

        idColumn.setCellValueFactory(new PropertyValueFactory<Module, Integer>("id"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<Module, String>("score"));

        modulesTable.getItems().addAll(scores);

        SignedUserHeaderController.viewPath = "/Views/CompetitorMenu.fxml";
    }

    private ObservableList<Module> parseModules( String modules){
        var modulesArray = modules.split(",");
        ObservableList<Module> result = FXCollections.observableArrayList();
        int i = 1;
        for (var module: modulesArray) {
            result.add(new Module(i++, module.trim()));
        }
        return result;
    }
}