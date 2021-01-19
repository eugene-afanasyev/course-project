package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.dao.DBDisciplineDAO;
import main.models.Voluunter;
import main.services.DisciplineService;

import java.io.IOException;

public class VolunteerManagementController {
    @FXML
    private Button searchButton;

    @FXML
    private ComboBox<String> competenciesComboBox;

    @FXML
    private TableView<Voluunter> volunteerTableView;

    @FXML
    private TableColumn<Voluunter, Integer> idColumn;

    @FXML
    private TableColumn<Voluunter, String> nameColumn;

    @FXML
    private TableColumn<Voluunter, String> sexColumn;

    @FXML
    private TableColumn<Voluunter, String> regionColumn;

    @FXML
    private TableColumn<Voluunter, String> competenceColumn;

    @FXML
    private VBox mainView;

    @FXML
    private Label sort;
    @FXML
    void initialize(){
        var disciplineService = new DisciplineService<>(DBDisciplineDAO::new);
        var allDisciplines = disciplineService.findAll();

        for(var discipline: allDisciplines) {
            competenciesComboBox.getItems().add(discipline.getRuName());
        }
        competenciesComboBox.setValue(allDisciplines.get(0).getRuName());

        

        idColumn.setCellValueFactory(new PropertyValueFactory<Voluunter, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Voluunter, String>("name"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<Voluunter, String>("sex"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<Voluunter, String>("region"));
        competenceColumn.setCellValueFactory(new PropertyValueFactory<Voluunter, String>("competence"));


        searchButton.setOnAction(actionEvent -> {

        });
    }

    public void ToVolunteerLoadingPage(){
        moveToScene("/Views/VolunteerLoadingView.fxml");
    }

    public void moveToScene(String path) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            Scene scene = new Scene(root, 1020, 740);
            Stage stage = (Stage) mainView.getScene().getWindow();
            scene.getStylesheets().add(getClass().getResource("/stylesheets/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
