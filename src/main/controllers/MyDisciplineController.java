package main.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.AuthManager;
import main.dao.DBChampionshipDAO;
import main.dao.DBDisciplineDAO;
import main.models.Championship;
import main.models.Module;
import main.models.User;
import main.models.UserViewModel;
import main.services.ChampionshipService;
import main.services.DisciplineService;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyDisciplineController {
    @FXML
    public TableView<UserViewModel> expertsTable;

    @FXML
    public TableColumn<UserViewModel, String> expertsNameColumn;

    @FXML
    public TableColumn<UserViewModel, String> expertsFamilyColumn;

    @FXML
    public TableColumn<UserViewModel, String> expertsBirthdayColumn;

    @FXML
    public TableView<UserViewModel> competitorsTable;

    @FXML
    public TableColumn<UserViewModel, String> competitorsNameColumn;

    @FXML
    public TableColumn<UserViewModel, String> competitorsFamilyColumn;

    @FXML
    public TableColumn<UserViewModel, String> competitorsBirthdayDateColumn;

    @FXML
    public Label title;

    private ChampionshipService<DBChampionshipDAO> championshipService = new ChampionshipService<>(DBChampionshipDAO::new);

    private Executor exec = Executors.newCachedThreadPool(runnable -> {
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        return t ;
    });

    @FXML
    public void initialize(){
        var user = AuthManager.Current.getUser();
        var currentChampionship = user.getChampionships().get(0);
        var currentDiscipline = user.getResults().get(0).getDiscipline();

        var usersRequest = new Task<List<User>>(){
            @Override
            protected List<User> call ( ) throws Exception {
                title.setText("Загрузка...");
                return currentChampionship.getUsers();
            }
        };

        usersRequest.setOnFailed(event -> {
            title.setText("Моя компетенция");
        });
        usersRequest.setOnSucceeded(event -> {
            var expertsCollection = FXCollections.<UserViewModel>observableArrayList();
            var competitorsCollection = FXCollections.<UserViewModel>observableArrayList();

            for(var item : usersRequest.getValue()){
                if(item.getResults().get(0).getDiscipline().getId() == currentDiscipline.getId()){
                    if(item.getRole().getName().equals("Expert")){
                        expertsCollection.add(new UserViewModel(item.getFirstName(), item.getLastName(), item.getBirthdayDate().toString()));
                    }else if(item.getRole().getName().equals("Competitor")){
                        competitorsCollection.add(new UserViewModel(item.getFirstName(), item.getLastName(), item.getBirthdayDate().toString()));
                    }
                }
            }

            expertsTable.getItems().addAll(expertsCollection);
            competitorsTable.getItems().addAll(competitorsCollection);

            title.setText("Моя компетенция");
        });

        competitorsFamilyColumn.setCellValueFactory(new PropertyValueFactory<UserViewModel, String>("secondName"));
        competitorsNameColumn.setCellValueFactory(new PropertyValueFactory<UserViewModel, String>("firstName"));
        competitorsBirthdayDateColumn.setCellValueFactory(new PropertyValueFactory<UserViewModel, String>("birthdayDate"));

        expertsNameColumn.setCellValueFactory(new PropertyValueFactory<UserViewModel, String>("firstName"));
        expertsBirthdayColumn.setCellValueFactory(new PropertyValueFactory<UserViewModel, String>("birthdayDate"));
        expertsFamilyColumn.setCellValueFactory(new PropertyValueFactory<UserViewModel, String>("secondName"));

        exec.execute(usersRequest);
    }

    private ObservableList<UserViewModel> getUsersView(List<User> users){
        var userViewCollection = FXCollections.<UserViewModel>observableArrayList();

        for(var user : users){
            userViewCollection.add(new UserViewModel(user.getFirstName(), user.getLastName(), user.getBirthdayDate().toString()));
        }

        return userViewCollection;
    }
}
